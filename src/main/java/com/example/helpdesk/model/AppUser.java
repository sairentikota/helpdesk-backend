package com.example.helpdesk.model;
import jakarta.persistence.*;
@Entity @Table(name="app_users")
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(nullable=false,unique=true) private String email;
 @Column(name="password_hash",nullable=false) private String passwordHash;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role;
 private boolean enabled=true;
 public Long getId(){return id;} public String getName(){return name;} public void setName(String x){name=x;}
 public String getEmail(){return email;} public void setEmail(String x){email=x;}
 public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String x){passwordHash=x;}
 public Role getRole(){return role;} public void setRole(Role x){role=x;}
 public boolean isEnabled(){return enabled;} public void setEnabled(boolean x){enabled=x;}
}
