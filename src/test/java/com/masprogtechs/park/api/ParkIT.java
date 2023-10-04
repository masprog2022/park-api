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
                //.jsonPath("slotCode").exists();


    }
}
