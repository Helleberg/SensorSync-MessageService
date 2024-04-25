package dk.sdu.messageservice.controller;

import dk.sdu.messageservice.request_types.UpdateFirmwareRequest;
import dk.sdu.messageservice.service.MqttService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class MessageController {
    private static final Logger log = LoggerFactory.getLogger(MqttService.class);

    @Autowired
    MqttService mqttService;

    // TELL ESP THAT THERE IS AN FIRMWARE UPDATE READY
    @PostMapping("/mqtt/device/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateFirmware(@RequestBody UpdateFirmwareRequest updateFirmwareRequest) {
        System.out.println(updateFirmwareRequest);
        try {
            // Generate message
            JSONObject message = new JSONObject();
            message.put("uuid", updateFirmwareRequest.getDeviceUUID());
            message.put("jwt", updateFirmwareRequest.getJwt());
            System.out.println("Publishing to: firmware/update/" + updateFirmwareRequest.getDeviceUUID().toString());
            System.out.println("With message: " + message);
            mqttService.publish("firmware/update/" + updateFirmwareRequest.getDeviceUUID().toString(), message.toString(), 1, false);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
