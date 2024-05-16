package pt.ua.deti.tqs.cliniconnect.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentDTO {
    private Date date;
    private String time;
    private double price;
    private String type;
    private String patientName;
    private String doctorName;
    private String hospitalName;
}
