package com.example.helpdesk.model;
import jakarta.persistence.*;import java.time.LocalDateTime;
@Entity @Table(name="ticket_comments") public class TicketComment {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="ticket_id") private Ticket ticket;
 @ManyToOne(optional=false) @JoinColumn(name="author_id") private AppUser author;
 @Column(nullable=false,columnDefinition="TEXT") private String body;
 @Column(name="created_at",insertable=false,updatable=false) private LocalDateTime createdAt;
 public Long getId(){return id;} public Ticket getTicket(){return ticket;} public void setTicket(Ticket x){ticket=x;}
 public AppUser getAuthor(){return author;} public void setAuthor(AppUser x){author=x;}
 public String getBody(){return body;} public void setBody(String x){body=x;} public LocalDateTime getCreatedAt(){return createdAt;}
}
