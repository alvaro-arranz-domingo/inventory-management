package com.aarranz.inventory.infrastructure.rest;

import com.aarranz.inventory.core.model.*;
import com.aarranz.inventory.core.repositories.ArticlesRepository;
import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.infrastructure.jpa.springrepo.SellsCRUDRepoSpring;
import com.aarranz.inventory.infrastructure.rest.dto.SellDTO;
import com.aarranz.inventory.mother.ArticleGroupMother;
import com.aarranz.inventory.mother.ArticleMother;
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
  private ArticlesRepository articles;

  @Autowired
  private SellsCRUDRepoSpring sells;

  @BeforeEach
  public void SetUp() {
    client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
  }

  @Test
  public void sell() {
    articles.save(Article.create(new ArticleId("1"), "articleName1", 11));
    articles.save(Article.create(new ArticleId("2"), "articleName2", 18));
    var product = ProductMother.anyProductWithArticles(
        "product1",
        new Stock(5),
        ArticleGroupMother.anyGroupWith("1",  2),
        ArticleGroupMother.anyGroupWith("2",  3));
    products.save(product);

    var sell = client
        .post()
          .uri("/sell")
          .bodyValue(createSellDTO("product1", 2))
        .exchange()
        .expectStatus()
          .isOk()
        .expectBody(SellDTO.class)
          .returnResult()
          .getResponseBody();

    assertEquals("product1", sell.productId);
    assertEquals(2, sell.amount);
    assertTrue(sells.existsById(sell.id));
    assertEquals(3, products.findByName(new ProductId("product1")).get().stock().value());
    assertEquals(7, articles.findById(new ArticleId("1")).get().stock());
    assertEquals(12, articles.findById(new ArticleId("2")).get().stock());
  }

  @Test
  public void sellUnknownProduct() {
    articles.save(Article.create(new ArticleId("1"), "articleTest1",15));
    products.save(ProductMother.anyProductWithArticles("product1", new Stock(5), ArticleGroupMother.anyGroupWith("1", 1)));

    client
        .post()
          .uri("/sell")
          .bodyValue(createSellDTO("unknown", 2))
        .exchange()
          .expectStatus()
          .isNotFound();
  }

  @Test
  public void sellInvalidAmount() {
    articles.save(Article.create(new ArticleId("1"), "articleName1", 11));
    articles.save(Article.create(new ArticleId("2"), "articleName1", 14));
    var product = ProductMother.anyProductWithArticles(
        "product1",
        ArticleGroupMother.anyGroupWith("1", 2),
        ArticleGroupMother.anyGroupWith("2", 3));
    products.save(product);

    client
        .post()
          .uri("/sell")
          .bodyValue(createSellDTO("product1", -2))
        .exchange()
          .expectStatus()
          .isBadRequest();
  }

  @Test
  public void sellMoreThanAvailable() {
    articles.save(Article.create(new ArticleId("1"), "articleName1", 11));
    articles.save(Article.create(new ArticleId("2"), "articleName1", 18));
    var product = ProductMother.anyProductWithArticles(
        "product1",
        new Stock(5),
        ArticleGroupMother.anyGroupWith("1", 2),
        ArticleGroupMother.anyGroupWith("2", 3));
    products.save(product);
    var sell = createSellDTO("product1", 8);

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
