package pt.ua.deti.tqs.cliniconnect.repositories;

import pt.ua.deti.tqs.cliniconnect.PriorityStatus;
import pt.ua.deti.tqs.cliniconnect.models.QueueManagement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueManagementRepository extends JpaRepository<QueueManagement, Long> {
    Optional<QueueManagement> findById(UUID id);
    void deleteById(UUID id);
    
    List<QueueManagement> findByPriorityStatusAndCalledTimeIsNullOrderByArrivalTimeAsc(PriorityStatus priorityStatus);
    List<QueueManagement> findByPriorityStatusOrderByQueueNumberDesc(PriorityStatus priorityStatus);
    List<QueueManagement> findByPriorityStatus(PriorityStatus priorityStatus);
    Optional<QueueManagement> findByQueueNumber(String queueNumber);

    @Query("SELECT q FROM QueueManagement q WHERE q.isCalled = true and q.isCalledOnBoard = false ORDER BY q.calledTime DESC")
    List<QueueManagement> findFirstByCalledTrueAndCalledOnBoardFalseOrderByCalledTimeDesc(Pageable pageable);
}
