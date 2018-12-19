package com.github.robisrob.webclient_bug;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
@AutoConfigureWireMock(port = 9999)
public class WebClientTest {


  @Test
  public void givenRetryWithExpotentionBackof_UnexpectedExceptionOccurs() {
    // GIVEN
    stubFor(get(urlEqualTo("/resource"))
        .willReturn(aResponse().withStatus(500)));

    // WHEN - THEN
    assertThatThrownBy(() -> WebClient
        .create("http://localhost:9999")
        .get()
        .uri("resource")
        .retrieve()
        .bodyToMono(String.class)
        .retryBackoff(1, Duration.ofSeconds(1))
        .block()).
        isInstanceOf(IllegalStateException.class)
        .hasMessage("Retries exhausted: 1/1");
  }
}
