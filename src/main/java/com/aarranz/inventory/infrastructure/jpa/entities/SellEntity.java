package com.aarranz.inventory.infrastructure.jpa.entities;

import javax.persistence.*;

@Entity
@Table(name = "Sells")
public class SellEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  public String productId;

  public int amount;
}
