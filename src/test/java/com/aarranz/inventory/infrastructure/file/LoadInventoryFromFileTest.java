package com.aarranz.inventory.infrastructure.file;

import com.aarranz.inventory.core.model.ProductId;
import com.aarranz.inventory.core.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class LoadInventoryFromFileTest {

  @Autowired
  public LoadInventoryFromFile loadInventoryFromFile;

  @Test
  public void loadInventoryFromFile() throws IOException {

    var inventoryResource = new ClassPathResource("inventory.json");
    var productsResource = new ClassPathResource("products.json");

    var products = loadInventoryFromFile.loadProducts(productsResource);
    var articles = loadInventoryFromFile.loadArticles(inventoryResource);

    assertNotNull(products);
    assertEquals(2, products.size());
    assertNotNull(articles);
    assertEquals(4, articles.size());
  }
}