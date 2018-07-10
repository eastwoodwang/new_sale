package com.ns.warlock.service.impl;

import com.ns.warlock.FileInfo;
import com.ns.warlock.FileInfo.FileType;
import com.ns.warlock.FileInfo.OrderType;
import com.ns.warlock.Setting;
import com.ns.warlock.service.FileService;
import com.ns.warlock.util.FreemarkerUtils;
import com.ns.warlock.util.SettingUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service("fileServiceImpl")
public class FileServiceImpl implements FileService, ServletContextAware {

    private ServletContext servletContext;

    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public boolean isValid(FileInfo.FileType fileType, MultipartFile multipartFile) {
        if (multipartFile == null) {
            return false;
        }
        Setting setting = SettingUtils.get();
        if (setting.getUploadMaxSize() != null && setting.getUploadMaxSize() != 0 && multipartFile.getSize() > setting.getUploadMaxSize() * 1024L * 1024L) {
            return false;
        }
        String[] uploadExtensions = setting.getUploadImageExtensions();
        if (ArrayUtils.isNotEmpty(uploadExtensions)) {
            return FilenameUtils.isExtension(multipartFile.getOriginalFilename(), uploadExtensions);
        }
        return false;
    }

    @Override
    public String upload(FileInfo.FileType fileType, MultipartFile multipartFile, boolean async) {
        if (multipartFile == null) {
            return null;
        }
        Setting setting = SettingUtils.get();
        String uploadPath = setting.getImageUploadPath();

        try {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("uuid", UUID.randomUUID().toString());
            String path = FreemarkerUtils.process(uploadPath, model);
            String destPath = path + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());

            //for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
                File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".tmp");
                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdirs();
                }
                multipartFile.transferTo(tempFile);
                if (async) {
                    addTask(destPath, tempFile, multipartFile.getContentType());
                } else {
                    try {
                        upload(destPath, tempFile, multipartFile.getContentType());
                    } finally {
                        FileUtils.deleteQuietly(tempFile);
                    }
                }
                return getUrl(destPath);
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取上传图片位置
     * @param path
     * @return
     */
    private String getUrl(String path) {
        Setting setting = SettingUtils.get();
        return setting.getSiteUrl() + path;
    }

    /**
     * 上传图片
     * @param path
     * @param file
     * @param contentType
     */
    public void upload(String path, File file, String contentType) {
        File destFile = new File(servletContext.getRealPath(path));
        try {
            FileUtils.moveFile(file, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加上传任务
     *
     * @param path
     *            上传路径
     * @param tempFile
     *            临时文件
     * @param contentType
     *            文件类型
     */
    private void addTask(final String path, final File tempFile, final String contentType) {
        taskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    upload(path, tempFile, contentType);
                } finally {
                    FileUtils.deleteQuietly(tempFile);
                }
            }
        });
    }

    @Override
    public String upload(FileInfo.FileType fileType, MultipartFile multipartFile) {
        return upload(fileType, multipartFile, false);
    }

    @Override
    public String uploadLocal(FileInfo.FileType fileType, MultipartFile multipartFile) {
        if (multipartFile == null) {
            return null;
        }
        Setting setting = SettingUtils.get();
        String uploadPath = setting.getImageUploadPath();

        try {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("uuid", UUID.randomUUID().toString());
            String path = FreemarkerUtils.process(uploadPath, model);
            String destPath = path + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            File destFile = new File(servletContext.getRealPath(destPath));
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            multipartFile.transferTo(destFile);
            return destPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FileInfo> browser(String path, FileInfo.FileType fileType, FileInfo.OrderType orderType) {
        if (path != null) {
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (!path.endsWith("/")) {
                path += "/";
            }
        } else {
            path = "/";
        }
        Setting setting = SettingUtils.get();
        String uploadPath = setting.getImageUploadPath();

        String browsePath = StringUtils.substringBefore(uploadPath, "${");
        browsePath = StringUtils.substringBeforeLast(browsePath, "/") + path;

        List<FileInfo> fileInfos = new ArrayList<FileInfo>();
        if (browsePath.indexOf("..") >= 0) {
            return fileInfos;
        }

        fileInfos = browser(browsePath);

        if (orderType == OrderType.size) {
            Collections.sort(fileInfos, new SizeComparator());
        } else if (orderType == OrderType.type) {
            Collections.sort(fileInfos, new TypeComparator());
        } else {
            Collections.sort(fileInfos, new NameComparator());
        }
        return fileInfos;
    }

    /**
     * 组装FileInfo
     * @param path
     * @return
     */
    private List<FileInfo> browser(String path) {
        Setting setting = SettingUtils.get();
        List<FileInfo> fileInfos = new ArrayList<FileInfo>();
        File directory = new File(servletContext.getRealPath(path));
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setName(file.getName());
                fileInfo.setUrl(setting.getSiteUrl() + path + file.getName());
                fileInfo.setIsDirectory(file.isDirectory());
                fileInfo.setSize(file.length());
                fileInfo.setLastModified(new Date(file.lastModified()));
                fileInfos.add(fileInfo);
            }
        }
        return fileInfos;
    }

    private class NameComparator implements Comparator<FileInfo> {
        public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
            return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(fileInfos1.getName(), fileInfos2.getName()).toComparison();
        }
    }

    private class SizeComparator implements Comparator<FileInfo> {
        public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
            return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(fileInfos1.getSize(), fileInfos2.getSize()).toComparison();
        }
    }

    private class TypeComparator implements Comparator<FileInfo> {
        public int compare(FileInfo fileInfos1, FileInfo fileInfos2) {
            return new CompareToBuilder().append(!fileInfos1.getIsDirectory(), !fileInfos2.getIsDirectory()).append(FilenameUtils.getExtension(fileInfos1.getName()), FilenameUtils.getExtension(fileInfos2.getName())).toComparison();
        }
    }
}
