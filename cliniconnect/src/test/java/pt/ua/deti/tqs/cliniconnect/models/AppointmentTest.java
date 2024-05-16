package pt.ua.deti.tqs.cliniconnect.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalTime;
import java.util.*;

class AppointmentTest {

    private Appointment appointment;

    @BeforeEach
    public void setUp() {
        appointment = new Appointment();
    }
    
    @Test 
    void createApointmentTest() {
        assertNotNull(appointment);

        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        Hospital hospital = new Hospital();

        UUID apointmentUUID = UUID.randomUUID();
        LocalTime time = LocalTime.of(10, 0);
        Date date = new Date();
        appointment = new Appointment(apointmentUUID, date, time, "status", 10.0, "type", "currency", patient, doctor, hospital);

        assertEquals(apointmentUUID, appointment.getId());
        assertEquals(date, appointment.getDate());
        assertEquals(time, appointment.getTime());
        assertEquals("status", appointment.getStatus());
        assertEquals(10.0, appointment.getPrice());
        assertEquals("type", appointment.getType());
        assertEquals("currency", appointment.getCurrency());
        assertEquals(patient, appointment.getPatient());
        assertEquals(doctor, appointment.getDoctor());
        assertEquals(hospital, appointment.getHospital());

    }

    @Test
    void testSetters() {
        UUID UUID = java.util.UUID.randomUUID();
        LocalTime time = LocalTime.of(10, 0);
        Date date = new Date();
        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        Hospital hospital = new Hospital();

        appointment.setId(UUID);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setStatus("status");
        appointment.setPrice(10.0);
        appointment.setType("type");
        appointment.setCurrency("currency");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setHospital(hospital);

        assertEquals(UUID, appointment.getId());
        assertEquals(date, appointment.getDate());
        assertEquals(time, appointment.getTime());
        assertEquals("status", appointment.getStatus());
        assertEquals(10.0, appointment.getPrice());
        assertEquals("type", appointment.getType());
        assertEquals("currency", appointment.getCurrency());
        assertEquals(patient, appointment.getPatient());
        assertEquals(doctor, appointment.getDoctor());
        assertEquals(hospital, appointment.getHospital());
    }   
}