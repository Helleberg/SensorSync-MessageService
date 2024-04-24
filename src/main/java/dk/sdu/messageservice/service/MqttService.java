package dk.sdu.messageservice.service;

import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttService {
    private static final Logger log = LoggerFactory.getLogger(MqttService.class);
    private final MqttClient mqttClient;

    @Autowired
    public MqttService(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @PostConstruct
    public void subscribe() {
        if (mqttClient.isConnected()) {
            try {
                mqttClient.subscribe("devices/new");
                mqttClient.subscribe("lifecycle/status");
                log.info("Subscribed to topics");
            } catch (MqttException e) {
                log.error("Error subscribing to topics", e);
            }
        } else {
            log.error("MQTT client is not connected. Cannot subscribe to topics.");
        }
    }

    public void publish(String topic, String message, int qos, boolean retained) {
        if (mqttClient.isConnected()) {
            try {
                MqttMessage mqttmessage = new MqttMessage(message.getBytes());
                mqttmessage.setQos(qos);
                mqttmessage.setRetained(retained);
                this.mqttClient.publish(topic, mqttmessage);

                log.info("Message published");

            } catch (MqttException e) {
                log.error("Error publishing to topic", e);
            }
        } else {
            log.error("MQTT client is not connected. Cannot publish to topic.");
        }
    }
}
