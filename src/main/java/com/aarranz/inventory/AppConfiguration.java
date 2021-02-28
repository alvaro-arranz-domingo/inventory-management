package com.aarranz.inventory;

import com.aarranz.inventory.core.repositories.ArticlesRepository;
import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.core.repositories.SellRepository;
import com.aarranz.inventory.core.transactional.TransactionProvider;
import com.aarranz.inventory.core.usecases.GetAllProductsUC;
import com.aarranz.inventory.core.usecases.SellProductUC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class AppConfiguration {

  @Bean
  public GetAllProductsUC getAllProductsUC(ProductRepository repository) {
    return new GetAllProductsUC(repository);
  }

  @Bean
  public SellProductUC sellProductUC(ProductRepository products,
                                     SellRepository sells,
                                     ArticlesRepository articles,
                                     TransactionProvider transactionProvider) {
    return new SellProductUC(products, articles, sells, transactionProvider);
  }

  @Bean
  public Docket apiDocket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.aarranz.inventory.infrastructure.rest"))
        .paths(PathSelectors.any())
        .build();
  }
}
