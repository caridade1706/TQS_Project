package pt.ua.deti.tqs.cliniconnect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.models.Staff;
import pt.ua.deti.tqs.cliniconnect.repositories.HospitalRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.StaffRepository;
import pt.ua.deti.tqs.cliniconnect.services.impl.StaffServiceImpl;

@ExtendWith(MockitoExtension.class)
class StaffServiceTest {

    @Mock(lenient = true)
    StaffRepository staffRepository;

    @Mock(lenient = true)
    HospitalRepository hospitalRepository;

    @InjectMocks
    StaffServiceImpl staffService;

    @Test
    void testGetStaffByEmail() {

        String email = "robertorcastro@ua.pt";

        Staff expectedStaff = new Staff(UUID.randomUUID(), "Roberto Castro", new Date(), email, "password",
                "989345890", "Rua do Hospital", "Porto", "Administration", "Recetionist");

        when(staffRepository.findByEmail(email)).thenReturn(expectedStaff);

        Staff actualStaff = staffService.getPatientByEmail(email);

        assertEquals(expectedStaff, actualStaff);
        verify(staffRepository, times(1)).findByEmail(email);
    }

    @Test
    void testUpdateStaffHospitals() {

        String email = "robertorcastro@ua.pt";
        List<String> hospitalNames = Arrays.asList("Hospital A", "Hospital B");

        Staff staff = new Staff(UUID.randomUUID(), "Roberto Castro", new Date(), email, "password",
                "989345890", "Rua do Hospital", "Porto", "Administration", "Receptionist");
        Hospital hospitalA = new Hospital(UUID.randomUUID(), "Hospital A", "Address A", "City A", new HashSet<>(), new HashSet<>(), new HashSet<>(), null);
        Hospital hospitalB = new Hospital(UUID.randomUUID(), "Hospital B", "Address B", "City B", new HashSet<>(), new HashSet<>(), new HashSet<>(), null);

        when(staffRepository.findByEmail(email)).thenReturn(staff);
        when(hospitalRepository.findByName("Hospital A")).thenReturn(Optional.of(hospitalA));
        when(hospitalRepository.findByName("Hospital B")).thenReturn(Optional.of(hospitalB));

        boolean result = staffService.updateStaffHospitals(email, hospitalNames);

        assertTrue(result);
        assertEquals(2, staff.getHospitals().size());
        assertTrue(staff.getHospitals().contains(hospitalA));
        assertTrue(staff.getHospitals().contains(hospitalB));
        assertTrue(hospitalA.getStaffs().contains(staff));
        assertTrue(hospitalB.getStaffs().contains(staff));

        verify(staffRepository, times(1)).findByEmail(email);
        verify(hospitalRepository, times(1)).findByName("Hospital A");
        verify(hospitalRepository, times(1)).findByName("Hospital B");
        verify(staffRepository, times(1)).save(staff);
    }

    @Test
    void testUpdateStaffHospitals_StaffNotFound() {

        String email = "robertorcastro@ua.pt";
        List<String> hospitalNames = Arrays.asList("Hospital A", "Hospital B");

        when(staffRepository.findByEmail(email)).thenReturn(null);

        boolean result = staffService.updateStaffHospitals(email, hospitalNames);

        assertFalse(result);

        verify(staffRepository, times(1)).findByEmail(email);
        verify(hospitalRepository, times(0)).findByName(anyString());
        verify(staffRepository, times(0)).save(any(Staff.class));
    }

    @Test
    void testUpdateStaffHospitals_HospitalNotFound() {

        String email = "robertorcastro@ua.pt";
        List<String> hospitalNames = Arrays.asList("Hospital A", "Hospital B");

        Staff staff = new Staff(UUID.randomUUID(), "Roberto Castro", new Date(), email, "password",
                "989345890", "Rua do Hospital", "Porto", "Administration", "Receptionist");

        when(staffRepository.findByEmail(email)).thenReturn(staff);
        when(hospitalRepository.findByName("Hospital A")).thenReturn(Optional.empty());
        when(hospitalRepository.findByName("Hospital B")).thenReturn(Optional.empty());

        boolean result = staffService.updateStaffHospitals(email, hospitalNames);

        assertTrue(result);
        assertEquals(0, staff.getHospitals().size());

        verify(staffRepository, times(1)).findByEmail(email);
        verify(hospitalRepository, times(1)).findByName("Hospital A");
        verify(hospitalRepository, times(1)).findByName("Hospital B");
        verify(staffRepository, times(1)).save(staff);
    }
}