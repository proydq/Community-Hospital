package com.proshine.shahecommunityhospital.service;


import com.proshine.shahecommunityhospital.entity.TbTerminal;

import java.util.List;

/**
 * TbTerminal Service接口
 * 
 * @author darkdog
 * @since 2020-03-05
 */
public interface ITbTerminalService {
    
    /**
     * 根据ID获取终端信息
     * 
     * @param id 终端ID
     * @return 终端实体对象，如果不存在则返回null
     */
    TbTerminal getById(String id);

    List<TbTerminal> findByExtraId2(String extraId2);

}