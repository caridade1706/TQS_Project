package pt.ua.deti.tqs.cliniconnect.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPatientDTO {
    private String name;
    private Date dob;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String city;
    private String preferredHospital;
}
