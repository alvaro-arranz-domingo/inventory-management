package com.aarranz.inventory.infrastructure.file.mapper;

import com.aarranz.inventory.core.model.*;
import com.aarranz.inventory.infrastructure.file.dto.ArticleFileDTO;
import com.aarranz.inventory.infrastructure.file.dto.ProductFileDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProductFileDTOMapper {

  public Product toDom(ProductFileDTO dto) {

    var articleRequirements = dto.contain_articles.stream()
        .map(adto -> new ArticleRequirement(new ArticleId(adto.art_id), Integer.parseInt(adto.amount_of)))
        .collect(Collectors.toSet());
    return new Product(new ProductId(dto.name), new Stock(0), articleRequirements);
  }
}
