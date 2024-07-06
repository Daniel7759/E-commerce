package com.example.services;

import com.example.models.RoleEntity;
import com.example.models.RoleEnum;
import com.example.models.UsuarioEntity;
import com.example.repositories.RoleRepository;
import com.example.repositories.UsuarioRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public UsuarioEntity insert(UsuarioEntity usuario) {
        encodePassword(usuario);
        assignUsuarioRole(usuario);
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Optional<UsuarioEntity> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public UsuarioEntity findByID(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<UsuarioEntity> getAll() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public UsuarioEntity login(String email, String password) {
        Optional<UsuarioEntity> userOptional = usuarioRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("El email " + email + " no está registrado.");
        }
        UsuarioEntity user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta.");
        }
        return user;
    }


    private void assignUsuarioRole(UsuarioEntity user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            RoleEntity userRole = roleRepository.findByName(RoleEnum.ROLE_USER);
            if (userRole == null) {
                userRole = RoleEntity.builder().name(RoleEnum.ROLE_USER).build();
                roleRepository.save(userRole);
            }
            user.setRoles(Collections.singleton(userRole));
        }
    }

    private void encodePassword(UsuarioEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
