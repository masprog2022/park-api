package com.masprogtechs.park.api;

import com.masprogtechs.park.api.web.dto.CustomerCreateDto;
import com.masprogtechs.park.api.web.dto.CustomerResponseDto;
import com.masprogtechs.park.api.web.exception.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/customers/customers-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/customers/customers-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CustomerIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createCustomer_WithDateValid_ReturnCustomerWithStatus201(){
       CustomerResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new CustomerCreateDto("Tobias Ferreira", "76327474004"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CustomerResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Tobias Ferreira");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("76327474004");
    }

    @Test
    public void createCustomer_WithCpfRegistered_ReturnErrorMessageWithStatus409(){
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new CustomerCreateDto("Tobias Ferreira", "52186821010"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void createCustomer_WithDateInvalid_ReturnErrorMessageWithStatus422(){
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new CustomerCreateDto("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new CustomerCreateDto("Bobb", "00000000000"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@gmail.com", "123456"))
                .bodyValue(new CustomerCreateDto("Bobb", "521.868.210-10"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createCustomer_WithUserNotAllowed_ReturnErrorMessageWithStatus403(){
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .bodyValue(new CustomerCreateDto("Tobias Ferreira", "52186821010"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }


}