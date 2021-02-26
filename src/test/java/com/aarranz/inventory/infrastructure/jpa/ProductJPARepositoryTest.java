package com.aarranz.inventory.infrastructure.jpa;

import com.aarranz.inventory.infrastructure.jpa.springrepo.ProductCRUDRepoSpring;
import com.aarranz.inventory.mother.ArticleGroupMother;
import com.aarranz.inventory.mother.ProductMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductJPARepositoryTest {

  @Autowired
  private ProductJPARepository products;

  @Autowired
  private ProductCRUDRepoSpring springRepo;

  @Test
  public void saveProduct() {
    var articles = ArticleGroupMother.anyGroup();
    var product = ProductMother.anyProductWithArticles(articles);

    products.save(product);

    assertEquals(1, springRepo.count());
  }

  @Test
  public void updateProduct() {
    products.save(ProductMother.anyProductWithArticles(
        "product1",
        ArticleGroupMother.anyGroupWith("1", 5, 2),
        ArticleGroupMother.anyGroupWith("2", 7, 3)));
    var product = products.findByName("product1").get();
    product.removeAmount(1);

    products.save(product);
  }

  @Test
  public void saveAllProducts() {

    var article1 = ArticleGroupMother.anyGroupWith("1", 5, 2);
    var article2 = ArticleGroupMother.anyGroupWith("2", 7, 3);
    var article3 = ArticleGroupMother.anyGroupWith("3", 8, 1);
    var product1 = ProductMother.anyProductWithArticles("product1", article1, article2);
    var product2 = ProductMother.anyProductWithArticles("product2", article2, article3);
    var allProducts = new HashSet<>(Arrays.asList(product1, product2));

    products.saveAll(allProducts);

    assertEquals(2, springRepo.count());
  }

  @Test
  public void findProductByName() {
    var article1 = ArticleGroupMother.anyGroupWith("1", 5, 2);
    var article2 = ArticleGroupMother.anyGroupWith("2", 7, 3);
    var product1 = ProductMother.anyProductWithArticles("product1", article1, article2);
    products.save(product1);

    var product = products.findByName("product1");

    assertNotNull(product);
    assertTrue(product.isPresent());
    assertEquals("product1", product.get().name());
  }

  @Test
  public void findProductByNameNonexistent() {
    var product = products.findByName("product2");

    assertNotNull(product);
    assertFalse(product.isPresent());
  }

  @AfterEach
  public void cleanUp() {
    products.clearAll();
  }
}