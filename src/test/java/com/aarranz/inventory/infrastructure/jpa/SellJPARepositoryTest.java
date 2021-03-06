package com.aarranz.inventory.infrastructure.jpa;

import com.aarranz.inventory.core.model.*;
import com.aarranz.inventory.infrastructure.jpa.springrepo.SellsCRUDRepoSpring;
import com.aarranz.inventory.mother.ArticleGroupMother;
import com.aarranz.inventory.mother.ProductMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SellJPARepositoryTest {

  @Autowired
  private SellsJPARepository sells;

  @Autowired
  private ProductJPARepository products;

  @Autowired
  private ArticlesJPARepository articles;

  @Autowired
  private SellsCRUDRepoSpring springRepo;

  @Test
  public void saveSell() {
    articles.save(Article.create(new ArticleId("1"), "articleTest1", 7));
    products.save(ProductMother.anyProductWithArticles(
        "product1",
        new Stock(3),
        ArticleGroupMother.anyGroupWith("1", 2)));
    var sell = Sell.create(new ProductId("product1"), 5);

    var createdSell = sells.save(sell);

    assertNotNull(createdSell);
    assertNotNull(createdSell.id());
    assertEquals("product1", createdSell.productId().value());
    assertEquals(5, createdSell.amount());
    assertTrue(springRepo.existsById(createdSell.id()));
  }

  @Test
  public void findSell() {
    articles.save(Article.create(new ArticleId("1"), "articleTest1", 7));
    products.save(ProductMother.anyProductWithArticles(
        "product1",
        new Stock(3),
        ArticleGroupMother.anyGroupWith("1", 2)));
    var sell = sells.save(Sell.create(new ProductId("product1"), 5));

    var sellFoundOp = sells.findById(sell.id());

    assertTrue(sellFoundOp.isPresent());
    assertEquals(sell.id(), sellFoundOp.get().id());
  }

  @Test
  public void findUnknownSell() {
    var sellFoundOp = sells.findById(5426L);

    assertFalse(sellFoundOp.isPresent());
  }

  @AfterEach
  public void cleanUp() {
    springRepo.deleteAll();
    products.clearAll();
  }
}
