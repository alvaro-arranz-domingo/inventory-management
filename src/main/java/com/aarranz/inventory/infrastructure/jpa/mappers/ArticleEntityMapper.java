package com.aarranz.inventory.infrastructure.jpa.mappers;

import com.aarranz.inventory.core.model.Article;
import com.aarranz.inventory.core.model.ArticleId;
import com.aarranz.inventory.infrastructure.jpa.entities.ArticleEntity;
import org.springframework.stereotype.Component;

@Component
public class ArticleEntityMapper {

  public Article toDom(ArticleEntity entity) {
    return new Article(new ArticleId(entity.id), entity.name, entity.stock);
  }

  public ArticleEntity toEntity(Article article) {
    var entity = new ArticleEntity();
    entity.id = article.id().value();
    entity.name = article.name();
    entity.stock = article.stock();
    return entity;
  }

}
