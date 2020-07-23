/*
 * Copyright (C) 2020 adidas AG.
 */

package br.com.route.gateways.http;

import br.com.route.gateways.http.jsons.PathResource;
import br.com.route.usecases.ShortestPath;
import br.com.route.usecases.ShortestPathInTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Henrique E. do Amaral
 * @see ItineraryController
 * @since 1.0.0.RELEASE
 */
@RestController
@RequestMapping("/itineraries")
@RequiredArgsConstructor
public class ItineraryController {

  private final ShortestPath shortestPath;
  private final ShortestPathInTime shortestPathInTime;

  @GetMapping("/shortest-path")
  public Mono<PathResource> getShortestPath(@RequestParam("from") String from,
      @RequestParam("to") String to) {

    return shortestPath.execute(from, to)
        .map(PathResource::new)
        .switchIfEmpty(Mono.error(new IllegalArgumentException("Error")));
  }

  @GetMapping("/shortest-path-in-time")
  public Mono<PathResource>  getShortestPathInTime(@RequestParam("from") String from, @RequestParam("to") String to) {
    return shortestPathInTime.execute(from, to)
        .map(PathResource::new)
        .switchIfEmpty(Mono.error(new IllegalArgumentException("Error")));
  }

  @ResponseStatus(
      value = HttpStatus.NOT_FOUND,
      reason = "Illegal arguments")
  @ExceptionHandler(IllegalArgumentException.class)
  public void illegalArgumentHandler() {
  }

}
