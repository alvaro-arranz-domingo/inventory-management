package com.aarranz.inventory;

import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.core.usecases.GetAllProductsUC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  @Bean
  public GetAllProductsUC getAllProductsUC(ProductRepository repository) {
    return new GetAllProductsUC(repository);
  }
}
