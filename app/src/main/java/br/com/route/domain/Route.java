package br.com.route.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(exclude = {"id"})
public class Route {
  @Id private String id;
  private String from;
  private String destiny;
  private String departureTime;
  private String arrivalTime;

  public Route(String from, String destinyCity, String departureTime, String arrivalTime) {
    this.from = from;
    this.destiny = destinyCity;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
  }
}
