package com.ns.warlock.common;

public final class CommonAttributes {

    /** 日期格式配比 */
    public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

    /** 存放当前项目路径下 */
    public static final String IMAGE_PATH = "/upload/image/";

    /** 项目配置路径 */
    public static final String PROJECT_XML_PATH = "/project.xml";

    /**
     * 不可实例化
     */
    private CommonAttributes() {
    }
}
