package com.soubao.common.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "shop")
@PropertySource("classpath:shopConstant.properties")
@Setter
@Getter
public class ShopConstant {
    private String goodsNoOriginalImage;
    private Integer orderGoodsBarCodeStart;
    private Integer orderGoodsBarCodeEnd;
    private String imageUpload;
    private Long imageMaxSize;
    private List<String> imageType;
    private String miniAppOauth;
    private Integer cartMostNum;
    private List<String> videoType;
}