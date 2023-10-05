package com.masprogtechs.park.api;

import com.masprogtechs.park.api.web.dto.ParkCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/parks/parks-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/parks/parks-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ParkIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createCheckIn_WithDataValid_ReturnCreatedAndLocation(){
        ParkCreateDto createDto = ParkCreateDto.builder()
                .plate("NER-1111").make("FIAT").model("PALIO 1.0")
                .color("AZUL").customerCpf("63609496010")
                .build();

        testClient.post().uri("api/v1/parks/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION)
                .expectBody()
                .jsonPath("plate").isEqualTo("NER-1111")
                .jsonPath("make").isEqualTo("FIAT")
                .jsonPath("model").isEqualTo("PALIO 1.0")
                .jsonPath("color").isEqualTo("AZUL")
                .jsonPath("customerCpf").isEqualTo("63609496010")
                .jsonPath("receipt").exists()
                .jsonPath("inputData").exists();
                //.jsonPath("slotCode").exists()

    }

    @Test
    public void createCheckIn_WithRoleCustomer_ReturnErrorMessageStatus403(){
        ParkCreateDto createDto = ParkCreateDto.builder()
                .plate("NER-1111").make("FIAT").model("PALIO 1.0")
                .color("AZUL").customerCpf("63609496010")
                .build();

        testClient.post().uri("/api/v1/parks/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@gmail.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("api/v1/parks/check-in")
                .jsonPath("method").isEqualTo("POST");
        //.jsonPath("slotCode").exists()

    }

    @Test
    public void createCheckIn_WithDataInvalid_ReturnErrorMessage422(){ // validar mais coisas ... por exemplo CPF
        ParkCreateDto createDto = ParkCreateDto.builder()
                .plate("").make("").model("")
                .color("").customerCpf("")
                .build();

        testClient.post().uri("/api/v1/parks/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@gmail.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo("422")
                .jsonPath("path").isEqualTo("/api/v1/parks/check-in")
                .jsonPath("method").isEqualTo("POST");
        //.jsonPath("slotCode").exists()

    }

    @Test
    public void createCheckIn_WithCpfNonExistent_ReturnErrorMessage404(){ // Cpf Não existente no banco de dados
        ParkCreateDto createDto = ParkCreateDto.builder()
                .plate("NER-1111").make("FIAT").model("PALIO 1.0")
                .color("AZUL").customerCpf("16508329073")
                .build();

        testClient.post().uri("/api/v1/parks/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/parks/check-in")
                .jsonPath("method").isEqualTo("POST");
        //.jsonPath("slotCode").exists()

    }

    @Sql(scripts = "/sql/parks/park-insert-slot-busy.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/parks/park-delete-slot-busy.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void createCheckIn_WithSlotBusy_ReturnErrorMessageStatus404(){ // Cpf Não existente no banco de dados
        ParkCreateDto createDto = ParkCreateDto.builder()
                .plate("NER-1111").make("FIAT").model("PALIO 1.0")
                .color("AZUL").customerCpf("63609496010")
                .build();

        testClient.post().uri("/api/v1/parks/check-in")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/parks/check-in")
                .jsonPath("method").isEqualTo("POST");
        //.jsonPath("slotCode").exists()

    }

    @Test
    public void findCheckIn_WithRoleAdmin_ReturnDataStatus200(){ // Cpf Não existente no banco de dados

        testClient.get()
                .uri("/api/v1/parks/check-in/{receipt}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("plate").isEqualTo("FIT-1020")
                .jsonPath("make").isEqualTo("FIAT")
                .jsonPath("model").isEqualTo("PALIO")
                .jsonPath("color").isEqualTo("VERDE")
                .jsonPath("customerCpf").isEqualTo("52186821010")
                .jsonPath("receipt").isEqualTo("20230313-101300")
                .jsonPath("inputData").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("slotCode").isEqualTo("A-01");
    }

    @Test
    public void findCheckIn_WithRoleCustomer_ReturnDataStatus200(){ // Cpf Não existente no banco de dados

        testClient.get()
                .uri("/api/v1/parks/check-in/{receipt}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("plate").isEqualTo("FIT-1020")
                .jsonPath("make").isEqualTo("FIAT")
                .jsonPath("model").isEqualTo("PALIO")
                .jsonPath("color").isEqualTo("VERDE")
                .jsonPath("customerCpf").isEqualTo("52186821010")
                .jsonPath("receipt").isEqualTo("20230313-101300")
                .jsonPath("inputData").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("slotCode").isEqualTo("A-01");

    }

    @Test
    public void findCheckIn_WithReceiptNonExistent_ReturnErrorStatus404(){ // Cpf Não existente no banco de dados

        testClient.get()
                .uri("/api/v1/parks/check-in/{receipt}", "20230313-999999")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/parks/check-in/20230313-999999")
                .jsonPath("method").isEqualTo("GET");
    }


    @Test
    public void createCheckout_WithReceiptExistent_ReturnSuccess(){
        testClient.put()
                .uri("/api/v1/parks/check-out/{receipt}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("plate").isEqualTo("FIT-1020")
                .jsonPath("make").isEqualTo("FIAT")
                .jsonPath("model").isEqualTo("PALIO")
                .jsonPath("color").isEqualTo("VERDE")
                .jsonPath("inputData").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("customerCpf").isEqualTo("52186821010")
                .jsonPath("slotCode").isEqualTo("A-01")
                .jsonPath("receipt").isEqualTo("20230313-101300")
                .jsonPath("outputData").exists()
                .jsonPath("charge").exists()
                .jsonPath("discount").exists();
    }


    @Test
    public void createCheckout_WithReceiptNonExistent_ReturnErrorStatus404(){
        testClient.put()
                .uri("/api/v1/parks/check-out/{receipt}", "20230313-101377")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/parks/check-out/20230313-101377")
                .jsonPath("method").isEqualTo("PUT");
    }

    @Test
    public void createCheckout_WithRoleCustomer_ReturnErrorStatus403(){
        testClient.put()
                .uri("/api/v1/parks/check-out/{receipt}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/parks/check-out/20230313-101300")
                .jsonPath("method").isEqualTo("PUT");
    }


}
