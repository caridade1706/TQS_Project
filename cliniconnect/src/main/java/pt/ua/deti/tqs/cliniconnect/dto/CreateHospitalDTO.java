package pt.ua.deti.tqs.cliniconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHospitalDTO {
    private String name;
    private String address;
    private String city;
}