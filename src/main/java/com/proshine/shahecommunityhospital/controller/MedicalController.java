package com.proshine.shahecommunityhospital.controller;

import com.proshine.shahecommunityhospital.common.ResponseEntity;
import com.proshine.shahecommunityhospital.dto.*;
import com.proshine.shahecommunityhospital.entity.ChargeRecord;
import com.proshine.shahecommunityhospital.entity.RefundRecord;
import com.proshine.shahecommunityhospital.service.MedicalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    
    // ================== 查询相关接口 ==================
    
    /**
     * 根据身份证号查询收费记录
     * 
     * @param identityCardNumber 身份证号
     * @return 收费记录列表
     */
    @GetMapping("/charge/records/{identityCardNumber}")
    public ResponseEntity<List<ChargeRecord>> getChargeRecordsByIdentityCard(
            @PathVariable String identityCardNumber) {
        try {
            log.info("查询收费记录 - 身份证号: {}", identityCardNumber);
            
            List<ChargeRecord> records = medicalService.getChargeRecordsByIdentityCard(identityCardNumber);
            
            log.info("查询收费记录成功 - 身份证号: {}, 记录数: {}", identityCardNumber, records.size());
            return ResponseEntity.success(records);
        } catch (Exception e) {
            log.error("查询收费记录失败 - 身份证号: {}", identityCardNumber, e);
            return ResponseEntity.fail("查询收费记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据身份证号查询退费记录
     * 
     * @param identityCardNumber 身份证号
     * @return 退费记录列表
     */
    @GetMapping("/refund/records/{identityCardNumber}")
    public ResponseEntity<List<RefundRecord>> getRefundRecordsByIdentityCard(
            @PathVariable String identityCardNumber) {
        try {
            log.info("查询退费记录 - 身份证号: {}", identityCardNumber);
            
            List<RefundRecord> records = medicalService.getRefundRecordsByIdentityCard(identityCardNumber);
            
            log.info("查询退费记录成功 - 身份证号: {}, 记录数: {}", identityCardNumber, records.size());
            return ResponseEntity.success(records);
        } catch (Exception e) {
            log.error("查询退费记录失败 - 身份证号: {}", identityCardNumber, e);
            return ResponseEntity.fail("查询退费记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询待确认的收费记录
     * 
     * @return 待确认收费记录列表
     */
    @GetMapping("/charge/pending")
    public ResponseEntity<List<ChargeRecord>> getPendingChargeRecords() {
        try {
            log.info("查询待确认收费记录");
            
            List<ChargeRecord> records = medicalService.getPendingChargeRecords();
            
            log.info("查询待确认收费记录成功 - 记录数: {}", records.size());
            return ResponseEntity.success(records);
        } catch (Exception e) {
            log.error("查询待确认收费记录失败", e);
            return ResponseEntity.fail("查询待确认收费记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询待确认的退费记录
     * 
     * @return 待确认退费记录列表
     */
    @GetMapping("/refund/pending")
    public ResponseEntity<List<RefundRecord>> getPendingRefundRecords() {
        try {
            log.info("查询待确认退费记录");
            
            List<RefundRecord> records = medicalService.getPendingRefundRecords();
            
            log.info("查询待确认退费记录成功 - 记录数: {}", records.size());
            return ResponseEntity.success(records);
        } catch (Exception e) {
            log.error("查询待确认退费记录失败", e);
            return ResponseEntity.fail("查询待确认退费记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据医院ID查询收费记录
     * 
     * @param addressId 医院ID
     * @return 收费记录列表
     */
    @GetMapping("/charge/hospital/{addressId}")
    public ResponseEntity<List<ChargeRecord>> getChargeRecordsByHospital(
            @PathVariable String addressId) {
        try {
            log.info("查询医院收费记录 - 医院ID: {}", addressId);
            
            List<ChargeRecord> records = medicalService.getChargeRecordsByAddressId(addressId);
            
            log.info("查询医院收费记录成功 - 医院ID: {}, 记录数: {}", addressId, records.size());
            return ResponseEntity.success(records);
        } catch (Exception e) {
            log.error("查询医院收费记录失败 - 医院ID: {}", addressId, e);
            return ResponseEntity.fail("查询医院收费记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据医院ID查询退费记录
     * 
     * @param addressId 医院ID
     * @return 退费记录列表
     */
    @GetMapping("/refund/hospital/{addressId}")
    public ResponseEntity<List<RefundRecord>> getRefundRecordsByHospital(
            @PathVariable String addressId) {
        try {
            log.info("查询医院退费记录 - 医院ID: {}", addressId);
            
            List<RefundRecord> records = medicalService.getRefundRecordsByAddressId(addressId);
            
            log.info("查询医院退费记录成功 - 医院ID: {}, 记录数: {}", addressId, records.size());
            return ResponseEntity.success(records);
        } catch (Exception e) {
            log.error("查询医院退费记录失败 - 医院ID: {}", addressId, e);
            return ResponseEntity.fail("查询医院退费记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 系统健康检查接口
     * 
     * @return 系统状态
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        log.info("系统健康检查");
        return ResponseEntity.success("医疗收费系统运行正常");
    }
}