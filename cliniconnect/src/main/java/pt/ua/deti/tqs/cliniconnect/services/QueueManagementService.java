package pt.ua.deti.tqs.cliniconnect.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import pt.ua.deti.tqs.cliniconnect.models.QueueManagement;

public interface QueueManagementService {
    List<QueueManagement> getAllQueueManagements();
    Optional<QueueManagement> queueManagementService(UUID id);
    QueueManagement saveQueueManagement(QueueManagement queueManagement);
    void deleteQueueManagementById(UUID id);
    Optional<QueueManagement> callNextTicket(String priorityStatus, String counter);
    QueueManagement createQueueManagement(String priorityStatus, String hospitalName);
}