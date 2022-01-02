package com.joumer.webservice.service;

import com.joumer.webservice.document.Role;
import com.joumer.webservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
