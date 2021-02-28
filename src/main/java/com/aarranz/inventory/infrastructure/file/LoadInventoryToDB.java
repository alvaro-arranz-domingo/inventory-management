package com.aarranz.inventory.infrastructure.file;

import com.aarranz.inventory.core.repositories.ArticlesRepository;
import com.aarranz.inventory.core.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;

@Component
public class LoadInventoryToDB {

  @Value(value = "${inventory.inventory-file:#{null}}")
  private String inventoryFile;

  @Value(value = "${inventory.products-file:#{null}}")
  private String productsFile;

  @Autowired
  private ProductRepository productsRepo;

  @Autowired
  private ArticlesRepository articlesRepo;

  @Autowired
  private LoadInventoryFromFile loadInventoryFromFile;

  public void loadToDB() throws IOException {

    if (inventoryFile == null
        || productsFile == null)
      return;

    var inventoryRes = new FileSystemResource(inventoryFile);
    var productsRes = new FileSystemResource(productsFile);

    var articles = loadInventoryFromFile.loadArticles(inventoryRes);
    var products = loadInventoryFromFile.loadProducts(productsRes);

    articlesRepo.saveAll(new HashSet<>(articles));
    productsRepo.saveAll(new HashSet<>(products));

    productsRepo.updateAllProductsStock();
  }
}
