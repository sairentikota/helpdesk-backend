package com.example.helpdesk.repo;
import com.example.helpdesk.model.TicketComment;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface CommentRepository extends JpaRepository<TicketComment,Long>{List<TicketComment> findByTicketIdOrderByCreatedAtAsc(Long id);}
