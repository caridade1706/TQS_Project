package pt.ua.deti.tqs.cliniconnect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.deti.tqs.cliniconnect.models.Staff;
import pt.ua.deti.tqs.cliniconnect.repositories.StaffRepository;
import pt.ua.deti.tqs.cliniconnect.services.impl.StaffServiceImpl;

@ExtendWith(MockitoExtension.class)
class StaffServiceTest {

    @Mock(lenient = true)
    StaffRepository staffRepository;

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
}