package com.ns.warlock.util;

import org.apache.commons.io.FilenameUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.springframework.util.Assert;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片处理工具
 */
public final class ImageUtils {

    /**
     * 处理类型
     */
    private enum Type {

        /** 自动 */
        auto,

        /** JDK */
        jdk,

        /** GraphicsMagick */
        graphicsMagick,

        /** ImageMagick */
        imageMagick
    }

    /** 处理类型 */
    private static Type type = Type.auto;

    /** graphicsMagick 程序路径 graphics是一种图片处理工具 */
    private static String graphicsMagickPath;

    /** imageMagick 程序路径 imageMagick是一种图片处理工具 */
    private static String imageMagickPath;

    /** 背景颜色 */
    private static final Color BACKGROUND_COLOR = Color.white;

    /** 图片质量 （0 - 100）*/
    private static final int DEST_QUALITY = 88;

    static {

        if (graphicsMagickPath == null) {
            //获取操作系统名称
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >=0 ) {
                //获取环境变量内容
                String pathVariable = System.getenv("Path");
                if (pathVariable != null) {
                    //获取所有的对象
                    String[] paths = pathVariable.split(";");
                    for (String path : paths) {
                        File gmFile = new File(path.trim() + "/gm.exe");
                        File gmDisplayFile = new File(path.trim() + "/gmdisplay.exe");
                        if (gmFile.exists() && gmDisplayFile.exists()) {
                            graphicsMagickPath = path.trim();
                            break;
                        }
                    }
                }
            }
        }

        if (imageMagickPath == null) {
           String osName = System.getProperty("os.name").toLowerCase();
           if (osName.indexOf("windows") >= 0) {
               String pathVariable = System.getenv("Path");
               if (pathVariable != null) {
                   String[] paths = pathVariable.split(";");
                   for (String path : paths) {
                       File convertFile = new File(path.trim() + "/convert.exe");
                       File compositeFile = new File(path.trim() + "/composite.exe");
                       if (convertFile.exists() && compositeFile.exists()) {
                           imageMagickPath = path.trim();
                           break;
                       }
                   }
               }
           }
        }

        if (type == Type.auto) {
            try { //获取运行位置并设置采用何种方式压缩
                IMOperation operation = new IMOperation();
                operation.version();
                IdentifyCmd identifyCmd = new IdentifyCmd(true);
                if (graphicsMagickPath != null) {
                    identifyCmd.setSearchPath(graphicsMagickPath);
                }
                identifyCmd.run(operation);
                type = Type.graphicsMagick;
            } catch (Throwable e1) {
                try {
                    IMOperation operation = new IMOperation();
                    operation.version();
                    IdentifyCmd identifyCmd = new IdentifyCmd(true);
                    identifyCmd.run(operation);
                    if (imageMagickPath != null) {
                        identifyCmd.setSearchPath(imageMagickPath);
                    }
                    type = Type.imageMagick;
                } catch (Throwable e2) {
                    type= Type.jdk;
                }
            }
        }

    }

    /**
     * 不可实例化
     */
    private ImageUtils () {
    }

    /**
     * 初始化
     */
    public static void initialize() {
    }

    /**
     * 图片等比缩放
     * @param srcFile
     * @param destFile
     * @param destWidth
     * @param destHeight
     */
    public static void zoom(File srcFile, File destFile, int destWidth, int destHeight) {
        //先检查是否为空
        Assert.notNull(srcFile);
        Assert.notNull(destFile);
        Assert.state(destHeight > 0);
        Assert.state(destWidth > 0);

        if (type == Type.jdk) {
            Graphics2D graphics2D = null; //调用Jwt进行图片处理
            //设置图片输出流
            ImageOutputStream imageOutputStream = null;
            ImageWriter imageWriter = null;

            try {
                //调用ImageIO读取源图片并返回缓冲流
                BufferedImage srcBufferedImage = ImageIO.read(srcFile);
                //获取源图片的高和宽
                int srcWidth = srcBufferedImage.getWidth();
                int srcHeight = srcBufferedImage.getHeight();
                //设置压缩后的高和宽
                int width = destWidth;
                int height = destHeight;

                //数学上面等等比 H1:W1 = H2:W2 虽然结果一致 为什么要这样？
                if (srcHeight > srcWidth) {
                    width = (int) Math.round(((destHeight * 1.0 /srcHeight) * srcWidth));
                } else {
                    height = (int) Math.round(((destWidth * 1.0 / srcWidth) * srcHeight));
                }


                //因为要使用graphics2D (它无构造函数需要对象生成) 故先构建BufferedImage
                BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
                graphics2D = destBufferedImage.createGraphics();
                graphics2D.setBackground(BACKGROUND_COLOR); //设置背景图
                graphics2D.clearRect(0, 0, destWidth, destHeight); //从0.0到destWidth.destHeight通过背景图片填充矩形区域
                graphics2D.drawImage(srcBufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),
                        (destWidth / 2) - (width / 2), (destHeight / 2) - (height / 2), null); //图片内容填充到 X,Y是开始的位置


                imageOutputStream = ImageIO.createImageOutputStream(destFile); //准备写入目标文件
                imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName())).next();
                imageWriter.setOutput(imageOutputStream); //图片流数据写入

                //修改写入参数
                ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
                imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);//设置参数的压缩模式
                imageWriteParam.setCompressionQuality((float)(DEST_QUALITY / 100.0)); //设置压缩比例

                imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
                imageOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (graphics2D != null) {
                    graphics2D.dispose(); //处理并释放使用的
                }

                if (imageWriter != null) {
                    imageWriter.dispose();
                }

                if (imageOutputStream != null) {
                    try {
                        imageOutputStream.close();
                    } catch (IOException e) {
                    }
                }
            }
        } else {
            IMOperation imOperation = new IMOperation();
            imOperation.thumbnail(destWidth, destHeight);
            imOperation.gravity("center");
            imOperation.background(toHexEncoding(BACKGROUND_COLOR));
            imOperation.extent(destWidth, destHeight);
            imOperation.quality((double) DEST_QUALITY);
            imOperation.addImage(srcFile.getPath());
            imOperation.addImage(destFile.getPath());

            if (type == Type.graphicsMagick) {
                ConvertCmd convertCmd = new ConvertCmd(true);
                if (graphicsMagickPath != null) {
                    convertCmd.setSearchPath(graphicsMagickPath);
                }
                try {
                  convertCmd.run(imOperation);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IM4JavaException e) {
                    e.printStackTrace();
                }
            } else {
                ConvertCmd convertCmd = new ConvertCmd(false);
                if (imageMagickPath != null) {
                    convertCmd.setSearchPath(imageMagickPath);
                }

                try {
                    convertCmd.run(imOperation);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IM4JavaException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 转换颜色为十六进制代码
     *
     * @param color
     *           颜色
     * @return  十六进制代码
     */
    private static String toHexEncoding(Color color) {
        String R, G, B;
        StringBuffer stringBuffer = new StringBuffer();
        R = Integer.toHexString(color.getRed());
        G = Integer.toHexString(color.getGreen());
        B = Integer.toHexString(color.getBlue());
        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;
        stringBuffer.append("#");
        stringBuffer.append(R);
        stringBuffer.append(G);
        stringBuffer.append(B);
        return stringBuffer.toString();
    }
}
