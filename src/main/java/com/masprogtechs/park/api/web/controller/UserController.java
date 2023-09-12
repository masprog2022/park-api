package com.masprogtechs.park.api.web.controller;

import com.masprogtechs.park.api.entity.User;
import com.masprogtechs.park.api.service.UserService;
import com.masprogtechs.park.api.web.dto.UserCreateDto;
import com.masprogtechs.park.api.web.dto.UserPasswordDto;
import com.masprogtechs.park.api.web.dto.UserResponseDto;
import com.masprogtechs.park.api.web.dto.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PostMapping
    @Operation(summary = "Salvar um usuário", description = "Salvar um usuário",
            tags = {"Usuário"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UserCreateDto.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto createDto){
        User user = userService.save(UserMapper.toUser(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

   /* @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }*/

    @GetMapping("/{id}")
    @Operation(summary = "Mostrar usuários por id", description = "Mostrar usuários por id",
            tags = {"Usuário"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @GetMapping
    @Operation(summary = "Mostrar todos os usuários", description = "Mostrar todos os usuários",
            tags = {"Usuário"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
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
    @Operation(summary = "Actualizar senha do usuário", description = "Actualizar senha do usuário",
            tags = {"Usuário"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = User.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                                                          @Valid @RequestBody UserPasswordDto dto){
        User user = userService.updatePassword(id, dto.getCurrentPassword(),
                dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }


}
