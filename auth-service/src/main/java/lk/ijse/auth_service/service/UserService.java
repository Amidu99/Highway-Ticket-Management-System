package lk.ijse.auth_service.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    boolean existsByEmail(String email);
}