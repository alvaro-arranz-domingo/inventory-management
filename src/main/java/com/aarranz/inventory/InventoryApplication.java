package com.aarranz.inventory;

import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.infrastructure.file.LoadInventoryToDB;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@SpringBootApplication
@EnableTransactionManagement
public class InventoryApplication {

  public static void main(String[] args) {
    SpringApplication.run(InventoryApplication.class, args);
  }

  @Bean
  public CommandLineRunner initInventory(ProductRepository products,
                                         LoadInventoryToDB loadInventoryToDB) throws IOException {
    return (args) -> {
      if (products.getAllProducts().isEmpty()) {
        loadInventoryToDB.loadToDB();
      }
    };
  }
}
