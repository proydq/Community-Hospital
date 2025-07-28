package com.proshine.shahecommunityhospital.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
/**
 * 退费记录实体类
 * 对应退费按钮点击和确认退费接口
 */
@Entity
@Table(name = "tb_refund_record")
@Data
public class RefundRecord {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 36)
    private String id;
    
    @Column(name = "address_id", nullable = false, length = 50)
    private String addressId;
    
    @Column(name = "address_name", nullable = false, length = 100)
    private String addressName;
    
    @Column(name = "window_id", nullable = false, length = 50)
    private String windowId;
    
    @Column(name = "window_name", nullable = false, length = 100)
    private String windowName;
    
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    
    @Column(name = "age", nullable = false, length = 10)
    private String age;
    
    @Column(name = "sex", nullable = false, length = 10)
    private String sex;
    
    @Column(name = "receivable", nullable = false, precision = 10, scale = 2)
    private BigDecimal receivable;
    
    @Column(name = "real_refund_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal realRefundAmount;
    
    @Column(name = "social_security_number", nullable = false, length = 50)
    private String socialSecurityNumber;
    
    @Column(name = "identity_card_number", nullable = false, length = 20)
    private String identityCardNumber;
    
    @Column(name = "operator", nullable = false, length = 50)
    private String operator;
    
    @Column(name = "operate_time", nullable = false, length = 50)
    private String operateTime;
    
    @Column(name = "status", length = 20)
    private String status; // 状态：PENDING(待确认), CONFIRMED(已确认)
    
    @Column(name = "create_time")
    private Long createTime; // 创建时间戳(毫秒)
    
    @Column(name = "update_time")
    private Long updateTime; // 更新时间戳(毫秒)
    
    @PrePersist
    protected void onCreate() {
        long currentTime = System.currentTimeMillis();
        createTime = currentTime;
        updateTime = currentTime;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updateTime = System.currentTimeMillis();
    }
}