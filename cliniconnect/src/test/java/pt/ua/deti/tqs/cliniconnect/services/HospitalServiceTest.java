package pt.ua.deti.tqs.cliniconnect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.deti.tqs.cliniconnect.dto.CreateHospitalDTO;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.repositories.HospitalRepository;
import pt.ua.deti.tqs.cliniconnect.services.impl.HospitalServiceImpl;

@ExtendWith(MockitoExtension.class)
class HospitalServiceTest {

    @Mock(lenient = true)
    HospitalRepository hospitalRepository;

    @InjectMocks
    HospitalServiceImpl hospitalServiceImpl;

    @Test
    @DisplayName("Test save hospital")
    void testSaveHospital() {
        UUID hospitalId = UUID.randomUUID();

        CreateHospitalDTO createHospitalDTO = new CreateHospitalDTO();
        createHospitalDTO.setName("HospitalName");
        createHospitalDTO.setAddress("Rua");
        createHospitalDTO.setCity("Aveiro");

        Hospital expectedHospital = new Hospital();
        expectedHospital.setId(hospitalId);
        expectedHospital.setName(createHospitalDTO.getName());
        expectedHospital.setAddress(createHospitalDTO.getAddress());
        expectedHospital.setCity(createHospitalDTO.getCity());
        expectedHospital.setStaffs(null);
        expectedHospital.setDoctors(null);
        expectedHospital.setAppointments(null);
        expectedHospital.setQueueManagement(null);

        // Mock the save operation
        when(hospitalRepository.save(any(Hospital.class))).thenReturn(expectedHospital);

        Hospital actualHospital = hospitalServiceImpl.saveHospital(createHospitalDTO);

        assertEquals(expectedHospital.getName(), actualHospital.getName());
        assertEquals(expectedHospital.getAddress(), actualHospital.getAddress());
        assertEquals(expectedHospital.getCity(), actualHospital.getCity());

        verify(hospitalRepository, times(1)).save(any(Hospital.class));
    }

    @Test
    @DisplayName("Test save hospital")
    void testGetHospitalById() {
        UUID hospitalId = UUID.randomUUID();

        Hospital expectedHospital = new Hospital();
        expectedHospital.setId(hospitalId);
        expectedHospital.setName("HospitalName");
        expectedHospital.setAddress("Rua");
        expectedHospital.setCity("Aveiro");
        expectedHospital.setStaffs(null);
        expectedHospital.setDoctors(null);
        expectedHospital.setAppointments(null);
        expectedHospital.setQueueManagement(null);

        // Mock the save operation
        when(hospitalRepository.findById(hospitalId)).thenReturn(Optional.of(expectedHospital));

        Optional<Hospital> actualHospital = hospitalServiceImpl.getHospitalById(hospitalId);

        assertEquals(expectedHospital.getName(), actualHospital.get().getName());
        assertEquals(expectedHospital.getAddress(), actualHospital.get().getAddress());
        assertEquals(expectedHospital.getCity(), actualHospital.get().getCity());

        verify(hospitalRepository, times(1)).findById(hospitalId);
    }

    @Test
    @DisplayName("Test Get All Hospitals")
    void testGetAllHospital() {
        UUID hospitalId1 = UUID.randomUUID();
        UUID hospitalId2 = UUID.randomUUID();


        Hospital expectedHospital1 = new Hospital();
        expectedHospital1.setId(hospitalId1);
        expectedHospital1.setName("HospitalName");
        expectedHospital1.setAddress("Rua");
        expectedHospital1.setCity("Aveiro");
        expectedHospital1.setStaffs(null);
        expectedHospital1.setDoctors(null);
        expectedHospital1.setAppointments(null);
        expectedHospital1.setQueueManagement(null);

        Hospital expectedHospital2 = new Hospital();
        expectedHospital2.setId(hospitalId2);
        expectedHospital2.setName("HospitalName");
        expectedHospital2.setAddress("Rua");
        expectedHospital2.setCity("Aveiro");
        expectedHospital2.setStaffs(null);
        expectedHospital2.setDoctors(null);
        expectedHospital2.setAppointments(null);
        expectedHospital2.setQueueManagement(null);

        // Mock the save operation
        when(hospitalRepository.findAll()).thenReturn(List.of(expectedHospital1, expectedHospital2));

        List<Hospital> actualHospitals = hospitalServiceImpl.getAllHospitals();

        assertEquals(expectedHospital1.getName(), actualHospitals.get(0).getName());
        assertEquals(expectedHospital1.getAddress(), actualHospitals.get(0).getAddress());
        assertEquals(expectedHospital1.getCity(), actualHospitals.get(0).getCity());

        assertEquals(expectedHospital2.getName(), actualHospitals.get(1).getName());
        assertEquals(expectedHospital2.getAddress(), actualHospitals.get(1).getAddress());
        assertEquals(expectedHospital2.getCity(), actualHospitals.get(1).getCity());

        verify(hospitalRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test delete hospital by id")
    void testDeleteHospital() {
        UUID hospitalId = UUID.randomUUID();

        doNothing().when(hospitalRepository).deleteById(hospitalId);

        // Perform the delete operation
        hospitalServiceImpl.deleteHospital(hospitalId);

        // Verify that the repository's deleteById method was called once with the correct ID
        verify(hospitalRepository, times(1)).deleteById(hospitalId);
    }

}