package pt.ua.deti.tqs.cliniconnect.services;

import java.util.List;

import pt.ua.deti.tqs.cliniconnect.models.Staff;

public interface StaffService {
    Staff getPatientByEmail(String email);
    boolean updateStaffHospitals(String email, List<String> hospitals);
}