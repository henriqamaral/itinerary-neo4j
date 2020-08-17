
package br.com.route.usecases;

import br.com.route.domain.Path;
import br.com.route.gateways.CityGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Henrique E. do Amaral
 * @see ShortestPath
 * @since 1.0.0.RELEASE
 */
@Component
@RequiredArgsConstructor
public class ShortestPath {
  private final CityGateway cityGateway;


  public Mono<Path> execute(String from, String to) {
    return cityGateway.getShortestPath(from, to);
  }
}
