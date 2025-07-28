package com.proshine.shahecommunityhospital.repository;

import com.proshine.shahecommunityhospital.entity.RefundRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 退费记录Repository接口
 */
@Repository
public interface RefundRecordRepository extends JpaRepository<RefundRecord, String> {
    
    /**
     * 根据身份证号查询退费记录
     */
    List<RefundRecord> findByIdentityCardNumber(String identityCardNumber);
    
    /**
     * 根据社保号查询退费记录
     */
    List<RefundRecord> findBySocialSecurityNumber(String socialSecurityNumber);
    
    /**
     * 根据状态查询退费记录
     */
    List<RefundRecord> findByStatus(String status);
    
    /**
     * 根据医院ID查询退费记录
     */
    List<RefundRecord> findByAddressId(String addressId);
    
    /**
     * 根据窗口ID查询退费记录
     */
    List<RefundRecord> findByWindowId(String windowId);
    
    /**
     * 根据操作人查询退费记录
     */
    List<RefundRecord> findByOperator(String operator);
    
    /**
     * 根据患者姓名查询退费记录
     */
    List<RefundRecord> findByName(String name);
    
    /**
     * 根据医院ID和窗口ID查询退费记录
     */
    List<RefundRecord> findByAddressIdAndWindowId(String addressId, String windowId);
    
    /**
     * 根据身份证号和状态查询退费记录
     */
    List<RefundRecord> findByIdentityCardNumberAndStatus(String identityCardNumber, String status);
    
    /**
     * 根据时间戳范围查询退费记录
     */
    @Query("SELECT r FROM RefundRecord r WHERE r.createTime BETWEEN :startTime AND :endTime ORDER BY r.createTime DESC")
    List<RefundRecord> findByCreateTimeBetween(@Param("startTime") Long startTime, 
                                              @Param("endTime") Long endTime);
    
    /**
     * 查询待确认的退费记录
     */
    @Query("SELECT r FROM RefundRecord r WHERE r.status = 'PENDING' ORDER BY r.createTime DESC")
    List<RefundRecord> findPendingRefundRecords();
    
    /**
     * 查询已确认的退费记录
     */
    @Query("SELECT r FROM RefundRecord r WHERE r.status = 'CONFIRMED' ORDER BY r.createTime DESC")
    List<RefundRecord> findConfirmedRefundRecords();
    
    /**
     * 根据操作时间范围查询退费记录
     */
    @Query("SELECT r FROM RefundRecord r WHERE r.operateTime BETWEEN :startTime AND :endTime ORDER BY r.operateTime DESC")
    List<RefundRecord> findByOperateTimeBetween(@Param("startTime") String startTime, 
                                               @Param("endTime") String endTime);
    
    /**
     * 根据退费金额范围查询退费记录
     */
    @Query("SELECT r FROM RefundRecord r WHERE r.realRefundAmount BETWEEN :minAmount AND :maxAmount ORDER BY r.realRefundAmount DESC")
    List<RefundRecord> findByRealRefundAmountBetween(@Param("minAmount") BigDecimal minAmount, 
                                                    @Param("maxAmount") BigDecimal maxAmount);
    
    /**
     * 统计某个医院的退费记录数量
     */
    @Query("SELECT COUNT(r) FROM RefundRecord r WHERE r.addressId = :addressId")
    Long countByAddressId(@Param("addressId") String addressId);
    
    /**
     * 统计某个窗口的退费记录数量
     */
    @Query("SELECT COUNT(r) FROM RefundRecord r WHERE r.windowId = :windowId")
    Long countByWindowId(@Param("windowId") String windowId);
    
    /**
     * 统计某个医院的总退费金额
     */
    @Query("SELECT SUM(r.realRefundAmount) FROM RefundRecord r WHERE r.addressId = :addressId AND r.status = 'CONFIRMED'")
    BigDecimal sumRefundAmountByAddressId(@Param("addressId") String addressId);
    
    /**
     * 统计某个窗口的总退费金额
     */
    @Query("SELECT SUM(r.realRefundAmount) FROM RefundRecord r WHERE r.windowId = :windowId AND r.status = 'CONFIRMED'")
    BigDecimal sumRefundAmountByWindowId(@Param("windowId") String windowId);
    
    /**
     * 根据创建时间戳查询指定时间之后的记录
     */
    @Query("SELECT r FROM RefundRecord r WHERE r.createTime >= :timestamp ORDER BY r.createTime DESC")
    List<RefundRecord> findByCreateTimeAfter(@Param("timestamp") Long timestamp);
    
    /**
     * 根据创建时间戳查询指定时间之前的记录
     */
    @Query("SELECT r FROM RefundRecord r WHERE r.createTime <= :timestamp ORDER BY r.createTime DESC")
    List<RefundRecord> findByCreateTimeBefore(@Param("timestamp") Long timestamp);
}