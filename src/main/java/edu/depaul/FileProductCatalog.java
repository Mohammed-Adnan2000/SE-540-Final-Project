package edu.depaul;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class FileProductCatalog implements ProductStorage {
    private Map<Integer, Product> products = new HashMap<>();
    private final String productFile;

    public FileProductCatalog(String productFile) {
        this.productFile = productFile;
        try {
            loadProducts();
        } catch (IOException e) {
            System.out.println("Error initializing product catalog: " + e.getMessage());
        }
    }

    @Override
    public void loadProducts() throws IOException {
        String data = FileManager.readFile(productFile);
        JSONArray productsArray = new JSONArray(data);
        for (int i = 0; i < productsArray.length(); i++) {
            JSONObject obj = productsArray.getJSONObject(i);
            int id = obj.getInt("id");
            String name = obj.getString("name");
            double price = obj.getDouble("price");

            Product product = new Product(id, name, price);

            if (obj.has("attributes")) {
                JSONObject attributes = obj.getJSONObject("attributes");
                for (String key : attributes.keySet()) {
                    product.setAttribute(key, attributes.get(key));
                }
            }

            products.put(id, product);
        }
    }

    @Override
    public void saveProducts() throws IOException {
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
        FileManager.writeFile(productFile, productsArray.toString());
    }

    @Override
    public Product getProduct(int productId) {
        return products.get(productId);
    }

    @Override
    public Map<Integer, Product> getAllProducts() {
        return new HashMap<>(products);
    }
}
