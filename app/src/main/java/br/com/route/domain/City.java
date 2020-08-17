
package br.com.route.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Henrique E. do Amaral
 * @see City
 * @since 1.0.0.RELEASE
 */
@Node
@NoArgsConstructor
@Getter
@Setter
public class City {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @Relationship(type = "TO")
  private Map<City, RouteEntity> routes = new HashMap<>();

  public void addRoute(City to, String departureTime, String arriveTime) {
    final RouteEntity routeEntity = new RouteEntity(departureTime, arriveTime);
    routes.put(to, routeEntity);
  }

  public City(String name) {
    this.name = name;
  }

}
