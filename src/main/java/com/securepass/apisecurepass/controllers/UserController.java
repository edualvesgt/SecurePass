package com.securepass.apisecurepass.controllers;

import com.securepass.apisecurepass.dtos.UserDto;
import com.securepass.apisecurepass.models.UserModel;
import com.securepass.apisecurepass.repositories.UserRepository;
import com.securepass.apisecurepass.services.FileUploadService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = {"application/json"})
public class UserController {

    // Injeção da dependência do repositório do usuário
    @Autowired
    UserRepository userRepository;

    @Autowired
    FileUploadService fileUploadService;

    // Endpoint para listar todos os usuários
    @GetMapping
    public ResponseEntity<List<UserModel>> listUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    // Endpoint para cadastrar um novo usuário
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> userRegister(@ModelAttribute UserDto userDto) {
        // Verifica se o email já está cadastrado
        if (userRepository.findByEmail(userDto.email()) != null) {
            // Se o email já existe, retorna um erro
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse email já está cadastrado!");
        }

        // Converte o DTO do usuário para a entidade UserModel
        UserModel user = new UserModel();
        BeanUtils.copyProperties(userDto, user);

        // Salva o novo usuário no banco de dados
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }
}
