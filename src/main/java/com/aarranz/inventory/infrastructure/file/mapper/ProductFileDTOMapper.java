package com.aarranz.inventory.infrastructure.file.mapper;

import com.aarranz.inventory.core.model.Article;
import com.aarranz.inventory.core.model.ArticleRequirement;
import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.infrastructure.file.dto.ArticleFileDTO;
import com.aarranz.inventory.infrastructure.file.dto.ProductFileDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProductFileDTOMapper {

  public List<Product> toDom(List<ProductFileDTO> productDtos, List<ArticleFileDTO> articlesDtos) {

    var articles = articlesDtos.stream()
        .map(this::toDom)
        .collect(Collectors.toMap(a -> a.id(), a -> a));

    return productDtos.stream()
        .map(pdto -> toDom(pdto, articles))
        .collect(Collectors.toList());
  }

  private Article toDom(ArticleFileDTO dto) {
    return Article.create(dto.art_id, dto.name, Integer.parseInt(dto.stock));
  }

  private Product toDom(ProductFileDTO dto, Map<String, Article> articles) {

    var articleRequirements = dto.contain_articles.stream()
        .map(adto -> new ArticleRequirement(articles.get(adto.art_id), Integer.parseInt(adto.amount_of)))
        .collect(Collectors.toSet());
    return new Product(dto.name, articleRequirements);
  }
}
