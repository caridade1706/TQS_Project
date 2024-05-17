package pt.ua.deti.tqs.cliniconnect.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

class CreateAppointmentDTOTest {

    private CreateAppointmentDTO createAppointmentDTO;

    @BeforeEach
    void setUp() {
        createAppointmentDTO = new CreateAppointmentDTO();
    }

    @Test
    void testAllConstructer() {
        Date date = new Date();
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO(date, "time", 10.0, "type", "patientName", "doctorName", "hospitalName");
        assertEquals(date, createAppointmentDTO.getDate());
        assertEquals("time", createAppointmentDTO.getTime());
        assertEquals(10.0, createAppointmentDTO.getPrice());
        assertEquals("type", createAppointmentDTO.getType());
        assertEquals("patientName", createAppointmentDTO.getPatientName());
        assertEquals("doctorName", createAppointmentDTO.getDoctorName());
        assertEquals("hospitalName", createAppointmentDTO.getHospitalName());
    }

    @Test
    void testSettersAndGetters() {

        Date date = new Date();

        createAppointmentDTO.setDate(date);
        createAppointmentDTO.setTime("time");
        createAppointmentDTO.setPrice(10.0);
        createAppointmentDTO.setType("type");
        createAppointmentDTO.setPatientName("patientName");
        createAppointmentDTO.setDoctorName("doctorName");
        createAppointmentDTO.setHospitalName("hospitalName");
        
        assertEquals(date, createAppointmentDTO.getDate());
        assertEquals("time", createAppointmentDTO.getTime());
        assertEquals(10.0, createAppointmentDTO.getPrice());
        assertEquals("type", createAppointmentDTO.getType());
        assertEquals("patientName", createAppointmentDTO.getPatientName());
        assertEquals("doctorName", createAppointmentDTO.getDoctorName());
        assertEquals("hospitalName", createAppointmentDTO.getHospitalName());
    }        
}