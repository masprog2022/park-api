package com.masprogtechs.park.api;


import com.masprogtechs.park.api.web.dto.SlotCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/slots/slots-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/slots/slots-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SlotIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createSlot_WithDataValid_ReturnLocationStatus201(){
           testClient
                .post()
                .uri("/api/v1/slots")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
               .bodyValue(new SlotCreateDto("A-05", "FREE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);

    }
}
