package pt.ua.deti.tqs.cliniconnect.services;

import pt.ua.deti.tqs.cliniconnect.models.Staff;

public interface StaffService {
    Staff getPatientByEmail(String email);
}