package com.gatling;

import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class HelloTestSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = HttpDsl.http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json")
            .userAgentHeader("Gatling/ Hello Performance Test");

    ScenarioBuilder scn = CoreDsl.scenario("Load Test for Hello")
            .exec(http("Get Hello Request")
                    .get("/api/v1/hello")
                    .check(status().is(200))
            );

    public HelloTestSimulation() {
        this.setUp(scn.injectOpen(constantUsersPerSec(50).during(Duration.ofSeconds(15))))
                .protocols(httpProtocol);
    }
}
