
package br.com.route.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.RelationshipProperties;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Henrique E. do Amaral
 * @see RouteEntity
 * @since 1.0.0.RELEASE
 */
@RelationshipProperties
@Getter
@NoArgsConstructor
public class RouteEntity {

  @Id
  @GeneratedValue
  private Long id;

  public RouteEntity(String departureTime, String arriveTime) {
    this.departureTime = departureTime;
    this.arriveTime = arriveTime;
    this.duration = calculateDuration();
  }

  private String departureTime;

  private String arriveTime;

  private Long duration;

  private long calculateDuration() {

    final LocalDateTime departureDate =
        LocalDateTime.of(0, 1, 1, getHours(departureTime), getMinutes(departureTime));
    final LocalDateTime arrivalDate =
        LocalDateTime.of(0, 1, 1, getHours(arriveTime), getMinutes(arriveTime));

    return Duration.between(departureDate, arrivalDate).toHours();
  }

  private int getHours(final String time) {
    return Integer.parseInt(time.split(":")[0]);
  }

  private int getMinutes(final String time) {
    return Integer.parseInt(time.split(":")[1]);
  }
}
