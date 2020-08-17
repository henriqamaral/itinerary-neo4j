
package br.com.route.gateways.http;

import br.com.route.domain.Route;
import br.com.route.gateways.http.jsons.RouteResource;
import br.com.route.usecases.CreateRoute;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Henrique E. do Amaral
 * @see RouteController
 * @since 1.0.0.RELEASE
 */
@RestController
@RequestMapping("/routes")
public class RouteController {

  private final CreateRoute createRoute;

  public RouteController(CreateRoute createRoute) {
    this.createRoute = createRoute;
  }

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public Mono<Route> creteRoute(@RequestBody RouteResource route) {
    return createRoute.execute(route.toDomain());
  }

}
