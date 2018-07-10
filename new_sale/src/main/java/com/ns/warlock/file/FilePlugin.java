package com.ns.warlock.file;

import com.ns.warlock.FileInfo;
import com.ns.warlock.Setting;
import com.ns.warlock.util.SettingUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("filePlugin")
public class FilePlugin implements ServletContextAware {

    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * 上传文件
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
     * 获取图片的全路径
     * @param path
     * @return
     */
    public String getUrl(String path) {
        Setting setting = SettingUtils.get();
        return setting.getSiteUrl() + path;
    }


    public List<FileInfo> browser(String path) {
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

}

