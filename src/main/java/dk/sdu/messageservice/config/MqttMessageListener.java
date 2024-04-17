package dk.sdu.messageservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.sdu.messageservice.entity.DeviceDTO;
import dk.sdu.messageservice.entity.MqttResponse;
import dk.sdu.messageservice.feign.DeviceServiceInterface;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MqttMessageListener implements MqttCallback {
    private static final Logger log = LoggerFactory.getLogger(MqttMessageListener.class);
    private final ObjectMapper objectMapper;

    @Autowired
    DeviceServiceInterface deviceServiceInterface;

    public MqttMessageListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        log.warn("Disconnected from broker...");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        MqttResponse response = new MqttResponse(topic, message.getPayload());

        if ("devices/new".equals(response.getTopic())) {
            log.info("Trying to convert message to DeviceDTO...");
            DeviceDTO device = objectMapper.readValue(response.getMessage(), DeviceDTO.class);
            System.out.println(device.toString());
            try {
                deviceServiceInterface.save(device);
                log.info("Device stored in the device service Database...");
            } catch (Exception e) {
                log.warn("Could not store the device...");
                e.printStackTrace();
            }

        } else if ("lifecycle/status".equals(response.getTopic())) {
            System.out.println("Got lifecycle status: " + Arrays.toString(response.getMessage()));
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // TODO: Handle when a message has been delivered.
    }
}
