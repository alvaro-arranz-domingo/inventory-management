package com.aarranz.inventory.infrastructure.rest;

import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.infrastructure.jpa.springrepo.SellsCRUDRepoSpring;
import com.aarranz.inventory.infrastructure.rest.dto.SellDTO;
import com.aarranz.inventory.mother.ArticleGroupMother;
import com.aarranz.inventory.mother.ProductMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class POSTSellControllerTest {

  @LocalServerPort
  private int port;

  private WebTestClient client;

  @Autowired
  private ProductRepository products;

  @Autowired
  private SellsCRUDRepoSpring sells;

  @BeforeEach
  public void SetUp() {
    client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
  }

  @Test
  public void sell() {
    var product = ProductMother.anyProductWithArticles(
        "product1",
        ArticleGroupMother.anyGroupWith("1", 5, 2),
        ArticleGroupMother.anyGroupWith("2", 8, 3));
    products.save(product);
    var sellRequest = createSellDTO("product1", 2);

    var sell = client
        .post()
          .uri("/sell")
          .bodyValue(sellRequest)
        .exchange()
        .expectStatus()
          .isOk()
        .expectBody(SellDTO.class)
          .returnResult()
          .getResponseBody();

    assertEquals("product1", sell.productId);
    assertEquals(2, sell.amount);
    assertTrue(sells.existsById(sell.id));
  }

  @Test
  public void sellUnknownProduct() {
    var sell = createSellDTO("unknown", 2);

    client
        .post()
          .uri("/sell")
          .bodyValue(sell)
        .exchange()
          .expectStatus()
          .isNotFound();
  }

  @Test
  public void sellInvalidAmount() {
    var product = ProductMother.anyProductWithArticles(
        "product1",
        ArticleGroupMother.anyGroupWith("1", 5, 2),
        ArticleGroupMother.anyGroupWith("2", 8, 3));
    products.save(product);
    var sell = createSellDTO("product1", -2);

    client
        .post()
          .uri("/sell")
          .bodyValue(sell)
        .exchange()
          .expectStatus()
          .isBadRequest();
  }

  @Test
  public void sellMoreThanAvailable() {
    var product = ProductMother.anyProductWithArticles(
        "product1",
        ArticleGroupMother.anyGroupWith("1", 5, 2),
        ArticleGroupMother.anyGroupWith("2", 8, 3));
    products.save(product);
    var sell = createSellDTO("product1", 5);

    client
        .post()
          .uri("/sell")
          .bodyValue(sell)
        .exchange()
        .expectStatus()
          .isBadRequest();
  }

  @AfterEach
  public void cleanUp() {
    sells.deleteAll();
    products.clearAll();
  }

  private SellDTO createSellDTO(String product, int amount) {
    var sellRequest = new SellDTO();
    sellRequest.productId = product;
    sellRequest.amount = amount;
    return sellRequest;
  }

}
