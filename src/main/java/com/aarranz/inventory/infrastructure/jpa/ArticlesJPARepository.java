package com.aarranz.inventory.infrastructure.jpa;

import com.aarranz.inventory.core.model.Article;
import com.aarranz.inventory.core.model.ArticleId;
import com.aarranz.inventory.core.repositories.ArticlesRepository;
import com.aarranz.inventory.infrastructure.jpa.mappers.ArticleEntityMapper;
import com.aarranz.inventory.infrastructure.jpa.springrepo.ArticleCRUDRepoSpring;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class ArticlesJPARepository implements ArticlesRepository {

  private final ArticleCRUDRepoSpring repoSpring;
  private final ArticleEntityMapper mapper;

  public ArticlesJPARepository(ArticleCRUDRepoSpring repoSpring, ArticleEntityMapper mapper) {
    this.repoSpring = repoSpring;
    this.mapper = mapper;
  }

  @Override
  public void save(Article article) {
    repoSpring.save(mapper.toEntity(article));
  }

  @Override
  public void saveAll(Set<Article> articles) {
    repoSpring.saveAll(articles.stream().map(mapper::toEntity).collect(Collectors.toList()));
  }

  @Override
  public Optional<Article> findById(ArticleId id) {
    return repoSpring.findById(id.value()).map(mapper::toDom);
  }

  @Override
  public List<Article> findAllById(Iterable<ArticleId> ids) {
    var entities = repoSpring.findAllById(StreamSupport.stream(ids.spliterator(), false)
        .map(i -> i.value())
        .collect(Collectors.toList()));

    return StreamSupport.stream(entities.spliterator(), false).map(mapper::toDom).collect(Collectors.toList());
  }
}
