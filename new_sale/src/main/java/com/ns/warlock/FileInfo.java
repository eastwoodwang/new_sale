package com.ns.warlock;

import java.util.Date;

public class FileInfo {

    public enum FileType {

        /** 图片 */
        image,

        /** Flash */
        flash,

        /** 媒体 */
        media,

        /** 文件 */
        file
    }

    public enum OrderType {

        /** 名称 */
        name,

        /** 大小 */
        size,

        /** 类型 */
        type
    }

    /** 名称 */
    private String name;

    /** URL */
    private String url;

    /** 是否为目录 */
    private Boolean isDirectory;

    /** 大小 */
    private Long size;

    /** 最后修改日期 */
    private Date lastModified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(Boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
