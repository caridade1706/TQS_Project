package pt.ua.deti.tqs.cliniconnect.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Get all queue managements")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrive all queue managements"),
        @ApiResponse(responseCode = "404", description = "Queue managements could not be retrived")
    })
    @GetMapping
    public ResponseEntity<QueueManagement> getAllQueueManagements() {
        QueueManagement queueManagement = queueManagementService.getAllQueueManagements();
        if (queueManagement != null) {
            return ResponseEntity.ok(queueManagement);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get queue management by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrive a queue management"),
        @ApiResponse(responseCode = "404", description = "Queue management could not be retrived")
    })
    @GetMapping("/{id}")
    public ResponseEntity<QueueManagement> getQueueManagementById(@PathVariable UUID id) {
        Optional<QueueManagement> queueManagement = queueManagementService.queueManagementService(id);
        return queueManagement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new queue management")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created the queue management"),
        @ApiResponse(responseCode = "404", description = "Queue management could not be created")
    })
    @PostMapping
    public QueueManagement createQueueManagement(@RequestParam String priorityStatus, @RequestParam String hospitalName) {
        return queueManagementService.createQueueManagement(priorityStatus, hospitalName);
    }

    @Operation(summary = "Delete a queue management")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted the queue management"),
        @ApiResponse(responseCode = "404", description = "Queue management could not be deleted")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQueueManagement(@PathVariable UUID id) {
        queueManagementService.deleteQueueManagementById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Call next ticket")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully called the next ticket"),
        @ApiResponse(responseCode = "404", description = "No ticket to call")
    })
    @GetMapping("/call-next")
    public ResponseEntity<QueueManagement> callNextTicket(@RequestParam String priorityStatus, @RequestParam String counter) {
        Optional<QueueManagement> nextTicket = queueManagementService.callNextTicket(priorityStatus, counter);
        return nextTicket.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}