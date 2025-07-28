package com.proshine.shahecommunityhospital.controller;

import com.proshine.shahecommunityhospital.common.ResponseEntity;
import com.proshine.shahecommunityhospital.dto.*;
import com.proshine.shahecommunityhospital.service.MedicalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 医疗收费系统控制器
 * 提供收费和退费相关的API接口
 * 
 * @author 沙河社区医院
 */
@RestController
@RequestMapping("/thirdpart/medical")
@Slf4j
@CrossOrigin(origins = "*")
public class MedicalController {
    
    @Autowired
    private MedicalService medicalService;
    
    /**
     * 收费按钮点击接口
     * 当点击收费按钮时接收所传参数
     * 
     * @param request 收费按钮点击请求参数
     * @return 统一返回结果
     */
    @PostMapping("/chargeButtonClick")
    public ResponseEntity<Void> chargeButtonClick(@RequestBody @Valid ChargeButtonClickRequest request) {
        try {
            log.info("收费按钮点击 - 患者: {}, 身份证号: {}, 应收: {}, 实收: {}", 
                    request.getName(), 
                    request.getIdentityCardNumber(),
                    request.getReceivable(),
                    request.getPaidUp());
            
            medicalService.handleChargeButtonClick(request);
            
            log.info("收费按钮点击处理成功 - 患者: {}", request.getName());
            return ResponseEntity.success(null);
        } catch (IllegalArgumentException e) {
            log.warn("收费按钮点击参数错误 - 患者: {}, 错误: {}", request.getName(), e.getMessage());
            return ResponseEntity.fail(e.getMessage());
        } catch (Exception e) {
            log.error("收费按钮点击处理失败 - 患者: {}", request.getName(), e);
            return ResponseEntity.fail("收费按钮点击处理失败: " + e.getMessage());
        }
    }
    
    /**
     * 确认收费接口
     * 当点击收费的确认按钮时接收所传参数
     * 
     * @param request 确认收费请求参数
     * @return 统一返回结果
     */
    @PostMapping("/confirmPayment")
    public ResponseEntity<Void> confirmPayment(@RequestBody @Valid ConfirmPaymentRequest request) {
        try {
            log.info("确认收费 - 患者: {}, 身份证号: {}, 应收: {}, 实收: {}", 
                    request.getName(), 
                    request.getIdentityCardNumber(),
                    request.getReceivable(),
                    request.getPaidUp());
            
            medicalService.confirmPayment(request);
            
            log.info("确认收费处理成功 - 患者: {}", request.getName());
            return ResponseEntity.success(null);
        } catch (IllegalArgumentException e) {
            log.warn("确认收费参数错误 - 患者: {}, 错误: {}", request.getName(), e.getMessage());
            return ResponseEntity.fail(e.getMessage());
        } catch (Exception e) {
            log.error("确认收费处理失败 - 患者: {}", request.getName(), e);
            return ResponseEntity.fail("确认收费处理失败: " + e.getMessage());
        }
    }
    
    /**
     * 退费按钮点击接口
     * 当点击退费按钮时接收所传参数
     * 
     * @param request 退费按钮点击请求参数
     * @return 统一返回结果
     */
    @PostMapping("/refundButtonClick")
    public ResponseEntity<Void> refundButtonClick(@RequestBody @Valid RefundButtonClickRequest request) {
        try {
            log.info("退费按钮点击 - 患者: {}, 身份证号: {}, 应收: {}, 实退: {}", 
                    request.getName(), 
                    request.getIdentityCardNumber(),
                    request.getReceivable(),
                    request.getRealRefundAmount());
            
            medicalService.handleRefundButtonClick(request);
            
            log.info("退费按钮点击处理成功 - 患者: {}", request.getName());
            return ResponseEntity.success(null);
        } catch (IllegalArgumentException e) {
            log.warn("退费按钮点击参数错误 - 患者: {}, 错误: {}", request.getName(), e.getMessage());
            return ResponseEntity.fail(e.getMessage());
        } catch (Exception e) {
            log.error("退费按钮点击处理失败 - 患者: {}", request.getName(), e);
            return ResponseEntity.fail("退费按钮点击处理失败: " + e.getMessage());
        }
    }
    
    /**
     * 确认退费接口
     * 当点击退费的确认按钮时接收所传参数
     * 
     * @param request 确认退费请求参数
     * @return 统一返回结果
     */
    @PostMapping("/confirmRefund")
    public ResponseEntity<Void> confirmRefund(@RequestBody @Valid ConfirmRefundRequest request) {
        try {
            log.info("确认退费 - 患者: {}, 身份证号: {}, 应收: {}, 实退: {}", 
                    request.getName(), 
                    request.getIdentityCardNumber(),
                    request.getReceivable(),
                    request.getRealRefundAmount());
            
            medicalService.confirmRefund(request);
            
            log.info("确认退费处理成功 - 患者: {}", request.getName());
            return ResponseEntity.success(null);
        } catch (IllegalArgumentException e) {
            log.warn("确认退费参数错误 - 患者: {}, 错误: {}", request.getName(), e.getMessage());
            return ResponseEntity.fail(e.getMessage());
        } catch (Exception e) {
            log.error("确认退费处理失败 - 患者: {}", request.getName(), e);
            return ResponseEntity.fail("确认退费处理失败: " + e.getMessage());
        }
    }
}