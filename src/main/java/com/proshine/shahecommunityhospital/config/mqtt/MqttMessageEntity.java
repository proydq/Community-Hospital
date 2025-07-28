package com.proshine.shahecommunityhospital.config.mqtt;

import lombok.Data;

@Data
public class MqttMessageEntity {

	private String topic;

	private String content;

}
