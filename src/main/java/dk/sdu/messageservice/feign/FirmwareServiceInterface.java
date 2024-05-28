package dk.sdu.messageservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@FeignClient("FIRMWARE-SERVICE")
public interface FirmwareServiceInterface {
    // DELETE FIRMWARE FOLDER AFTER UPDATE
    @GetMapping("/firmware/delete/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    void deleteFirmwareVersion(@PathVariable("uuid") UUID uuid);
}
