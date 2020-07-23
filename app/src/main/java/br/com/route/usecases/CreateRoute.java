package br.com.route.usecases;

import br.com.route.domain.City;
import br.com.route.domain.Route;
import br.com.route.gateways.CityGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateRoute {

  private final CityGateway cityGateway;

  public Mono<Route> execute(final Route route) {

    final Mono<City> fromCity = cityGateway.findByName(route.getFrom())
        .switchIfEmpty(cityGateway.save(new City(route.getFrom())));

    final Mono<City> toCity = cityGateway.findByName(route.getDestiny())
        .switchIfEmpty(cityGateway.save(new City(route.getDestiny())));

    return fromCity
        .zipWith(toCity)
        .map(cities -> {
          cities.getT1().addRoute(cities.getT2(), route.getDepartureTime(), route.getArrivalTime());
          return cities.getT1();
        }).flatMap(cityGateway::save).map(city -> route);
  }

}
