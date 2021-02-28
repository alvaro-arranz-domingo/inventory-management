package com.aarranz.inventory.mother;

import com.aarranz.inventory.core.model.ArticleId;
import com.aarranz.inventory.core.model.ArticleRequirement;

public class ArticleGroupMother {

  public static ArticleRequirement anyGroup() {
    return new ArticleRequirement(new ArticleId("anyId"), 2);
  }

  public static ArticleRequirement anyGroupWith(String id, int amount) {
    return new ArticleRequirement(new ArticleId(id), amount);
  }
}
