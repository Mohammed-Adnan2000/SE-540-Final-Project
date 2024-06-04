package edu.depaul;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProductCatalog {
    private Map<Integer, Product> products = new HashMap<>();
    private static final String PRODUCT_FILE = "products.json";

    public ProductCatalog() {
        loadProducts();
    }

    private void loadProducts() {
        try {
            String data = FileManager.readFile(PRODUCT_FILE);
            JSONArray productsArray = new JSONArray(data);
            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject obj = productsArray.getJSONObject(i);
                int id = obj.getInt("id");
                String name = obj.getString("name");
                double price = obj.getDouble("price");

                Product product = new Product(id, name, price);

                // Check if the attributes object exists before accessing it
                if (obj.has("attributes")) {
                    JSONObject attributes = obj.getJSONObject("attributes");
                    for (String key : attributes.keySet()) {
                        product.setAttribute(key, attributes.get(key));
                    }
                }

                products.put(id, product);
            }
        } catch (IOException e) {
            System.out.println("Error loading product catalog: " + e.getMessage());
        }
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
        saveProducts();
    }

    private void saveProducts() {
        JSONArray productsArray = new JSONArray();
        for (Product product : products.values()) {
            JSONObject obj = new JSONObject();
            obj.put("id", product.getId());
            obj.put("name", product.getName());
            obj.put("price", product.getPrice());
            JSONObject attrObj = new JSONObject();
            product.getAttributes().forEach(attrObj::put);
            obj.put("attributes", attrObj);
            productsArray.put(obj);
        }
        try {
            FileManager.writeFile(PRODUCT_FILE, productsArray.toString());
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    public Product getProduct(int productId) {
        return products.get(productId);
    }

    public Map<Integer, Product> getAllProducts() {
        return new HashMap<>(products);
    }
}
