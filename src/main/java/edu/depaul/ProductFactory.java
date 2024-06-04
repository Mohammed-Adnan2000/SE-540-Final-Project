package edu.depaul;

import java.util.Map;

public class ProductFactory {

    public Product createProduct(String type, Map<String, Object> properties) throws IllegalArgumentException {
        int id = extractInteger(properties, "id");
        String name = extractString(properties, "name");
        double price = extractDouble(properties, "price");

        switch (type.toLowerCase()) {
            case "electronics":
                String manufacturer = extractString(properties, "manufacturer");
                return new Electronics(id, name, price, manufacturer);
            case "clothing":
                String size = extractString(properties, "size");
                String material = extractString(properties, "material");
                return new Clothing(id, name, price, size, material);
            default:
                throw new IllegalArgumentException("Unknown product type: " + type);
        }
    }

    private int extractInteger(Map<String, Object> properties, String key) {
        if (!properties.containsKey(key) || !(properties.get(key) instanceof Integer)) {
            throw new IllegalArgumentException("Missing or invalid " + key);
        }
        return (int) properties.get(key);
    }

    private String extractString(Map<String, Object> properties, String key) {
        if (!properties.containsKey(key) || !(properties.get(key) instanceof String)) {
            throw new IllegalArgumentException("Missing or invalid " + key);
        }
        return (String) properties.get(key);
    }

    private double extractDouble(Map<String, Object> properties, String key) {
        if (!properties.containsKey(key) || !(properties.get(key) instanceof Double)) {
            throw new IllegalArgumentException("Missing or invalid " + key);
        }
        return (double) properties.get(key);
    }
}
