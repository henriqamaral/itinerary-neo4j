
package br.com.route.domain;

import lombok.Getter;

/**
 * @author Henrique E. do Amaral
 * @see Path
 * @since 1.0.0.RELEASE
 */
@Getter
public class Path {

  private final String departureCity;
  private final String arrivalCity;
  private final Integer totalConnections;
  private final Integer totalInTime;

   private Path(String departureCity, String arrivalCity, Integer totalConnections,
       Integer totalInTime) {
    this.departureCity = departureCity;
    this.arrivalCity = arrivalCity;
    this.totalConnections = totalConnections;
     this.totalInTime = totalInTime;
   }

  public static Path createConnectionPath(String departureCity, String arrivalCity, Integer totalConnections){
    return new Path(departureCity, arrivalCity, totalConnections, null);
  }

  public static Path createTimePath(String departureCity, String arrivalCity, Integer totalInTime){
    return new Path(departureCity, arrivalCity, null, totalInTime);
  }
}
