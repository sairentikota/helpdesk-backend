package com.example.helpdesk.web;
import com.example.helpdesk.model.*;import com.example.helpdesk.repo.*;import com.example.helpdesk.service.*;import jakarta.validation.Valid;import jakarta.validation.constraints.*;import org.springframework.data.domain.Page;import org.springframework.http.*;import org.springframework.security.core.Authentication;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.web.bind.annotation.*;import java.time.LocalDate;import java.util.*;
@RestController @RequestMapping("/api") public class ApiController{
  private final AuthService auth;private final TicketService svc;private final UserRepository users;private final CategoryRepository cats;private final PasswordEncoder enc;private final org.springframework.jdbc.core.JdbcTemplate jdbc;
  public ApiController(AuthService a,TicketService s,UserRepository u,CategoryRepository c,PasswordEncoder e,org.springframework.jdbc.core.JdbcTemplate j){auth=a;svc=s;users=u;cats=c;enc=e;jdbc=j;}
  private AppUser me(Authentication a){return users.findByEmail(a.getName()).orElseThrow();}
  private void admin(AppUser u){if(u.getRole()!=Role.ADMIN)throw new SecurityException("Admin only");}
  public record Register(@NotBlank @Size(max=120)String name,@Email @NotBlank String email,@Size(min=8,max=72)String password){}
  public record Login(@Email @NotBlank String email,@NotBlank String password){}
  public record NewTicket(@NotBlank @Size(max=180)String title,@NotBlank @Size(max=10000)String description,@NotNull Long categoryId,@NotNull Priority priority){}
  public record Change(@NotNull TicketStatus status,String resolutionNotes){}
  public record CommentBody(@NotBlank @Size(max=5000)String body){}
  public record NewUser(@NotBlank String name,@Email @NotBlank String email,@Size(min=8,max=72)String password,@NotNull Role role){}
  @PostMapping("/auth/register")Object register(@Valid @RequestBody Register r){return auth.register(r.name(),r.email(),r.password());}
  @PostMapping("/auth/login")Object login(@Valid @RequestBody Login r){return auth.login(r.email(),r.password());}
  @GetMapping("/categories")List<Category> categories(){return cats.findAll();}
  @GetMapping("/tickets")Page<Ticket> tickets(Authentication a,@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="10")int size,@RequestParam(required=false)TicketStatus status,@RequestParam(required=false)Priority priority,@RequestParam(required=false)String category,@RequestParam(required=false)Long agentId,@RequestParam(required=false)LocalDate from,@RequestParam(required=false)LocalDate to){return svc.list(me(a),page,size,status,priority,category,agentId,from,to);}
  @PostMapping("/tickets")ResponseEntity<Ticket> create(Authentication a,@Valid @RequestBody NewTicket r){return ResponseEntity.status(201).body(svc.create(me(a),r.title(),r.description(),r.categoryId(),r.priority()));}
  @GetMapping("/tickets/{id}")Ticket ticket(Authentication a,@PathVariable Long id){return svc.get(me(a),id);}
  @PatchMapping("/tickets/{id}/status")Ticket status(Authentication a,@PathVariable Long id,@Valid @RequestBody Change r){return svc.status(me(a),id,r.status(),r.resolutionNotes());}
  @PostMapping("/tickets/{id}/comments")ResponseEntity<TicketComment> comment(Authentication a,@PathVariable Long id,@Valid @RequestBody CommentBody r){return ResponseEntity.status(201).body(svc.comment(me(a),id,r.body()));}
  @GetMapping("/tickets/{id}/comments")List<TicketComment> comments(Authentication a,@PathVariable Long id){return svc.comments(me(a),id);}
  @PostMapping("/tickets/{id}/assign/{agentId}")Ticket assign(Authentication a,@PathVariable Long id,@PathVariable Long agentId){admin(me(a));return svc.assign(id,agentId);}
  @GetMapping("/dashboard/stats")Map<String,Object> stats(){return svc.stats();}
  @GetMapping("/admin/users")List<Map<String,Object>> userList(Authentication a){admin(me(a));return users.findAll().stream().map(u->Map.<String,Object>of("id",u.getId(),"name",u.getName(),"email",u.getEmail(),"role",u.getRole().name(),"enabled",u.isEnabled())).toList();}
  @PostMapping("/admin/users")ResponseEntity<?> newUser(Authentication a,@Valid @RequestBody NewUser r){admin(me(a));if(users.findByEmail(r.email().toLowerCase()).isPresent())throw new IllegalArgumentException("Email already exists");AppUser u=new AppUser();u.setName(r.name());u.setEmail(r.email().toLowerCase());u.setPasswordHash(enc.encode(r.password()));u.setRole(r.role());users.save(u);return ResponseEntity.status(201).body(Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail(),"role",u.getRole().name()));}
  @org.springframework.transaction.annotation.Transactional
  @DeleteMapping("/admin/users/{id}")ResponseEntity<?> deleteUser(Authentication a,@PathVariable Long id){admin(me(a));jdbc.update("UPDATE tickets SET assigned_agent_id = NULL WHERE assigned_agent_id = ?", id);jdbc.update("DELETE FROM ticket_comments WHERE author_id = ?", id);jdbc.update("DELETE FROM ticket_comments WHERE ticket_id IN (SELECT id FROM tickets WHERE requester_id = ?)", id);jdbc.update("DELETE FROM tickets WHERE requester_id = ?", id);users.deleteById(id);return ResponseEntity.noContent().build();}
}
