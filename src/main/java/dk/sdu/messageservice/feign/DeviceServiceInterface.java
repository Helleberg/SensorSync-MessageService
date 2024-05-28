package dk.sdu.messageservice.feign;

import dk.sdu.messageservice.entity.DeviceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient("DEVICE-SERVICE")
public interface DeviceServiceInterface {
    // CREATE
    @PostMapping("api/v1/devices")
    @ResponseStatus(HttpStatus.CREATED)
    DeviceDTO save(@RequestBody DeviceDTO device);

    // UPDATE
    @PutMapping("api/v1/devices/{uuid}/lastping")
    @ResponseStatus(HttpStatus.OK)
    DeviceDTO updateLastPing(@RequestBody DeviceDTO device, @PathVariable UUID uuid);
}
