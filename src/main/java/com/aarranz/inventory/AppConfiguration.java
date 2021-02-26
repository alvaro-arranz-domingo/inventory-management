package com.aarranz.inventory;

import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.core.repositories.SellRepository;
import com.aarranz.inventory.core.transactional.TransactionProvider;
import com.aarranz.inventory.core.usecases.GetAllProductsUC;
import com.aarranz.inventory.core.usecases.SellProductUC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  @Bean
  public GetAllProductsUC getAllProductsUC(ProductRepository repository) {
    return new GetAllProductsUC(repository);
  }

  @Bean
  public SellProductUC sellProductUC(ProductRepository products,
                                     SellRepository sells,
                                     TransactionProvider transactionProvider) {
    return new SellProductUC(products, sells, transactionProvider);
  }
}
