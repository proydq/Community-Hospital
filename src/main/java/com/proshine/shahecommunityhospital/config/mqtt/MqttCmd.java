package com.proshine.shahecommunityhospital.config.mqtt;

import com.proshine.shahecommunityhospital.utils.PrimaryKeyUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author lenovo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MqttCmd {

    /**
     * 消息ID, 唯一标示符
     */
    private String id   = "";
    /**
     * 服务类型
     */
    private String service  = "";
    /**
     * 操作类型
     */
    private String operation    = "";


    public static MqttCmd create(String service, String operation) {
        return new MqttCmd(PrimaryKeyUtil.generatePrimary(true), service, operation, null);
    }

    /**
     * 携带的参数列表
     */
    @SuppressWarnings("rawtypes")
    private Map params  = null;
    
    
    @SuppressWarnings("rawtypes")
    public static MqttCmd create(String service, String operation, Map params) {
        return new MqttCmd(PrimaryKeyUtil.generatePrimary(true), service, operation, params);
    }
    
}
