package com.stewart.system.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Stewart
 * @create 2021/1/6
 */
@Component
@Data
@ConfigurationProperties(prefix = "alioss") //读取配置文件里面的属性
public class OssEntity {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
