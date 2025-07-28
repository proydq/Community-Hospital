package com.proshine.shahecommunityhospital.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 退费按钮点击请求DTO
 * 对应接口：/thirdpart/medical/refundButtonClick
 */
@Data
public class RefundButtonClickRequest {
    
    /**
     * 医院/店名ID
     */
    private String addressId;
    
    /**
     * 医院/店名
     */
    private String addressName;
    
    /**
     * 窗口id
     */
    private String windowId;
    
    /**
     * 窗口名
     */
    private String windowName;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 年龄
     */
    private String age;
    
    /**
     * 性别
     */
    private String sex;
    
    /**
     * 应收
     */
    private BigDecimal receivable;
    
    /**
     * 实退
     */
    private BigDecimal realRefundAmount;
    
    /**
     * 社保号
     */
    private String socialSecurityNumber;
    
    /**
     * 身份证号
     */
    private String identityCardNumber;
    
    /**
     * 操作人
     */
    private String operator;
    
    /**
     * 操作时间
     */
    private String operateTime;
}