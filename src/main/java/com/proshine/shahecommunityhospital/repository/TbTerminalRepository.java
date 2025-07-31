package com.proshine.shahecommunityhospital.repository;

import com.proshine.shahecommunityhospital.entity.TbTerminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TbTerminal Repository接口
 * 
 * @author darkdog
 * @since 2020-03-05
 */
@Repository
public interface TbTerminalRepository extends JpaRepository<TbTerminal, String> {
    // JpaRepository已经提供了基本的CRUD操作
    // 如需自定义查询方法，可以在此添加

    List<TbTerminal> findByExtraId2(String extraId2);

}