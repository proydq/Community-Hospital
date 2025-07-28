package com.proshine.shahecommunityhospital.service;

import com.proshine.shahecommunityhospital.dto.*;
import com.proshine.shahecommunityhospital.entity.ChargeRecord;
import com.proshine.shahecommunityhospital.entity.RefundRecord;
import com.proshine.shahecommunityhospital.repository.ChargeRecordRepository;
import com.proshine.shahecommunityhospital.repository.RefundRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 医疗收费服务类
 * 处理收费和退费相关的业务逻辑
 */
@Service
@Slf4j
@Transactional
public class MedicalService {
    
    @Autowired
    private ChargeRecordRepository chargeRecordRepository;
    
    @Autowired
    private RefundRecordRepository refundRecordRepository;
    
    /**
     * 处理收费按钮点击事件
     * 对应接口：/thirdpart/medical/chargeButtonClick
     * 
     * @param request 收费按钮点击请求DTO
     */
    public void handleChargeButtonClick(ChargeButtonClickRequest request) {
        try {
            log.info("处理收费按钮点击事件，患者：{}，身份证号：{}", request.getName(), request.getIdentityCardNumber());
            
            // 参数校验
            validateChargeRequest(request);
            
            // 创建收费记录
            ChargeRecord chargeRecord = new ChargeRecord();
            BeanUtils.copyProperties(request, chargeRecord);
            chargeRecord.setStatus("PENDING"); // 设置为待确认状态
            
            chargeRecordRepository.save(chargeRecord);
            
            log.info("收费记录创建成功，记录ID：{}", chargeRecord.getId());
        } catch (Exception e) {
            log.error("处理收费按钮点击事件失败，患者：{}", request.getName(), e);
            throw new RuntimeException("处理收费按钮点击事件失败：" + e.getMessage());
        }
    }
    
    /**
     * 确认收费
     * 对应接口：/thirdpart/medical/confirmPayment
     * 
     * @param request 确认收费请求DTO
     */
    public void confirmPayment(ConfirmPaymentRequest request) {
        try {
            log.info("确认收费，患者：{}，身份证号：{}", request.getName(), request.getIdentityCardNumber());
            
            // 参数校验
            validatePaymentRequest(request);
            
            // 查找对应的待确认收费记录
            List<ChargeRecord> pendingRecords = chargeRecordRepository
                .findByIdentityCardNumberAndStatus(request.getIdentityCardNumber(), "PENDING");
            
            ChargeRecord chargeRecord;
            if (!pendingRecords.isEmpty()) {
                // 更新最新的待确认记录
                chargeRecord = pendingRecords.get(pendingRecords.size() - 1);
                BeanUtils.copyProperties(request, chargeRecord, "id", "createTime");
                chargeRecord.setStatus("CONFIRMED");
                log.info("更新收费记录确认状态，记录ID：{}", chargeRecord.getId());
            } else {
                // 如果没有找到待确认记录，创建新的确认记录
                chargeRecord = new ChargeRecord();
                BeanUtils.copyProperties(request, chargeRecord);
                chargeRecord.setStatus("CONFIRMED");
                log.info("创建新的确认收费记录");
            }
            
            chargeRecordRepository.save(chargeRecord);
            log.info("收费确认完成，记录ID：{}", chargeRecord.getId());
        } catch (Exception e) {
            log.error("确认收费失败，患者：{}", request.getName(), e);
            throw new RuntimeException("确认收费失败：" + e.getMessage());
        }
    }
    
    /**
     * 处理退费按钮点击事件
     * 对应接口：/thirdpart/medical/refundButtonClick
     * 
     * @param request 退费按钮点击请求DTO
     */
    public void handleRefundButtonClick(RefundButtonClickRequest request) {
        try {
            log.info("处理退费按钮点击事件，患者：{}，身份证号：{}", request.getName(), request.getIdentityCardNumber());
            
            // 参数校验
            validateRefundButtonRequest(request);
            
            // 创建退费记录
            RefundRecord refundRecord = new RefundRecord();
            BeanUtils.copyProperties(request, refundRecord);
            refundRecord.setStatus("PENDING"); // 设置为待确认状态
            
            refundRecordRepository.save(refundRecord);
            
            log.info("退费记录创建成功，记录ID：{}", refundRecord.getId());
        } catch (Exception e) {
            log.error("处理退费按钮点击事件失败，患者：{}", request.getName(), e);
            throw new RuntimeException("处理退费按钮点击事件失败：" + e.getMessage());
        }
    }
    
    /**
     * 确认退费
     * 对应接口：/thirdpart/medical/confirmRefund
     * 
     * @param request 确认退费请求DTO
     */
    public void confirmRefund(ConfirmRefundRequest request) {
        try {
            log.info("确认退费，患者：{}，身份证号：{}", request.getName(), request.getIdentityCardNumber());
            
            // 参数校验
            validateConfirmRefundRequest(request);
            
            // 查找对应的待确认退费记录
            List<RefundRecord> pendingRecords = refundRecordRepository
                .findByIdentityCardNumberAndStatus(request.getIdentityCardNumber(), "PENDING");
            
            RefundRecord refundRecord;
            if (!pendingRecords.isEmpty()) {
                // 更新最新的待确认记录
                refundRecord = pendingRecords.get(pendingRecords.size() - 1);
                BeanUtils.copyProperties(request, refundRecord, "id", "createTime");
                refundRecord.setStatus("CONFIRMED");
                log.info("更新退费记录确认状态，记录ID：{}", refundRecord.getId());
            } else {
                // 如果没有找到待确认记录，创建新的确认记录
                refundRecord = new RefundRecord();
                BeanUtils.copyProperties(request, refundRecord);
                refundRecord.setStatus("CONFIRMED");
                log.info("创建新的确认退费记录");
            }
            
            refundRecordRepository.save(refundRecord);
            log.info("退费确认完成，记录ID：{}", refundRecord.getId());
        } catch (Exception e) {
            log.error("确认退费失败，患者：{}", request.getName(), e);
            throw new RuntimeException("确认退费失败：" + e.getMessage());
        }
    }
    
    /**
     * 收费请求参数校验
     */
    private void validateChargeRequest(ChargeButtonClickRequest request) {
        if (!StringUtils.hasText(request.getAddressId())) {
            throw new IllegalArgumentException("医院/店名ID不能为空");
        }
        if (!StringUtils.hasText(request.getAddressName())) {
            throw new IllegalArgumentException("医院/店名不能为空");
        }
        if (!StringUtils.hasText(request.getWindowId())) {
            throw new IllegalArgumentException("窗口ID不能为空");
        }
        if (!StringUtils.hasText(request.getWindowName())) {
            throw new IllegalArgumentException("窗口名不能为空");
        }
        if (!StringUtils.hasText(request.getName())) {
            throw new IllegalArgumentException("患者姓名不能为空");
        }
        if (!StringUtils.hasText(request.getAge())) {
            throw new IllegalArgumentException("患者年龄不能为空");
        }
        if (!StringUtils.hasText(request.getSex())) {
            throw new IllegalArgumentException("患者性别不能为空");
        }
        if (request.getReceivable() == null || request.getReceivable().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("应收金额不能为空且不能为负数");
        }
        if (request.getPaidUp() == null || request.getPaidUp().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("实收金额不能为空且不能为负数");
        }
        if (request.getChange() == null || request.getChange().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("找零金额不能为空且不能为负数");
        }
        if (!StringUtils.hasText(request.getSocialSecurityNumber())) {
            throw new IllegalArgumentException("社保号不能为空");
        }
        if (!StringUtils.hasText(request.getIdentityCardNumber())) {
            throw new IllegalArgumentException("身份证号不能为空");
        }
        if (!StringUtils.hasText(request.getOperator())) {
            throw new IllegalArgumentException("操作人不能为空");
        }
        if (!StringUtils.hasText(request.getOperateTime())) {
            throw new IllegalArgumentException("操作时间不能为空");
        }
    }
    
    /**
     * 确认收费请求参数校验
     */
    private void validatePaymentRequest(ConfirmPaymentRequest request) {
        if (!StringUtils.hasText(request.getAddressId())) {
            throw new IllegalArgumentException("医院/店名ID不能为空");
        }
        if (!StringUtils.hasText(request.getAddressName())) {
            throw new IllegalArgumentException("医院/店名不能为空");
        }
        if (!StringUtils.hasText(request.getWindowId())) {
            throw new IllegalArgumentException("窗口ID不能为空");
        }
        if (!StringUtils.hasText(request.getWindowName())) {
            throw new IllegalArgumentException("窗口名不能为空");
        }
        if (!StringUtils.hasText(request.getName())) {
            throw new IllegalArgumentException("患者姓名不能为空");
        }
        if (!StringUtils.hasText(request.getAge())) {
            throw new IllegalArgumentException("患者年龄不能为空");
        }
        if (!StringUtils.hasText(request.getSex())) {
            throw new IllegalArgumentException("患者性别不能为空");
        }
        if (request.getReceivable() == null || request.getReceivable().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("应收金额不能为空且不能为负数");
        }
        if (request.getPaidUp() == null || request.getPaidUp().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("实收金额不能为空且不能为负数");
        }
        if (request.getChange() == null || request.getChange().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("找零金额不能为空且不能为负数");
        }
        if (!StringUtils.hasText(request.getSocialSecurityNumber())) {
            throw new IllegalArgumentException("社保号不能为空");
        }
        if (!StringUtils.hasText(request.getIdentityCardNumber())) {
            throw new IllegalArgumentException("身份证号不能为空");
        }
        if (!StringUtils.hasText(request.getOperator())) {
            throw new IllegalArgumentException("操作人不能为空");
        }
        if (!StringUtils.hasText(request.getOperateTime())) {
            throw new IllegalArgumentException("操作时间不能为空");
        }
    }
    
    /**
     * 退费按钮请求参数校验
     */
    private void validateRefundButtonRequest(RefundButtonClickRequest request) {
        if (!StringUtils.hasText(request.getAddressId())) {
            throw new IllegalArgumentException("医院/店名ID不能为空");
        }
        if (!StringUtils.hasText(request.getAddressName())) {
            throw new IllegalArgumentException("医院/店名不能为空");
        }
        if (!StringUtils.hasText(request.getWindowId())) {
            throw new IllegalArgumentException("窗口ID不能为空");
        }
        if (!StringUtils.hasText(request.getWindowName())) {
            throw new IllegalArgumentException("窗口名不能为空");
        }
        if (!StringUtils.hasText(request.getName())) {
            throw new IllegalArgumentException("患者姓名不能为空");
        }
        if (!StringUtils.hasText(request.getAge())) {
            throw new IllegalArgumentException("患者年龄不能为空");
        }
        if (!StringUtils.hasText(request.getSex())) {
            throw new IllegalArgumentException("患者性别不能为空");
        }
        if (request.getReceivable() == null || request.getReceivable().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("应收金额不能为空且不能为负数");
        }
        if (request.getRealRefundAmount() == null || request.getRealRefundAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("实退金额不能为空且不能为负数");
        }
        if (!StringUtils.hasText(request.getSocialSecurityNumber())) {
            throw new IllegalArgumentException("社保号不能为空");
        }
        if (!StringUtils.hasText(request.getIdentityCardNumber())) {
            throw new IllegalArgumentException("身份证号不能为空");
        }
        if (!StringUtils.hasText(request.getOperator())) {
            throw new IllegalArgumentException("操作人不能为空");
        }
        if (!StringUtils.hasText(request.getOperateTime())) {
            throw new IllegalArgumentException("操作时间不能为空");
        }
    }
    
    /**
     * 确认退费请求参数校验
     */
    private void validateConfirmRefundRequest(ConfirmRefundRequest request) {
        if (!StringUtils.hasText(request.getAddressId())) {
            throw new IllegalArgumentException("医院/店名ID不能为空");
        }
        if (!StringUtils.hasText(request.getAddressName())) {
            throw new IllegalArgumentException("医院/店名不能为空");
        }
        if (!StringUtils.hasText(request.getWindowId())) {
            throw new IllegalArgumentException("窗口ID不能为空");
        }
        if (!StringUtils.hasText(request.getWindowName())) {
            throw new IllegalArgumentException("窗口名不能为空");
        }
        if (!StringUtils.hasText(request.getName())) {
            throw new IllegalArgumentException("患者姓名不能为空");
        }
        if (!StringUtils.hasText(request.getAge())) {
            throw new IllegalArgumentException("患者年龄不能为空");
        }
        if (!StringUtils.hasText(request.getSex())) {
            throw new IllegalArgumentException("患者性别不能为空");
        }
        if (request.getReceivable() == null || request.getReceivable().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("应收金额不能为空且不能为负数");
        }
        if (request.getRealRefundAmount() == null || request.getRealRefundAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("实退金额不能为空且不能为负数");
        }
        if (!StringUtils.hasText(request.getSocialSecurityNumber())) {
            throw new IllegalArgumentException("社保号不能为空");
        }
        if (!StringUtils.hasText(request.getIdentityCardNumber())) {
            throw new IllegalArgumentException("身份证号不能为空");
        }
        if (!StringUtils.hasText(request.getOperator())) {
            throw new IllegalArgumentException("操作人不能为空");
        }
        if (!StringUtils.hasText(request.getOperateTime())) {
            throw new IllegalArgumentException("操作时间不能为空");
        }
    }
    
    // ================== 查询相关方法 ==================
    
    /**
     * 根据身份证号查询收费记录
     */
    public List<ChargeRecord> getChargeRecordsByIdentityCard(String identityCardNumber) {
        return chargeRecordRepository.findByIdentityCardNumber(identityCardNumber);
    }
    
    /**
     * 根据身份证号查询退费记录
     */
    public List<RefundRecord> getRefundRecordsByIdentityCard(String identityCardNumber) {
        return refundRecordRepository.findByIdentityCardNumber(identityCardNumber);
    }
    
    /**
     * 查询待确认的收费记录
     */
    public List<ChargeRecord> getPendingChargeRecords() {
        return chargeRecordRepository.findPendingChargeRecords();
    }
    
    /**
     * 查询待确认的退费记录
     */
    public List<RefundRecord> getPendingRefundRecords() {
        return refundRecordRepository.findPendingRefundRecords();
    }
    
    /**
     * 根据医院ID查询收费记录
     */
    public List<ChargeRecord> getChargeRecordsByAddressId(String addressId) {
        return chargeRecordRepository.findByAddressId(addressId);
    }
    
    /**
     * 根据医院ID查询退费记录
     */
    public List<RefundRecord> getRefundRecordsByAddressId(String addressId) {
        return refundRecordRepository.findByAddressId(addressId);
    }
}