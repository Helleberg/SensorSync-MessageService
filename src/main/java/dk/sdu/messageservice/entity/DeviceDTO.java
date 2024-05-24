package dk.sdu.messageservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class DeviceDTO {
    @JsonProperty("uuid")
    private UUID hardware_id;

    @JsonProperty("toit_firmware_version")
    private String firmwareVersion;

    @JsonProperty("athena_version")
    private String athenaVersion;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("jaguar_port")
    private int jaguarPort;

    public DeviceDTO(UUID hardware_id, String athenaVersion, String firmwareVersion, String ipAddress, int jaguarPort) {
        this.hardware_id = hardware_id;
        this.athenaVersion = athenaVersion;
        this.firmwareVersion = firmwareVersion;
        this.ipAddress = ipAddress;
        this.jaguarPort = jaguarPort;
    }

    @Override
    public String toString() {
        return "DeviceDTO{" +
                "hardwareId='" + hardware_id + '\'' +
                ", athenaVersion='" + athenaVersion + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", jaguarPort='" + jaguarPort + '\'' +
                '}';
    }
}
