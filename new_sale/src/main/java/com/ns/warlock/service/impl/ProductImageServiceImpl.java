package com.ns.warlock.service.impl;

import com.ns.warlock.Setting;
import com.ns.warlock.common.CommonAttributes;
import com.ns.warlock.dao.ProductImageDao;
import com.ns.warlock.dto.ProductImageDTO;
import com.ns.warlock.file.FilePlugin;
import com.ns.warlock.service.ProductImageService;
import com.ns.warlock.util.FreemarkerUtils;
import com.ns.warlock.util.ImageUtils;
import com.ns.warlock.util.SettingUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("productImageServiceImpl")
public class ProductImageServiceImpl implements ProductImageService, ServletContextAware {

    /** 目标扩展名 */
    private static final String DEST_EXTENSION = "jpg";

    private static final String DEST_CONTENT_TYPE = "image/jpeg";

    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    private ServletContext servletContext;

    @Autowired
    private ProductImageDao productImageDao;

    @Resource
    private FilePlugin filePlugin;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public List<ProductImageDTO> findByProductSn(String productSn) {
        return productImageDao.findByProductSn(productSn);
    }

    @Override
    public void insert(ProductImageDTO productImageDTO) {
        productImageDao.insert(productImageDTO);
    }

    @Override
    public void update(ProductImageDTO productImageDTO) {
        productImageDao.update(productImageDTO);
    }

    private void addTask(final String sourcePath, final String largePath, final String mediumPath, final String thumbnailPath, final File tempFile, final String contentType) {
        try {

            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Setting setting = SettingUtils.get();
                    //获取临时文件路径 并将需要提交文件设置在临时文件区
                    String tempPath = System.getProperty("java.io.tmpdir");
                    File watermarkFile = new File(servletContext.getRealPath(setting.getWatermarkImage()));
                    File largeTempFile = new File(tempPath + "/upload_" + UUID.randomUUID() + "." + DEST_EXTENSION);
                    File mediumTempFile = new File(tempPath + "/upload_" + UUID.randomUUID() + "." + DEST_EXTENSION);
                    File thumbnailTempFile = new File(tempPath + "/upload_" + UUID.randomUUID() + "." + DEST_EXTENSION);

                    try {
                        ImageUtils.zoom(tempFile, largeTempFile, setting.getLargeProductImageWidth(), setting.getLargeProductImageHeight());
                        //调用 imageUtils.addWatermark 大图片加水印
                        ImageUtils.zoom(tempFile, mediumTempFile, setting.getMediumProductImageWidth(), setting.getMediumProductImageHeight());
                        //调用 imageUtils.addWatermark 中图片加水印
                        ImageUtils.zoom(tempFile, thumbnailTempFile, setting.getThumbnailProductImageWidth(), setting.getThumbnailProductImageHeight());
                        //调用 imageUtils.addWatermark 小图片加水印

                        filePlugin.upload(sourcePath, tempFile, contentType);
                        filePlugin.upload(largePath, largeTempFile, DEST_CONTENT_TYPE);
                        filePlugin.upload(mediumPath, mediumTempFile, DEST_CONTENT_TYPE);
                        filePlugin.upload(thumbnailPath, thumbnailTempFile, DEST_CONTENT_TYPE);

                    } finally {
                        FileUtils.deleteQuietly(tempFile);
                        FileUtils.deleteQuietly(largeTempFile);
                        FileUtils.deleteQuietly(mediumTempFile);
                        FileUtils.deleteQuietly(thumbnailTempFile);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void build(ProductImageDTO productImageDTO) {
        MultipartFile multipartFile = productImageDTO.getFile();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                Setting setting = SettingUtils.get();
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("uuid", UUID.randomUUID().toString());
                String uploadPath = FreemarkerUtils.process(CommonAttributes.IMAGE_PATH,model); //后期可以通过配置实现图片路径
                String uuid = UUID.randomUUID().toString();
                String sourcePath = uploadPath + uuid + "-source." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                String largePath = uploadPath + uuid + "-large." + DEST_EXTENSION;
                String mediumPath = uploadPath + uuid + "-medium." + DEST_EXTENSION;
                String thumbnailPath = uploadPath + uuid + "-thumbnail." + DEST_EXTENSION;

                //获取临时文件并存放至临时文件夹下
                File tempFile = new File(System.getProperty("java.to.tmpdir") + "/upload_" + UUID.randomUUID() + ".tmp");
                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdir();
                }
                //获取传来的文件并存放至临时文件下
                multipartFile.transferTo(tempFile);

                //异步任务去生产文件图片
                addTask(sourcePath, largePath, mediumPath, thumbnailPath, tempFile, multipartFile.getContentType());

                productImageDTO.setSource(setting.getSiteUrl() + sourcePath);
                productImageDTO.setLarge(setting.getSiteUrl() + largePath);
                productImageDTO.setMedium(setting.getSiteUrl() + mediumPath);
                productImageDTO.setThumbnail(setting.getSiteUrl() + thumbnailPath);

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
