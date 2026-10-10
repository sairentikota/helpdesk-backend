package com.example.helpdesk.repo;
import com.example.helpdesk.model.*;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface UserRepository extends JpaRepository<AppUser,Long>{Optional<AppUser> findByEmail(String email);List<AppUser> findByRole(Role role);}
