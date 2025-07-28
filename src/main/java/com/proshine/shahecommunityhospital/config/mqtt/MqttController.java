package com.proshine.shahecommunityhospital.config.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lenovo
 */
@RestController
@RequestMapping(value="/mqtt")
public class MqttController {

	@Autowired
	private MqttGateway mqttGateway;

	@PostMapping("/send")
	public String send(@RequestBody MqttMessageEntity message) {
		// 发送消息到指定主题
		//mqttGateway.sendToMqtt("bt_client/10948857", 1, JSON.toJSONString(MqttCmd.create("ServiceUpdateWeekCourse","UPDATE_WEEK_COURSE")));
		return "send topic: " + message.getTopic() + ", message : " + message.getContent();
	}


}
