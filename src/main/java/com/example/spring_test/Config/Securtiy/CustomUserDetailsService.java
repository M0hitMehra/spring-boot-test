package com.example.spring_test.Config.Securtiy;

import com.example.spring_test.models.Employee;
import com.example.spring_test.repositories.EmployeeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    CustomUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee user = employeeRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User Not found"
                ));

        return new CustomUserDetails(user);
    }
}
