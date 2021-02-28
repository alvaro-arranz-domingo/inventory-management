package com.aarranz.inventory.infrastructure.jpa;

import com.aarranz.inventory.core.model.Article;
import com.aarranz.inventory.core.model.ArticleId;
import com.aarranz.inventory.core.model.ProductId;
import com.aarranz.inventory.core.model.Stock;
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
  private ArticlesJPARepository articles;

  @Autowired
  private ProductCRUDRepoSpring springRepo;

  @Test
  public void saveProduct() {
    articles.save(Article.create(new ArticleId("1"), "articleTest", 5));
    var product = ProductMother.anyProductWithArticles(
        "product1",
        new Stock(1),
        ArticleGroupMother.anyGroupWith("1", 5));

    products.save(product);

    assertEquals(1, springRepo.count());
  }

  @Test
  public void updateProduct() {
    articles.save(Article.create(new ArticleId("1"), "articleTest", 5));
    articles.save(Article.create(new ArticleId("2"), "articleTest", 7));
    products.save(ProductMother.anyProductWithArticles(
        "product1",
        new Stock(2),
        ArticleGroupMother.anyGroupWith("1", 2),
        ArticleGroupMother.anyGroupWith("2", 3)));
    var product = products.findByName(new ProductId("product1")).get();
    product.stock().removeAmount(1);

    products.save(product);
  }

  @Test
  public void saveAllProducts() {
    articles.save(Article.create(new ArticleId("1"), "articleTest1", 5));
    articles.save(Article.create(new ArticleId("2"), "articleTest2", 7));
    articles.save(Article.create(new ArticleId("3"), "articleTest3", 7));
    var article1 = ArticleGroupMother.anyGroupWith("1", 2);
    var article2 = ArticleGroupMother.anyGroupWith("2", 3);
    var article3 = ArticleGroupMother.anyGroupWith("3", 1);
    var product1 = ProductMother.anyProductWithArticles(
        "product1",
        new Stock(5),
        article1, article2);
    var product2 = ProductMother.anyProductWithArticles(
        "product2",
        new Stock(5),
        article2, article3);
    var allProducts = new HashSet<>(Arrays.asList(product1, product2));

    products.saveAll(allProducts);

    assertEquals(2, springRepo.count());
  }

  @Test
  public void findProductByName() {
    articles.save(Article.create(new ArticleId("1"), "articleTest1", 5));
    articles.save(Article.create(new ArticleId("2"), "articleTest2", 7));
    var article1 = ArticleGroupMother.anyGroupWith("1", 2);
    var article2 = ArticleGroupMother.anyGroupWith("2", 3);
    var product1 = ProductMother.anyProductWithArticles("product1", new Stock(5), article1, article2);
    products.save(product1);

    var product = products.findByName(new ProductId("product1"));

    assertNotNull(product);
    assertTrue(product.isPresent());
    assertEquals("product1", product.get().name().value());
  }

  @Test
  public void findProductByNameNonexistent() {
    var product = products.findByName(new ProductId("product2"));

    assertNotNull(product);
    assertFalse(product.isPresent());
  }

  @Test
  public void updateAllProductsStock() {
    articles.save(Article.create(new ArticleId("1"), "articleTest1", 5));
    articles.save(Article.create(new ArticleId("2"), "articleTest2", 7));
    articles.save(Article.create(new ArticleId("3"), "articleTest3", 10));
    var product1 = ProductMother.anyProductWithArticles(
        "product1",
        new Stock(0),
        ArticleGroupMother.anyGroupWith("1", 2),
        ArticleGroupMother.anyGroupWith("2", 3));
    var product2 = ProductMother.anyProductWithArticles(
        "product2",
        new Stock(0),
        ArticleGroupMother.anyGroupWith("2", 1),
        ArticleGroupMother.anyGroupWith("3", 3));
    products.save(product1);
    products.save(product2);

    products.updateAllProductsStock();

    assertEquals(2, products.findByName(new ProductId("product1")).get().stock().value());
    assertEquals(3, products.findByName(new ProductId("product2")).get().stock().value());
  }

  @Test
  public void updateProductStock() {
    articles.save(Article.create(new ArticleId("1"), "articleTest1", 5));
    articles.save(Article.create(new ArticleId("2"), "articleTest2", 7));
    articles.save(Article.create(new ArticleId("3"), "articleTest3", 10));
    var product1 = ProductMother.anyProductWithArticles(
        "product1",
        new Stock(0),
        ArticleGroupMother.anyGroupWith("1", 2),
        ArticleGroupMother.anyGroupWith("2", 3));
    var product2 = ProductMother.anyProductWithArticles(
        "product2",
        new Stock(0),
        ArticleGroupMother.anyGroupWith("2", 1),
        ArticleGroupMother.anyGroupWith("3", 3));
    products.save(product1);
    products.save(product2);

    products.updateProductStock(new ProductId("product2"));

    assertEquals(0, products.findByName(new ProductId("product1")).get().stock().value());
    assertEquals(3, products.findByName(new ProductId("product2")).get().stock().value());
  }

  @Test
  public void getProductsToBeUpdatedWhenSellProduct() {
    articles.save(Article.create(new ArticleId("1"), "articleTest1", 5));
    articles.save(Article.create(new ArticleId("2"), "articleTest2", 7));
    articles.save(Article.create(new ArticleId("3"), "articleTest3", 10));
    var product1 = ProductMother.anyProductWithArticles(
        "product1",
        new Stock(0),
        ArticleGroupMother.anyGroupWith("1", 2),
        ArticleGroupMother.anyGroupWith("2", 3));
    var product2 = ProductMother.anyProductWithArticles(
        "product2",
        new Stock(0),
        ArticleGroupMother.anyGroupWith("2", 1),
        ArticleGroupMother.anyGroupWith("3", 3));
    var product3 = ProductMother.anyProductWithArticles(
        "product3",
        new Stock(0),
        ArticleGroupMother.anyGroupWith("3", 3));
    products.save(product1);
    products.save(product2);
    products.save(product3);

    var results1 = products.getProductsToBeUpdatedWhenSellProduct(product1.name());
    var results2 = products.getProductsToBeUpdatedWhenSellProduct(product2.name());

    assertEquals(2, results1.size());
    assertTrue(results1.stream().anyMatch(id -> id.value().equals("product1")));
    assertTrue(results1.stream().anyMatch(id -> id.value().equals("product2")));
    assertEquals(3, results2.size());
    assertTrue(results2.stream().anyMatch(id -> id.value().equals("product1")));
    assertTrue(results2.stream().anyMatch(id -> id.value().equals("product2")));
    assertTrue(results2.stream().anyMatch(id -> id.value().equals("product3")));
  }

  @AfterEach
  public void cleanUp() {
    products.clearAll();
  }
}