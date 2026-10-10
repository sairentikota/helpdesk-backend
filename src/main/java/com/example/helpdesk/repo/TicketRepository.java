package com.example.helpdesk.repo;
import com.example.helpdesk.model.*;import org.springframework.data.jpa.repository.*;import org.springframework.data.repository.query.Param;import org.springframework.data.domain.*;import java.time.LocalDateTime;
public interface TicketRepository extends JpaRepository<Ticket,Long>{
 @Query("select t from Ticket t where (:role='ADMIN' or (:role='AGENT' and t.assignedAgent.id=:uid) or (:role='USER' and t.requester.id=:uid)) and (:status is null or t.status=:status) and (:priority is null or t.priority=:priority) and (:category is null or lower(t.category.name)=lower(:category)) and (:agentId is null or t.assignedAgent.id=:agentId) and (:start is null or t.createdAt>=:start) and (:end is null or t.createdAt<=:end)")
 Page<Ticket> filtered(@Param("role") String role,@Param("uid") Long uid,@Param("status") TicketStatus status,@Param("priority") Priority priority,@Param("category") String category,@Param("agentId") Long agentId,@Param("start") LocalDateTime start,@Param("end") LocalDateTime end,Pageable pageable);
 long countByStatus(TicketStatus status);
}
