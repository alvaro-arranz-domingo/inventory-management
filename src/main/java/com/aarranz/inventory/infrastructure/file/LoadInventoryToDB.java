package com.aarranz.inventory.infrastructure.file;

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
  private LoadInventoryFromFile loadInventoryFromFile;

  public void loadToDB() throws IOException {

    if (inventoryFile == null
        || productsFile == null)
      return;

    var inventoryRes = new FileSystemResource(inventoryFile);
    var productsRes = new FileSystemResource(productsFile);

    var products = loadInventoryFromFile.load(inventoryRes, productsRes);

    productsRepo.saveAll(new HashSet<>(products));
  }
}
