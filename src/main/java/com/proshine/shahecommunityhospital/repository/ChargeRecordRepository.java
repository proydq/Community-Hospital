package com.proshine.shahecommunityhospital.repository;

import com.proshine.shahecommunityhospital.entity.ChargeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 收费记录Repository接口
 */
@Repository
public interface ChargeRecordRepository extends JpaRepository<ChargeRecord, String> {
    
    /**
     * 根据身份证号查询收费记录
     */
    List<ChargeRecord> findByIdentityCardNumber(String identityCardNumber);
    
    /**
     * 根据社保号查询收费记录
     */
    List<ChargeRecord> findBySocialSecurityNumber(String socialSecurityNumber);
    
    /**
     * 根据状态查询收费记录
     */
    List<ChargeRecord> findByStatus(String status);
    
    /**
     * 根据医院ID查询收费记录
     */
    List<ChargeRecord> findByAddressId(String addressId);
    
    /**
     * 根据窗口ID查询收费记录
     */
    List<ChargeRecord> findByWindowId(String windowId);
    
    /**
     * 根据操作人查询收费记录
     */
    List<ChargeRecord> findByOperator(String operator);
    
    /**
     * 根据患者姓名查询收费记录
     */
    List<ChargeRecord> findByName(String name);
    
    /**
     * 根据医院ID和窗口ID查询收费记录
     */
    List<ChargeRecord> findByAddressIdAndWindowId(String addressId, String windowId);
    
    /**
     * 根据身份证号和状态查询收费记录
     */
    List<ChargeRecord> findByIdentityCardNumberAndStatus(String identityCardNumber, String status);
    
    /**
     * 根据时间戳范围查询收费记录
     */
    @Query("SELECT c FROM ChargeRecord c WHERE c.createTime BETWEEN :startTime AND :endTime ORDER BY c.createTime DESC")
    List<ChargeRecord> findByCreateTimeBetween(@Param("startTime") Long startTime, 
                                              @Param("endTime") Long endTime);
    
    /**
     * 查询待确认的收费记录
     */
    @Query("SELECT c FROM ChargeRecord c WHERE c.status = 'PENDING' ORDER BY c.createTime DESC")
    List<ChargeRecord> findPendingChargeRecords();
    
    /**
     * 查询已确认的收费记录
     */
    @Query("SELECT c FROM ChargeRecord c WHERE c.status = 'CONFIRMED' ORDER BY c.createTime DESC")
    List<ChargeRecord> findConfirmedChargeRecords();
    
    /**
     * 根据操作时间范围查询收费记录
     */
    @Query("SELECT c FROM ChargeRecord c WHERE c.operateTime BETWEEN :startTime AND :endTime ORDER BY c.operateTime DESC")
    List<ChargeRecord> findByOperateTimeBetween(@Param("startTime") String startTime, 
                                               @Param("endTime") String endTime);
    
    /**
     * 统计某个医院的收费记录数量
     */
    @Query("SELECT COUNT(c) FROM ChargeRecord c WHERE c.addressId = :addressId")
    Long countByAddressId(@Param("addressId") String addressId);
    
    /**
     * 统计某个窗口的收费记录数量
     */
    @Query("SELECT COUNT(c) FROM ChargeRecord c WHERE c.windowId = :windowId")
    Long countByWindowId(@Param("windowId") String windowId);
    
    /**
     * 根据创建时间戳查询指定时间之后的记录
     */
    @Query("SELECT c FROM ChargeRecord c WHERE c.createTime >= :timestamp ORDER BY c.createTime DESC")
    List<ChargeRecord> findByCreateTimeAfter(@Param("timestamp") Long timestamp);
    
    /**
     * 根据创建时间戳查询指定时间之前的记录
     */
    @Query("SELECT c FROM ChargeRecord c WHERE c.createTime <= :timestamp ORDER BY c.createTime DESC")
    List<ChargeRecord> findByCreateTimeBefore(@Param("timestamp") Long timestamp);
}