package pt.ua.deti.tqs.cliniconnect.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.*;
import pt.ua.deti.tqs.cliniconnect.PriorityStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class QueueManagement implements java.io.Serializable {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private PriorityStatus priorityStatus;

    private LocalDateTime arrivalTime;
    private LocalDateTime calledTime;

    private boolean isCalled;
    private boolean isCalledOnBoard;
    private String counterNumber;

    @Column(unique = true)
    private String queueNumber;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
}
