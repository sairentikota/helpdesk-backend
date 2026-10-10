package com.example.helpdesk.model;
import jakarta.persistence.*;import java.time.LocalDateTime;
@Entity @Table(name="tickets") public class Ticket {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=180) private String title;
 @Column(nullable=false,columnDefinition="TEXT") private String description;
 @ManyToOne(optional=false) @JoinColumn(name="category_id") private Category category;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Priority priority;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private TicketStatus status=TicketStatus.OPEN;
 @ManyToOne(optional=false) @JoinColumn(name="requester_id") private AppUser requester;
 @ManyToOne @JoinColumn(name="assigned_agent_id") private AppUser assignedAgent;
 @Column(name="resolution_notes",columnDefinition="TEXT") private String resolutionNotes;
 @Column(name="created_at",insertable=false,updatable=false) private LocalDateTime createdAt;
 public Long getId(){return id;} public String getTitle(){return title;} public void setTitle(String x){title=x;}
 public String getDescription(){return description;} public void setDescription(String x){description=x;}
 public Category getCategory(){return category;} public void setCategory(Category x){category=x;}
 public Priority getPriority(){return priority;} public void setPriority(Priority x){priority=x;}
 public TicketStatus getStatus(){return status;} public void setStatus(TicketStatus x){status=x;}
 public AppUser getRequester(){return requester;} public void setRequester(AppUser x){requester=x;}
 public AppUser getAssignedAgent(){return assignedAgent;} public void setAssignedAgent(AppUser x){assignedAgent=x;}
 public String getResolutionNotes(){return resolutionNotes;} public void setResolutionNotes(String x){resolutionNotes=x;}
 public LocalDateTime getCreatedAt(){return createdAt;}
}
