package lk.ijse.auth_service.service.impl;

import lk.ijse.auth_service.repo.UserRepo;
import lk.ijse.auth_service.service.UserService;
import lk.ijse.auth_service.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {
    private final UserRepo repo;
    private final Mapping mapping;

    @Override
    public UserDetailsService userDetailsService() {
        return email ->
                repo.findByEmail(email).
                        orElseThrow(()->new UsernameNotFoundException("User Not Found!"));
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }
}