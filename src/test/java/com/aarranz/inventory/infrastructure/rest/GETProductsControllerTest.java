package com.aarranz.inventory.infrastructure.rest;

import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.infrastructure.rest.dto.ProductDTO;
import com.aarranz.inventory.mother.ArticleGroupMother;
import com.aarranz.inventory.mother.ProductMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GETProductsControllerTest {

  @LocalServerPort
  private int port;

  private WebTestClient client;

  @Autowired
  private ProductRepository productsRepo;

  @BeforeEach
  public void SetUp() {
    client = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
  }

  @Test
  public void getProducts() {

    initDBWithTwoProducts();

    var products = client
        .get()
          .uri("/products")
        .exchange()
        .expectStatus()
          .isOk()
        .expectBody(ProductDTO[].class)
          .returnResult()
          .getResponseBody();

    assertNotNull(products);
    assertEquals(2, products.length);
  }

  @Test
  public void getProductsEmpty() {

    var products = client
        .get()
          .uri("/products")
        .exchange()
        .expectStatus()
          .isOk()
        .expectBody(ProductDTO[].class)
          .returnResult()
          .getResponseBody();

    assertNotNull(products);
    assertEquals(0, products.length);
  }

  private void initDBWithTwoProducts() {
    var article1 = ArticleGroupMother.anyGroupWith("1", 5, 2);
    var article2 = ArticleGroupMother.anyGroupWith("2", 7, 3);
    var article3 = ArticleGroupMother.anyGroupWith("3", 8, 1);

    var product1 = ProductMother.anyProductWithArticles("product1", article1, article2);
    var product2 = ProductMother.anyProductWithArticles("product2", article2, article3);
    var allProducts = new HashSet<>(Arrays.asList(product1, product2));

    productsRepo.saveAll(allProducts);
  }
}