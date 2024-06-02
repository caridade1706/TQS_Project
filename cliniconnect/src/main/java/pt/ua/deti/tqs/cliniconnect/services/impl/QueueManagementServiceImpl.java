package pt.ua.deti.tqs.cliniconnect.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.PriorityStatus;
import pt.ua.deti.tqs.cliniconnect.models.QueueManagement;
import pt.ua.deti.tqs.cliniconnect.repositories.HospitalRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.QueueManagementRepository;
import pt.ua.deti.tqs.cliniconnect.services.QueueManagementService;

@Service
@AllArgsConstructor
public class QueueManagementServiceImpl implements QueueManagementService {

    private QueueManagementRepository queueManagementRepository;
    private HospitalRepository hospitalRepository;

    @Override
    public QueueManagement getAllQueueManagements() {
        PageRequest pageRequest = PageRequest.of(0, 1);
        List<QueueManagement> tickets = queueManagementRepository.findFirstByCalledTrueAndCalledOnBoardFalseOrderByCalledTimeDesc(pageRequest);    
        
        QueueManagement ticket = tickets.isEmpty() ? null : tickets.get(0);
        if (ticket != null) {
            ticket.setCalledOnBoard(true);
            queueManagementRepository.save(ticket);
            return ticket;
        }
        return null;
    }

    @Override
    public Optional<QueueManagement> queueManagementService(UUID id) {
        return queueManagementRepository.findById(id);
    }

    @Override
    public QueueManagement saveQueueManagement(QueueManagement queueManagement) {
        return queueManagementRepository.save(queueManagement);
    }

    @Override
    public void deleteQueueManagementById(UUID id) {
        queueManagementRepository.deleteById(id);
    }

    @Override
    public Optional<QueueManagement> callNextTicket(String priorityStatus, String counter) {
        PriorityStatus status = PriorityStatus.valueOf(priorityStatus);
        List<QueueManagement> queueManagements = queueManagementRepository
                .findByPriorityStatusAndCalledTimeIsNullOrderByArrivalTimeAsc(status);
        if (!queueManagements.isEmpty()) {
            QueueManagement nextTicket = queueManagements.get(0);
            nextTicket.setCalledTime(LocalDateTime.now());
            nextTicket.setCounterNumber(counter);
            nextTicket.setCalled(true);
            queueManagementRepository.save(nextTicket);  // Save the updated ticket
            return Optional.of(nextTicket);
        }
        return Optional.empty();
    }

    @Override
    public QueueManagement createQueueManagement(String priorityStatus, String hospitalName) {
        PriorityStatus status = PriorityStatus.valueOf(priorityStatus);
        String queueNumber = generateQueueNumber(status);
        
        Optional<QueueManagement> existingQueueManagement = queueManagementRepository.findByQueueNumber(queueNumber);
        if (existingQueueManagement.isPresent()) {
            throw new RuntimeException("Queue number already exists: " + queueNumber);
        }

        QueueManagement queueManagement = new QueueManagement();
        queueManagement.setPriorityStatus(status);
        queueManagement.setQueueNumber(queueNumber);
        queueManagement.setArrivalTime(LocalDateTime.now());
        queueManagement.setHospital(hospitalRepository.findByName(hospitalName).get());
        queueManagement.setCalled(false);
        queueManagement.setCalledOnBoard(false);
        return queueManagementRepository.save(queueManagement);
    }

    public String generateQueueNumber(PriorityStatus priorityStatus) {
        List<QueueManagement> queueManagements = queueManagementRepository.findByPriorityStatusOrderByQueueNumberDesc(priorityStatus);
        
        List<Integer> numbers = new ArrayList<>();

        for (QueueManagement qm : queueManagements) {
            String queueNumber = qm.getQueueNumber().substring(1);
            int number = Integer.parseInt(queueNumber);
            numbers.add(number);
        }

        numbers.sort(Collections.reverseOrder());

        if (queueManagements.isEmpty()) {
            return priorityStatus + "1";
        } else {
            Integer lastNumber = numbers.get(0);
            String newQueueNumber = String.valueOf(lastNumber + 1);
            return priorityStatus + newQueueNumber;
        }
    }
    

}