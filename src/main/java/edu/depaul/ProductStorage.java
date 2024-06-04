package edu.depaul;

import java.util.Map;
import java.io.IOException;

public interface ProductStorage {
    void loadProducts() throws IOException;
    void saveProducts() throws IOException;
    Product getProduct(int productId);
    Map<Integer, Product> getAllProducts();
}
