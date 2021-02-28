package com.aarranz.inventory.core.repositories;

import com.aarranz.inventory.core.model.Article;
import com.aarranz.inventory.core.model.ArticleId;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ArticlesRepository {

  void save(Article article);

  void saveAll(Set<Article> articles);

  Optional<Article> findById(ArticleId id);
  
  List<Article> findAllById(Iterable<ArticleId> ids);
}
