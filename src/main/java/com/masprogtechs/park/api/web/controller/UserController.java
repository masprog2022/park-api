package com.masprogtechs.park.api.web.controller;

import com.masprogtechs.park.api.entity.User;
import com.masprogtechs.park.api.service.UserService;
import com.masprogtechs.park.api.web.dto.UserCreateDto;
import com.masprogtechs.park.api.web.dto.UserPasswordDto;
import com.masprogtechs.park.api.web.dto.UserResponseDto;
import com.masprogtechs.park.api.web.dto.mapper.UserMapper;
import com.masprogtechs.park.api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuário", description = "Endpoints para gerenciar usuários" )
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

  /*  @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
        User userSave = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
    }*/


    @PostMapping("/admin")
    @Operation(summary = "Criar um novo usuário admin", description = "Recurso para criar um novo usuário sdmin",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    public ResponseEntity<UserResponseDto> createUserAdmin(@Valid @RequestBody UserCreateDto createDto){
        User user = userService.saveUserAdmin(UserMapper.toUser(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @PostMapping("/customer")
    @Operation(summary = "Criar um novo usuário customer", description = "Recurso para criar um novo usuário customer",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    public ResponseEntity<UserResponseDto> createUserCustomer(@Valid @RequestBody UserCreateDto createDto){
        User user = userService.saveUserCustomer(UserMapper.toUser(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }


   /* @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }*/

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CUSTOMER') AND #id == authentication.principal.id)")
    @Operation(summary = "Recuperar um usuário pelo id", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN | CUSTOMER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos os usuários cadastrados", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })
    public ResponseEntity<List<UserResponseDto>> getAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }

   /* @PatchMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user){
        User userUpdate = userService.updatePassword(id, user.getPassword());
        return ResponseEntity.ok(userUpdate);
    }

        @PatchMapping("/{id}")
        public ResponseEntity<UserResponseDto> updatePassword(@PathVariable Long id,
                                                              @RequestBody UserPasswordDto dto){
        User user = userService.updatePassword(id, dto.getCurrentPassword(),
                dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.ok(UserMapper.toDto(user));
    }*/

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER') AND (#id == authentication.principal.id)")
    @Operation(summary = "Atualizar senha", description = "Requisição exige um Bearer Token. Acesso restrito a ADMIN | CUSTOMER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Senha não confere",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos invalidos ou mal formatados",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                                                          @Valid @RequestBody UserPasswordDto dto){
      userService.updatePassword(id, dto.getCurrentPassword(),
                dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }


}
