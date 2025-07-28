package com.proshine.shahecommunityhospital.config.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * @author lenovo
 */
@Configuration
public class MqttConfig {
	/**
     * 先创建连接
	 * 创建MqttPahoClientFactory，设置MQTT Broker连接属性，如果使用SSL验证，也在这里设置。
	 * @return factory
	 */
	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();
		// 设置代理端的URL地址，可以是多个
		options.setServerURIs(new String[]{"tcp://127.0.0.1:18880"});
		factory.setConnectionOptions(options);
		return factory;
	}
	
	/**
	 * 出站通道
	 */
	@Bean
	public MessageChannel mqttOutboundChannel() {
		return new DirectChannel();
	}

	/**
	 * 出站
	 */
    @Bean
	@ServiceActivator(inputChannel = "mqttOutboundChannel")
	public MessageHandler outbound() {
		// 发送消息和消费消息Channel可以使用相同MqttPahoClientFactory
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("publishClient", mqttClientFactory());
		// 如果设置成true，即异步，发送消息时将不会阻塞。
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic("command");
		// 设置默认QoS
		messageHandler.setDefaultQos(1);
		// Paho消息转换器
		DefaultPahoMessageConverter defaultPahoMessageConverter = new DefaultPahoMessageConverter();
		messageHandler.setConverter(defaultPahoMessageConverter);
		return messageHandler;
	}

}

