package pt.ua.deti.tqs.cliniconnect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import pt.ua.deti.tqs.cliniconnect.PriorityStatus;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.models.QueueManagement;
import pt.ua.deti.tqs.cliniconnect.repositories.HospitalRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.QueueManagementRepository;
import pt.ua.deti.tqs.cliniconnect.services.impl.QueueManagementServiceImpl;

@ExtendWith(MockitoExtension.class)
class QueueManagementServiceTest {

    @Mock(lenient = true)
    QueueManagementRepository queueManagementRepository;

    @Mock(lenient = true)
    HospitalRepository hospitalRepository;

    @InjectMocks
    QueueManagementServiceImpl queueManagementServiceImpl;

    @Test
    @DisplayName("Test get all queue management")
    void testGetAllQueueManagement() {

        UUID id = UUID.randomUUID();

        List<QueueManagement> queueManagements = new ArrayList<>();

        QueueManagement queueManagement = new QueueManagement();
        queueManagement.setId(id);
        queueManagement.setPriorityStatus(PriorityStatus.A);
        queueManagement.setArrivalTime(null);
        queueManagement.setCalledTime(null);
        queueManagement.setCalled(true);
        queueManagement.setCounterNumber("1");
        queueManagement.setQueueNumber("A1");
        queueManagement.setHospital(null);

        queueManagements.add(queueManagement);

        when(queueManagementRepository.findFirstByCalledTrueAndCalledOnBoardFalseOrderByCalledTimeDesc(any(PageRequest.class))).thenReturn(queueManagements);

        QueueManagement result = queueManagementServiceImpl.getAllQueueManagements();

        assertEquals(id, result.getId());

        verify(queueManagementRepository, times(1)).findFirstByCalledTrueAndCalledOnBoardFalseOrderByCalledTimeDesc(any(PageRequest.class));
    }

    @Test
    @DisplayName("Test get all queue management")
    void testGetAllQueueManagementIsEmpty() {

        List<QueueManagement> queueManagements = new ArrayList<>();

        when(queueManagementRepository.findFirstByCalledTrueAndCalledOnBoardFalseOrderByCalledTimeDesc(any(PageRequest.class))).thenReturn(queueManagements);

        QueueManagement result = queueManagementServiceImpl.getAllQueueManagements();

        assertEquals(null ,result);

        verify(queueManagementRepository, times(1)).findFirstByCalledTrueAndCalledOnBoardFalseOrderByCalledTimeDesc(any(PageRequest.class));
    }


    @Test
    @DisplayName("Test get queue management by id")
    void testGetQueueManagementById() {

        UUID id = UUID.randomUUID();

        QueueManagement queueManagement = new QueueManagement();
        queueManagement.setId(id);
        queueManagement.setPriorityStatus(PriorityStatus.A);
        queueManagement.setArrivalTime(null);
        queueManagement.setCalledTime(null);
        queueManagement.setCalled(true);
        queueManagement.setCounterNumber("1");
        queueManagement.setQueueNumber("A1");
        queueManagement.setHospital(null);

        when(queueManagementRepository.findById(id)).thenReturn(Optional.of(queueManagement));

        Optional<QueueManagement> result = queueManagementServiceImpl.queueManagementService(id);

        assertEquals(id, result.get().getId());

        verify(queueManagementRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    @DisplayName("Test Save queue management")
    void testSaveQueueManagement() {

        UUID id = UUID.randomUUID();

        QueueManagement queueManagement = new QueueManagement();
        queueManagement.setId(id);
        queueManagement.setPriorityStatus(PriorityStatus.A);
        queueManagement.setArrivalTime(null);
        queueManagement.setCalledTime(null);
        queueManagement.setCalled(true);
        queueManagement.setCounterNumber("1");
        queueManagement.setQueueNumber("A1");
        queueManagement.setHospital(null);

        when(queueManagementRepository.save(queueManagement)).thenReturn(queueManagement);

        QueueManagement result = queueManagementServiceImpl.saveQueueManagement(queueManagement);

        assertEquals(id, result.getId());

        verify(queueManagementRepository, times(1)).save(any(QueueManagement.class));
    }

    @Test
    @DisplayName("Test Delete queue management by Id")
    void testDeleteQueueManagementById() {
        UUID id = UUID.randomUUID();

        doNothing().when(queueManagementRepository).deleteById(id);

        queueManagementServiceImpl.deleteQueueManagementById(id);

        verify(queueManagementRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Test Call Next Ticket")
    void testCallNextTicket() {
        String counterNumber = "1";
        String priorityStatus = "A";

        UUID id = UUID.randomUUID();
        QueueManagement queueManagement = new QueueManagement();
        queueManagement.setId(id);
        queueManagement.setPriorityStatus(PriorityStatus.A);
        queueManagement.setArrivalTime(null);
        queueManagement.setCalledTime(null);
        queueManagement.setCalled(true);
        queueManagement.setCounterNumber("1");
        queueManagement.setQueueNumber("A1");
        queueManagement.setHospital(null);

        List<QueueManagement> queueManagements = new ArrayList<>();
        queueManagements.add(queueManagement);

        when(queueManagementRepository.findByPriorityStatusAndCalledTimeIsNullOrderByArrivalTimeAsc(PriorityStatus.valueOf(priorityStatus))).thenReturn(queueManagements);

        Optional<QueueManagement> result = queueManagementServiceImpl.callNextTicket(priorityStatus, counterNumber);

        assertEquals(id, result.get().getId());

        verify(queueManagementRepository, times(1)).findByPriorityStatusAndCalledTimeIsNullOrderByArrivalTimeAsc(any(PriorityStatus.class));
    }

    @Test
    @DisplayName("Test Call Next Ticket with no QueueManagement")
    void testCallNextTicketWithNoQueueManagement() {
        String counterNumber = "1";
        String priorityStatus = "A";

        List<QueueManagement> queueManagements = new ArrayList<>();

        when(queueManagementRepository.findByPriorityStatusAndCalledTimeIsNullOrderByArrivalTimeAsc(PriorityStatus.valueOf(priorityStatus))).thenReturn(queueManagements);

        Optional<QueueManagement> result = queueManagementServiceImpl.callNextTicket(priorityStatus, counterNumber);

        assertTrue(result.isEmpty());
        
        verify(queueManagementRepository, times(1)).findByPriorityStatusAndCalledTimeIsNullOrderByArrivalTimeAsc(any(PriorityStatus.class));
    }

    @Test
    @DisplayName("Test Create Queue Management")
    void testCreateQueueManagement() {
        String priorityStatus = "A";
        String hospitalName = "HospitalName";

        Hospital hospital = new Hospital();
        hospital.setId(UUID.randomUUID());
        hospital.setName(hospitalName);

        when(hospitalRepository.findByName(hospitalName)).thenReturn(Optional.of(hospital));
        when(queueManagementRepository.findByQueueNumber(any(String.class))).thenReturn(Optional.empty());

        QueueManagement queueManagement = new QueueManagement();
        queueManagement.setId(UUID.randomUUID());
        queueManagement.setPriorityStatus(PriorityStatus.A);
        queueManagement.setQueueNumber("A1");
        queueManagement.setArrivalTime(LocalDateTime.now());
        queueManagement.setHospital(hospital);
        queueManagement.setCalled(false);

        when(queueManagementRepository.save(any(QueueManagement.class))).thenReturn(queueManagement);

        QueueManagement result = queueManagementServiceImpl.createQueueManagement(priorityStatus, hospitalName);

        assertEquals("A1", result.getQueueNumber());
        assertEquals(PriorityStatus.A, result.getPriorityStatus());
        assertEquals(hospital, result.getHospital());

        verify(hospitalRepository, times(1)).findByName(hospitalName);
        verify(queueManagementRepository, times(1)).findByQueueNumber(any(String.class));
        verify(queueManagementRepository, times(1)).save(any(QueueManagement.class));
    }

    @Test
    @DisplayName("Test Create Queue Management with Existing Queue Number")
    void testCreateQueueManagementWithExistingQueueNumber() {
        String priorityStatus = "A";
        String hospitalName = "HospitalName";

        QueueManagement existingQueueManagement = new QueueManagement();
        existingQueueManagement.setQueueNumber("A1");

        when(queueManagementRepository.findByQueueNumber(any(String.class))).thenReturn(Optional.of(existingQueueManagement));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            queueManagementServiceImpl.createQueueManagement(priorityStatus, hospitalName);
        });

        assertEquals("Queue number already exists: A1", exception.getMessage());

        verify(queueManagementRepository, times(1)).findByQueueNumber(any(String.class));
        verify(queueManagementRepository, times(0)).save(any(QueueManagement.class));
    }

    @Test
    @DisplayName("Test Generate Queue Number")
    void testGenerateQueueNumber() {
        PriorityStatus priorityStatus = PriorityStatus.A;

        List<QueueManagement> queueManagements = new ArrayList<>();
        QueueManagement qm1 = new QueueManagement();
        qm1.setQueueNumber("A1");
        QueueManagement qm2 = new QueueManagement();
        qm2.setQueueNumber("A2");

        queueManagements.add(qm1);
        queueManagements.add(qm2);

        when(queueManagementRepository.findByPriorityStatusOrderByQueueNumberDesc(priorityStatus)).thenReturn(queueManagements);

        String newQueueNumber = queueManagementServiceImpl.generateQueueNumber(priorityStatus);

        assertEquals("A3", newQueueNumber);

        verify(queueManagementRepository, times(1)).findByPriorityStatusOrderByQueueNumberDesc(priorityStatus);
    }

    @Test
    @DisplayName("Test Generate Queue Number When List Is Empty")
    void testGenerateQueueNumberWhenListIsEmpty() {
        PriorityStatus priorityStatus = PriorityStatus.A;

        List<QueueManagement> queueManagements = new ArrayList<>();

        when(queueManagementRepository.findByPriorityStatusOrderByQueueNumberDesc(priorityStatus)).thenReturn(queueManagements);

        String newQueueNumber = queueManagementServiceImpl.generateQueueNumber(priorityStatus);

        assertEquals("A1", newQueueNumber);

        verify(queueManagementRepository, times(1)).findByPriorityStatusOrderByQueueNumberDesc(priorityStatus);
    }
}