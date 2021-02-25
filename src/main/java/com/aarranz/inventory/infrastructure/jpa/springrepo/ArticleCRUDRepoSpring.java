package com.aarranz.inventory.infrastructure.jpa.springrepo;

import com.aarranz.inventory.infrastructure.jpa.entities.ArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCRUDRepoSpring extends CrudRepository<ArticleEntity, String> {
}
