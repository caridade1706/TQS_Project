package pt.ua.deti.tqs.cliniconnect.models;

import java.util.Date;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class QueueManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String priorityStatus;
    private Date arrivalTime;
    private String queueNumber;

    @OneToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
}
