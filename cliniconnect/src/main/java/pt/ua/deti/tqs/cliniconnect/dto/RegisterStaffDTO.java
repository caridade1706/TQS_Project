package pt.ua.deti.tqs.cliniconnect.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStaffDTO {
    private String name;
    private Date dob;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String city;
    private String department;
    private String task;
}
