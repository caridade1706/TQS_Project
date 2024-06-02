package pt.ua.deti.tqs.cliniconnect.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;

import pt.ua.deti.tqs.cliniconnect.PriorityStatus;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.models.QueueManagement;
import pt.ua.deti.tqs.cliniconnect.services.QueueManagementService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QueueManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QueueManagementService queueManagementService;

    private String url = "/api/queue-management";

    @Test
    @DisplayName("Test Getting all QueueManagements")
    void testGetAllQueueManagementd() throws Exception {

        Hospital hospital = new Hospital();
        hospital.setId(UUID.randomUUID());
        hospital.setName("Hospital Name");
        hospital.setAddress("Hospital Address");
        hospital.setCity("Hospital City");
        hospital.setStaffs(null);
        hospital.setDoctors(null);
        hospital.setAppointments(null);
        hospital.setQueueManagement(null);

        List<QueueManagement> queueManagements = List.of(
                new QueueManagement(UUID.randomUUID(), PriorityStatus.A, null, null, false, "1", "A12", hospital),
                new QueueManagement(UUID.randomUUID(), PriorityStatus.B, null, null, false, "3", "B10", hospital));

        // Mock service call with any instance
        when(queueManagementService.getAllQueueManagements()).thenReturn(queueManagements);

        // Perform request and verify response
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].priorityStatus").value("A"))
                .andExpect(jsonPath("$[0].queueNumber").value("A12"))
                .andExpect(jsonPath("$[1].priorityStatus").value("B"))
                .andExpect(jsonPath("$[1].queueNumber").value("B10"));

        verify(queueManagementService, times(1)).getAllQueueManagements();
    }

    @Test
    @DisplayName("Test Getting QueueManagement by the Id")
    void testGetQueueManagementById() throws Exception {

        Hospital hospital = new Hospital();
        hospital.setId(UUID.randomUUID());
        hospital.setName("Hospital Name");
        hospital.setAddress("Hospital Address");
        hospital.setCity("Hospital City");
        hospital.setStaffs(null);
        hospital.setDoctors(null);
        hospital.setAppointments(null);
        hospital.setQueueManagement(null);

        UUID queueId = UUID.randomUUID();

        QueueManagement queueManagement = new QueueManagement(queueId, PriorityStatus.A, null, null, false,
                "1", "A12", hospital);

        // Mock service call
        when(queueManagementService.queueManagementService(queueId)).thenReturn(Optional.of(queueManagement));

        // Perform request and verify response
        mockMvc.perform(get(url + "/" + queueId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priorityStatus").value("A"))
                .andExpect(jsonPath("$.queueNumber").value("A12"));

        verify(queueManagementService, times(1)).queueManagementService(any(UUID.class));
    }

    @Test
    @DisplayName("Test Getting QueueManagement by the Id not found")
    void testGetQueueManagementByIdNotFound() throws Exception {

        UUID queueId = UUID.randomUUID();

        // Mock service call
        when(queueManagementService.queueManagementService(queueId)).thenReturn(Optional.empty());

        // Perform request and verify response
        mockMvc.perform(get(url + "/" + queueId))
                .andExpect(status().isNotFound());

        verify(queueManagementService, times(1)).queueManagementService(any(UUID.class));
    }

    @Test
    @DisplayName("Test Creating a QueueManagement")
    void testCreatingQueueManagement() throws Exception {

        Hospital hospital = new Hospital();
        hospital.setId(UUID.randomUUID());
        hospital.setName("Hospital Name");
        hospital.setAddress("Hospital Address");
        hospital.setCity("Hospital City");
        hospital.setStaffs(null);
        hospital.setDoctors(null);
        hospital.setAppointments(null);
        hospital.setQueueManagement(null);

        UUID queueId = UUID.randomUUID();

        QueueManagement queueManagement = new QueueManagement(queueId, PriorityStatus.A, null, null, false,
                "1", "A12", hospital);

        // Mock service call
        when(queueManagementService.createQueueManagement("A", "Hospital Name")).thenReturn(queueManagement);

        // Perform request and verify response
        mockMvc.perform(post(url + "?priorityStatus=A&hospitalName=Hospital Name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priorityStatus").value("A"))
                .andExpect(jsonPath("$.queueNumber").value("A12"));

        verify(queueManagementService, times(1)).createQueueManagement(any(String.class), any(String.class));
    }

    @Test
    @DisplayName("Test Deleting a QueueManagement by Id")
    void testDeletingQueueManagementById() throws Exception {
        UUID queueId = UUID.randomUUID();

        // Perform request and verify response
        mockMvc.perform(delete(url + "/" + queueId))
                .andExpect(status().isNoContent());

        verify(queueManagementService, times(1)).deleteQueueManagementById(queueId);
    }

    @Test
    @DisplayName("Test Calling the Next Ticket")
    void testCallingNextTicket() throws Exception {
        Hospital hospital = new Hospital();
        hospital.setId(UUID.randomUUID());
        hospital.setName("Hospital Name");
        hospital.setAddress("Hospital Address");
        hospital.setCity("Hospital City");

        UUID queueId = UUID.randomUUID();
        QueueManagement queueManagement = new QueueManagement(queueId, PriorityStatus.A, null, null, false, "1", "A12", hospital);

        when(queueManagementService.callNextTicket("A", "Counter1")).thenReturn(Optional.of(queueManagement));

        mockMvc.perform(get(url + "/call-next?priorityStatus=A&counter=Counter1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priorityStatus").value("A"))
                .andExpect(jsonPath("$.queueNumber").value("A12"));

        verify(queueManagementService, times(1)).callNextTicket("A", "Counter1");
    }

    @Test
    @DisplayName("Test Calling the Next Ticket with No Content")
    void testCallingNextTicketNoContent() throws Exception {
        when(queueManagementService.callNextTicket("A", "Counter1")).thenReturn(Optional.empty());

        mockMvc.perform(get(url + "/call-next?priorityStatus=A&counter=Counter1"))
                .andExpect(status().isNoContent());

        verify(queueManagementService, times(1)).callNextTicket("A", "Counter1");
    }
}