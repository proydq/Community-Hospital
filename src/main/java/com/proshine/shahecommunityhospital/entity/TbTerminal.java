package com.proshine.shahecommunityhospital.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 自定义的终端实体类，为了不改变之前的
 * </p>
 * @author darkdog
 * @since 2020-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "tb_terminal")
public class TbTerminal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "cstm_id")
    private String cstmId;

    @Column(name = "terminal_id")
    private String terminalId;

    @Column(name = "extra_id1")
    private String extraId1;

    @Column(name = "extra_id2")
    private String extraId2;

    @Column(name = "terminal_name")
    private String terminalName;

    @Column(name = "terminal_desc")
    private String terminalDesc;

    @Column(name = "cat_id")
    private String catId;

    @Column(name = "create_time", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "program_config", columnDefinition = "TEXT")
    private String programConfig;

    @Column(name = "terminal_config", columnDefinition = "TEXT")
    private String terminalConfig;

    /**
     * 0:离线 1:开机 2:关机
     */
    @Column(name = "online_state")
    private Integer onlineState;

    @Column(name = "logon_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logonTime;

    @Column(name = "logoff_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logoffTime;

    @Column(name = "last_alive_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAliveTime;

    @Column(name = "board_type")
    private String boardType;

    @Column(name = "ipaddr")
    private String ipaddr;

    @Column(name = "display_width")
    private Integer displayWidth;

    @Column(name = "display_height")
    private Integer displayHeight;

    @Column(name = "volume")
    private Integer volume;

    @Column(name = "network_type")
    private Integer networkType;

    /**
     * 1-2-3 ； 1表示daemon 2表示touch程序 3第三方程序
     */
    @Column(name = "app_version")
    private String appVersion;

    @Column(name = "firmware_version")
    private String firmwareVersion;

    @Column(name = "total_space")
    private Long totalSpace;

    @Column(name = "free_space")
    private Long freeSpace;

    /**
     * 节目的版本
     */
    @Column(name = "program_version")
    private String programVersion;

    @Column(name = "device_model_id")
    private String deviceModelId;

    @Column(name = "classroom_name")
    private String classroomName;

    @Column(name = "class_name")
    private String className;

    /**
     * 门禁常开常闭状态
     */
    @Column(name = "entrance_open_status")
    private Integer entranceOpenStatus;

    /**
     * 门禁出入类型
     */
    @Column(name = "entrance_type")
    private Integer entranceType;

    /**
     * 在实体保存之前自动设置创建时间
     */
    @PrePersist
    protected void onCreate() {
        if (createTime == null) {
            createTime = new Date();
        }
    }
}