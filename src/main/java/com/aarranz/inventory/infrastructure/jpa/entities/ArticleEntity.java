package com.aarranz.inventory.infrastructure.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Articles")
public class ArticleEntity {

  @Id
  public String id;
  public String name;
  public int stock;

}
