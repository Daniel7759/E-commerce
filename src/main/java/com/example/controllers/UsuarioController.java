package com.example.controllers;

import com.example.models.RoleEntity;
import com.example.models.UsuarioEntity;
import com.example.repositories.RoleRepository;
import com.example.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RoleRepository roleRepository;

    public UsuarioController(UsuarioService usuarioService, RoleRepository roleRepository) {
        this.usuarioService = usuarioService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(usuarioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.findByID(id));
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getRoles(){
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @PostMapping("/roles")
    public ResponseEntity<?> createRole(@RequestBody RoleEntity role){
        return ResponseEntity.ok(roleRepository.save(role));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UsuarioEntity usuario){
        return ResponseEntity.ok(usuarioService.insert(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password){
        try {
            return ResponseEntity.ok(usuarioService.login(email, password));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
