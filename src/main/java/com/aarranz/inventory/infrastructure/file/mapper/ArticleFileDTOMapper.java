package com.aarranz.inventory.infrastructure.file.mapper;

import com.aarranz.inventory.core.model.Article;
import com.aarranz.inventory.core.model.ArticleId;
import com.aarranz.inventory.infrastructure.file.dto.ArticleFileDTO;
import org.springframework.stereotype.Component;

@Component
public class ArticleFileDTOMapper {

  public Article toDom(ArticleFileDTO dto) {
    return Article.create(new ArticleId(dto.art_id), dto.name, Integer.parseInt(dto.stock));
  }
}
