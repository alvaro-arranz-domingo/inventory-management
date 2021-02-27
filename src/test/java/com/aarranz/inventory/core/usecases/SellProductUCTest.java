package com.aarranz.inventory.core.usecases;

import com.aarranz.inventory.core.model.ArticleId;
import com.aarranz.inventory.core.model.ProductId;
import com.aarranz.inventory.core.model.Sell;
import com.aarranz.inventory.core.model.exceptions.NotEnoughStockForArticleException;
import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.core.repositories.SellRepository;
import com.aarranz.inventory.infrastructure.transactional.DumbTransactionProvider;
import com.aarranz.inventory.infrastructure.transactional.SpringTransactionProvider;
import com.aarranz.inventory.mother.ArticleGroupMother;
import com.aarranz.inventory.mother.ProductMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SellProductUCTest {

  private ProductRepository products;

  private SellRepository sells;

  private SellProductUC sellProductUC;

  @BeforeEach
  public void setUp() {
    products = mock(ProductRepository.class);
    sells = mock(SellRepository.class);
    sellProductUC = new SellProductUC(products, sells, new DumbTransactionProvider());
  }

  @Test
  public void sell() {
    var product = ProductMother.anyProductWithArticles(
        "product1",
        ArticleGroupMother.anyGroupWith("1", 7, 2),
        ArticleGroupMother.anyGroupWith("3", 8, 1));
    when(products.findByName(product.name())).thenReturn(Optional.of(product));
    when(sells.save(any())).thenReturn(new Sell(1L, new ProductId("product1"), 1));

    var sell = sellProductUC.sell(1, new ProductId("product1"));

    var sellCaptor = ArgumentCaptor.forClass(Sell.class);
    verify(products).save(product);
    verify(sells).save(sellCaptor.capture());
    assertEquals(5, product.getStockOfArticleRequiredWithId(new ArticleId("1")).get());
    assertEquals(7, product.getStockOfArticleRequiredWithId(new ArticleId("3")).get());
    assertEquals("product1", sell.productId().value());
    assertEquals(1, sell.amount());
    assertEquals(1, sellCaptor.getValue().amount());
    assertEquals("product1", sellCaptor.getValue().productId().value());
    assertNotNull(sell.id());
  }

  @Test
  public void sellWithoutEnoughStock() {
    var product = ProductMother.anyProductWithArticles(
        "product1",
        ArticleGroupMother.anyGroupWith("1", 7, 2),
        ArticleGroupMother.anyGroupWith("3", 8, 1));
    when(products.findByName(product.name())).thenReturn(Optional.of(product));

    assertThrows(NotEnoughStockForArticleException.class, () -> sellProductUC.sell(4, new ProductId("product1")));
  }
}