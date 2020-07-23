package br.com.route.gateways.http;

import br.com.route.gateways.http.jsons.RouteResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouteControllerTest {

  private static final String DEPARTURE_TIME = "11:00";
  private static final String ARRIVAL_TIME = "12:00";
  private static final String FROM_CITY = "Sao Paulo";
  private static final String DESTINY_CITY = "Rio de Janeiro";


  @Autowired
  WebTestClient webTestClient;

  @Container
  private static Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:4.0");

  @DynamicPropertySource
  static void neo4jProperties(DynamicPropertyRegistry registry) {
    registry.add("org.neo4j.driver.uri", neo4jContainer::getBoltUrl);
    registry.add("org.neo4j.driver.authentication.username", () -> "neo4j");
    registry.add("org.neo4j.driver.authentication.password", neo4jContainer::getAdminPassword);
  }

  @Test
  public void given_route_create_with_success() {
    final RouteResource route = new RouteResource("Macapa", DESTINY_CITY, DEPARTURE_TIME,
        ARRIVAL_TIME);

    webTestClient
        .post()
        .uri("/routes")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(route), RouteResource.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.from")
        .isEqualTo("Macapa");
  }

}
