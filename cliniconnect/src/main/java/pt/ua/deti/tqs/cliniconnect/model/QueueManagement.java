package pt.ua.deti.tqs.cliniconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "queue_management")
public class QueueManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "queue_id")
    private Long id;

    @Column(name = "current_number")
    private int currentNumber;

    @Column(name = "priority_status")
    private boolean priorityStatus;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public boolean isPriorityStatus() {
        return priorityStatus;
    }

    public void setPriorityStatus(boolean priorityStatus) {
        this.priorityStatus = priorityStatus;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}