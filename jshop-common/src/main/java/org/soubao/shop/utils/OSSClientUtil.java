package org.soubao.shop.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.soubao.starter.common.ResultEnum;
import org.soubao.shop.exception.ShopException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@AllArgsConstructor
public class OSSClientUtil {

    private final String accessKeyId;
    private final String accessKeySecret;
    private final String endpoint;
    private final String bucketName;
    private final String filePath;//文件上传目录

    /**
     * 上传图片
     *
     * @param multipartFile
     * @return
     */
    public String uploadImgToOss(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        assert originalFilename != null;
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        String fileName = new Random().nextInt(10000) + System.currentTimeMillis() + fileSuffix;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            this.uploadFileToOSS(inputStream, fileName);
            return fileName;
        } catch (Exception e) {
            throw new ShopException(ResultEnum.UPLOAD_FAIL_OSS_ERROR);
        }
    }

    /**
     * 上传图片获取fileUrl
     *
     * @param inputStream
     * @param fileName
     * @return
     */
    private String uploadFileToOSS(InputStream inputStream, String fileName) {
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            PutObjectResult putResult = oss.putObject(bucketName, filePath + fileName, inputStream, objectMetadata);
            return putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param fileNameExtension 文件后缀
     * @return String
     */
    public String getContentType(String fileNameExtension) {
        if (fileNameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (fileNameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (fileNameExtension.equalsIgnoreCase(".jpeg") ||
                fileNameExtension.equalsIgnoreCase(".jpg") ||
                fileNameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (fileNameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (fileNameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (fileNameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (fileNameExtension.equalsIgnoreCase(".pptx") ||
                fileNameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (fileNameExtension.equalsIgnoreCase(".docx") ||
                fileNameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (fileNameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (fileNameExtension.equalsIgnoreCase(".mp4")) {
            return "video/mpeg4";
        }
        if (fileNameExtension.equalsIgnoreCase(".ogg")) {
            return "application/ogg";
        }
        if (fileNameExtension.equalsIgnoreCase(".flv")) {
            return "video/x-flv";
        }
        if (fileNameExtension.equalsIgnoreCase(".avi")) {
            return "video/x-msvideo";
        }
        if (fileNameExtension.equalsIgnoreCase(".wmv")) {
            return "video/x-ms-wmv";
        }
        if (fileNameExtension.equalsIgnoreCase(".rmvb")) {
            return "application/vnd.rn-realmedia";
        }
        if (fileNameExtension.equalsIgnoreCase(".mov")) {
            return "video/quicktime";
        }
        throw new ShopException(ResultEnum.FILE_TYPE_ERROR);
    }

    /**
     * 获取图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            log.info("split:{}", split);
            return this.getUrl(this.filePath + split[split.length - 1]);
        }
        return "";
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        URL url = oss.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString().substring(0, url.toString().indexOf("?"));
        }
        return null;
    }

    /**
     * 多图片上传
     *
     * @param multipartFiles
     * @return
     */
    public String uploadImageList(List<MultipartFile> multipartFiles) {
        String fileUrl = "";
        String str = "";
        StringBuilder photoUrl = new StringBuilder();
        for (MultipartFile file : multipartFiles) {
            fileUrl = uploadImgToOss(file);
            str = getImgUrl(fileUrl);
            if (photoUrl.length() == 0) {
                photoUrl = new StringBuilder(str);
            } else {
                photoUrl.append(",").append(str);
            }
        }
        return photoUrl.toString().trim();
    }

    /**
     * 单个图片上传
     *
     * @param multipartFile
     * @return
     */
    public String uploadImage(MultipartFile multipartFile) {
        String fileUrl = uploadImgToOss(multipartFile);
        String str = getImgUrl(fileUrl);
        return str.trim();
    }

    public String uploadVideo(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        assert originalFilename != null;
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        String fileName = new Random().nextInt(10000) + System.currentTimeMillis() + fileSuffix;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            this.uploadFileToOSS(inputStream, fileName);
            return getUrl(filePath + fileName);
        } catch (Exception e) {
            throw new ShopException(ResultEnum.UPLOAD_FAIL_OSS_ERROR);
        }
    }
}
