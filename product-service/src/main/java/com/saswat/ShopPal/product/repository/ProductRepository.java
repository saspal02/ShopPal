package com.saswat.ShopPal.product.repository;

import com.saswat.ShopPal.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {


}
