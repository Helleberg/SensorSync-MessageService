package dk.sdu.messageservice.controller;

import dk.sdu.messageservice.service.MqttService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class MessageController {
    private static final Logger log = LoggerFactory.getLogger(MqttService.class);

    @Autowired
    MqttService mqttService;

    // TELL ESP THAT THERE IS AN FIRMWARE UPDATE READY
    @PostMapping("/message/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateFirmware(@RequestParam(name = "uuid") UUID uuid, @RequestParam(name = "jwt") String jwt) {
        try {
            // Generate message
            JSONObject message = new JSONObject();
            message.put("uuid", uuid);
            message.put("jwt", jwt);

            mqttService.publish("firmware/update/" + uuid, message.toString(), 1, false);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
