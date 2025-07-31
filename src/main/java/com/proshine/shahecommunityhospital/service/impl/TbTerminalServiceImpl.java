package com.proshine.shahecommunityhospital.service.impl;

import com.proshine.shahecommunityhospital.entity.TbTerminal;
import com.proshine.shahecommunityhospital.repository.TbTerminalRepository;
import com.proshine.shahecommunityhospital.service.ITbTerminalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * TbTerminal Service实现类
 * 
 * @author darkdog
 * @since 2020-03-05
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TbTerminalServiceImpl implements ITbTerminalService {
    
    private final TbTerminalRepository tbTerminalRepository;
    
    /**
     * 根据ID获取终端信息
     * 
     * @param id 终端ID
     * @return 终端实体对象，如果不存在则返回null
     */
    @Override
    public TbTerminal getById(String id) {
        return tbTerminalRepository.findById(id).orElse(null);
    }

    @Override
    public List<TbTerminal> findByExtraId2(String extraId2) {
        return tbTerminalRepository.findByExtraId2(extraId2);
    }
}