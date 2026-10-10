package com.example.helpdesk.service;
import com.example.helpdesk.model.*;import com.example.helpdesk.repo.UserRepository;import com.example.helpdesk.security.JwtService;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import java.util.Map;
@Service public class AuthService{
 private final UserRepository users;private final PasswordEncoder enc;private final JwtService jwt;public AuthService(UserRepository u,PasswordEncoder e,JwtService j){users=u;enc=e;jwt=j;}
 @Transactional public Object register(String name,String email,String password){email=email.toLowerCase();if(users.findByEmail(email).isPresent())throw new IllegalArgumentException("Email already registered");AppUser u=new AppUser();u.setName(name);u.setEmail(email);u.setPasswordHash(enc.encode(password));u.setRole(Role.USER);users.save(u);return dto(u);}
 public Object login(String email,String password){AppUser u=users.findByEmail(email.toLowerCase()).orElseThrow(()->new IllegalArgumentException("Invalid email or password"));if(!u.isEnabled()||!enc.matches(password,u.getPasswordHash()))throw new IllegalArgumentException("Invalid email or password");return dto(u);}
 private Object dto(AppUser u){return Map.of("token",jwt.generate(u),"id",u.getId(),"name",u.getName(),"email",u.getEmail(),"role",u.getRole().name());}
}
