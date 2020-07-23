/*
 * Copyright (C) 2020 adidas AG.
 */

package br.com.route.gateways.neo4j.repositories;

import br.com.route.domain.City;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.springframework.data.repository.ReactiveNeo4jRepository;
import org.neo4j.springframework.data.repository.query.Query;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Henrique E. do Amaral
 * @see CityRepository
 * @since 1.0.0.RELEASE
 */
public interface CityRepository extends ReactiveNeo4jRepository<City, Long> {

  Mono<City> findByName(String name);

  @Query("MATCH p=shortestPath((a:City {name:$from})-[*]->(b:City {name:$to})) RETURN p")
  Flux<PathValue> shortestPath(@Param("from") String from, @Param("to") String to);

  @Query("MATCH (a:City {name: $from})\n"
      + "MATCH (b:City {name: $to})\n"
      + "CALL apoc.algo.dijkstra(a, b, 'TO', 'duration')\n"
      + "YIELD path, weight\n"
      + "RETURN path\n"
      + "ORDER BY weight ASC LIMIT 1")
  Flux<PathValue> shortestPathInTime(@Param("from") String from, @Param("to") String to);

}
