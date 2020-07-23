package br.com.route.gateways.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItineraryControllerTest {

  private static final String DEPARTURE_TIME = "11:00";
  private static final String ARRIVAL_TIME = "12:00";
  private static final String FROM_CITY = "Sao Paulo";
  private static final String DESTINY_CITY = "Rio de Janeiro";

  @Autowired
  private Driver driver;

  @Autowired
  WebTestClient webTestClient;

  @Container
  private static Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j:4.1")
      .withPlugins(MountableFile.forClasspathResource("apoc-4.1.0.1-all.jar"));

  @DynamicPropertySource
  static void neo4jProperties(DynamicPropertyRegistry registry) {
    registry.add("org.neo4j.driver.uri", neo4jContainer::getBoltUrl);
    registry.add("org.neo4j.driver.authentication.username", () -> "neo4j");
    registry.add("org.neo4j.driver.authentication.password", neo4jContainer::getAdminPassword);
  }

  @BeforeEach
  void setupData() throws IOException {
    try (BufferedReader routesReader = new BufferedReader(
        new InputStreamReader(this.getClass().getResourceAsStream("/routes.cypher")));
        Session session = driver.session()) {

      session.writeTransaction(tx -> {
        tx.run("MATCH (n) DETACH DELETE n");
        String routesCypher = routesReader.lines().collect(Collectors.joining(" "));
        tx.run(routesCypher);
        return null;
      });
    }

  }

  @Test
  public void given_from_to_city_return_shortest_path_with_success() {
    webTestClient
        .get()
        .uri("/itineraries/shortest-path?from=Sao Paulo&to=Salvador")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.departureCity")
        .isEqualTo("Sao Paulo")
        .jsonPath("$.arrivalCity")
        .isEqualTo("Salvador")
        .jsonPath("$.totalConnections")
        .isEqualTo("1");
  }

  @Test
  public void given_from_to_city_with_wrong_connection_return_not_found() {
    webTestClient
        .get()
        .uri("/itineraries/shortest-path?from=Sao Paulo&to=Recife")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  public void given_from_to_city_return_shortest_time_path_with_success() {
    webTestClient
        .get()
        .uri("/itineraries/shortest-path-in-time?from=Sao Paulo&to=Salvador")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.departureCity")
        .isEqualTo("Sao Paulo")
        .jsonPath("$.arrivalCity")
        .isEqualTo("Salvador")
        .jsonPath("$.totalInTime")
        .isEqualTo("8");
  }

}
