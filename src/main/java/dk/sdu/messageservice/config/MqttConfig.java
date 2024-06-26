package dk.sdu.messageservice.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {
    private static final Logger log = LoggerFactory.getLogger(MqttConfig.class);

    @Value("${athena.broker.url}")
    private String brokerUrl;
    @Value("${athena.broker.port}")
    private String brokerPort;
    @Value("${athena.broker.clientId}")
    private String clientId;
    @Value("${athena.broker.user}")
    private String username;
    @Value("${athena.broker.password}")
    private String password;
    @Value("${athena.broker.automaticReconnect}")
    private boolean automaticReconnect;

    @Autowired
    private MqttMessageListener messageListener;

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(automaticReconnect);
        options.setPassword(password.toCharArray());
        options.setUserName(username);
        return options;
    }

    @Bean
    public MqttClient mqttClient(MqttConnectOptions options) throws MqttException {
        log.info("tcp://{}:{}", brokerUrl, brokerPort);
        MqttClient client = new MqttClient("tcp://"+brokerUrl+":"+brokerPort, clientId);
        client.setCallback(messageListener);
        try {
            client.connect(options);
            log.info("Successfully connected to MQTT broker");
        } catch (MqttException e) {
            log.error("Connection to MQTT broker could not be established");
            e.printStackTrace();
        }
        return client;
    }
}
