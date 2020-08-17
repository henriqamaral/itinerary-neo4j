

package br.com.route.gateways.http.jsons;

import br.com.route.domain.Path;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;

/**
 * @author Henrique E. do Amaral
 * @see PathResource
 * @since 1.0.0.RELEASE
 */
@Getter
@JsonInclude(Include.NON_NULL)
public class PathResource {

  private final String departureCity;
  private final String arrivalCity;
  private final Integer totalConnections;
  private final Integer totalHours;

  public PathResource(final Path path) {

    this.departureCity = path.getDepartureCity();
    this.arrivalCity = path.getArrivalCity();
    this.totalConnections = path.getTotalConnections();
    this.totalHours = path.getTotalInTime();
  }

}
