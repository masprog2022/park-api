package com.masprogtechs.park.api.web.controller;

import com.masprogtechs.park.api.entity.Customer;
import com.masprogtechs.park.api.jwt.JwtUserDetails;
import com.masprogtechs.park.api.repository.CustomerRepository;
import com.masprogtechs.park.api.repository.projection.CustomerProjection;
import com.masprogtechs.park.api.service.CustomerService;
import com.masprogtechs.park.api.service.UserService;
import com.masprogtechs.park.api.web.dto.CustomerCreateDto;
import com.masprogtechs.park.api.web.dto.CustomerResponseDto;
import com.masprogtechs.park.api.web.dto.PageableDto;
import com.masprogtechs.park.api.web.dto.UserResponseDto;
import com.masprogtechs.park.api.web.dto.mapper.CustomerMapper;
import com.masprogtechs.park.api.web.dto.mapper.PageableMapper;
import com.masprogtechs.park.api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
@Tag(name = "Cliente", description = "Endpoints para gerenciar clientes" )
public class CustomerController {

    private final CustomerService customerService;

    private final UserService userService;

    @Operation(summary = "Criar um novo cliente", description = "Recurso para criar um novo cliente vinculado a usuário cadastrado. " +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='CUSTOMER'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Cliente CPF já possui cadastro no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permite ao perfil de ADMIN",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerResponseDto> create(@RequestBody @Valid CustomerCreateDto dto,
                                           @AuthenticationPrincipal JwtUserDetails userDetails){
        Customer customer = CustomerMapper.toCustomer(dto);
        customer.setUser(userService.findById(userDetails.getId()));

        customerService.save(customer);

        return ResponseEntity.status(201).body(CustomerMapper.toDto(customer));

    }
    @Operation(summary = "localizar um cliente", description = "Recurso para localizar um cliente pelo id. " +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permite ao perfil de CUSTOMER",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDto> getById(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        return ResponseEntity.ok(CustomerMapper.toDto(customer));
    }

    @Operation(summary = "Recuperar lista de clientes",
            description = "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN' ",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Representa a página retornada"
                    ),
                    @Parameter(in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5")),
                            description = "Representa o total de elementos por página"
                    ),
                    @Parameter(in = QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "nome,asc")),
                            description = "Representa a ordenação dos resultados. Aceita multiplos critérios de ordenação são suportados.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CustomerResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAll(@Parameter(hidden = true)
                                                  @PageableDefault(size = 5, sort = {"name"})Pageable pageable){
        Page<CustomerProjection> customers = customerService.findAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(customers));
    }
}
