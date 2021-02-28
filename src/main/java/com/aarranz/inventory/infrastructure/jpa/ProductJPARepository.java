package com.aarranz.inventory.infrastructure.jpa;

import com.aarranz.inventory.core.model.Product;
import com.aarranz.inventory.core.model.ProductId;
import com.aarranz.inventory.core.repositories.ProductRepository;
import com.aarranz.inventory.infrastructure.jpa.mappers.ProductEntityMapper;
import com.aarranz.inventory.infrastructure.jpa.springrepo.ArticleCRUDRepoSpring;
import com.aarranz.inventory.infrastructure.jpa.springrepo.ProductCRUDRepoSpring;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class ProductJPARepository implements ProductRepository {

  private final ProductEntityMapper mapper;
  private final ProductCRUDRepoSpring productCRUDRepo;
  private final ArticleCRUDRepoSpring articleCRUDRepo;
  private final JdbcTemplate dbTemplate;

  public ProductJPARepository(ProductEntityMapper mapper,
                              ProductCRUDRepoSpring productCRUDRepo,
                              ArticleCRUDRepoSpring articleCRUDRepo,
                              JdbcTemplate dbTemplate) {
    this.mapper = mapper;
    this.productCRUDRepo = productCRUDRepo;
    this.articleCRUDRepo = articleCRUDRepo;
    this.dbTemplate = dbTemplate;
  }

  @Override
  public Optional<Product> findByName(ProductId name) {
    return productCRUDRepo.findById(name.value()).map(mapper::toDom);
  }

  @Override
  public List<Product> getAllProducts() {
    var stream = StreamSupport.stream(productCRUDRepo.findAll().spliterator(), false);
    return stream.map(mapper::toDom).collect(Collectors.toList());
  }

  @Override
  public void saveAll(Set<Product> products) {
    productCRUDRepo.saveAll(products.stream().map(mapper::toEntity).collect(Collectors.toList()));
  }

  @Override
  public void save(Product product) {
    var entity = mapper.toEntity(product);
    productCRUDRepo.save(entity);
  }

  @Override
  public void clearAll() {
    productCRUDRepo.deleteAll();
    articleCRUDRepo.deleteAll();
  }

  @Override
  public void updateAllProductsStock() {
    dbTemplate.execute("update products\n" +
        "left join\n" +
        "(\n" +
        "    select p.name, min(floor(a.stock / pa.amount)) productStock\n" +
        "    from products p\n" +
        "    left join product_articles pa on p.name = pa.product_name\n" +
        "    left join articles a on pa.article_id = a.id\n" +
        "    group by p.name\n" +
        ") A on A.name = products.name\n" +
        "set stock = A.productStock");
  }

  @Override
  public void updateProductStock(ProductId id) {
    dbTemplate.execute("update products\n" +
        "    left join\n" +
        "    (\n" +
        "        select p.name, min(floor(a.stock / pa.amount)) productStock\n" +
        "        from products p\n" +
        "                 left join product_articles pa on p.name = pa.product_name\n" +
        "                 left join articles a on pa.article_id = a.id\n" +
        "        group by p.name\n" +
        "    ) A on A.name = products.name\n" +
        "set stock = A.productStock\n" +
        "where products.name = '" + id.value() + "'");
  }

  @Override
  public List<ProductId> getProductsToBeUpdatedWhenSellProduct(ProductId id) {
    var query = "select pachg.product_name\n" +
        "from products p\n" +
        "left join product_articles pa on p.name = pa.product_name\n" +
        "left join product_articles pachg on pachg.article_id = pa.article_id\n" +
        "where p.name = '" + id.value() + "'\n" +
        "group by pachg.product_name";
    return dbTemplate.queryForList(query, String.class)
        .stream()
        .map(s -> new ProductId(s))
        .collect(Collectors.toList());
  }
}
