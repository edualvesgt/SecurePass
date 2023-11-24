package com.securepass.apisecurepass.repositories;

import com.securepass.apisecurepass.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    // Método para buscar um usuário pelo email
    UserRepository findByEmail(String email);
}
