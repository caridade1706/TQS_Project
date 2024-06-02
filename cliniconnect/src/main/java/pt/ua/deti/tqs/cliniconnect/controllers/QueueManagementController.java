package pt.ua.deti.tqs.cliniconnect.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.models.QueueManagement;
import pt.ua.deti.tqs.cliniconnect.services.QueueManagementService;

import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/queue-management")
public class QueueManagementController {

    private final QueueManagementService queueManagementService;

    @GetMapping
    public ResponseEntity<QueueManagement> getAllQueueManagements() {
        QueueManagement queueManagement = queueManagementService.getAllQueueManagements();
        if (queueManagement != null) {
            return ResponseEntity.ok(queueManagement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<QueueManagement> getQueueManagementById(@PathVariable UUID id) {
        Optional<QueueManagement> queueManagement = queueManagementService.queueManagementService(id);
        return queueManagement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public QueueManagement createQueueManagement(@RequestParam String priorityStatus, @RequestParam String hospitalName) {
        return queueManagementService.createQueueManagement(priorityStatus, hospitalName);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQueueManagement(@PathVariable UUID id) {
        queueManagementService.deleteQueueManagementById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/call-next")
    public ResponseEntity<QueueManagement> callNextTicket(@RequestParam String priorityStatus, @RequestParam String counter) {
        Optional<QueueManagement> nextTicket = queueManagementService.callNextTicket(priorityStatus, counter);
        return nextTicket.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}