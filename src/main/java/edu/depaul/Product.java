package edu.depaul;

import java.util.HashMap;
import java.util.Map;

public class Product {
    private int id;
    private String name;
    private double price;
    private Map<String, Object> attributes;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.attributes = new HashMap<>();
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void displayProductDetails() {
        System.out.println("ID: " + getId() + ", Name: " + getName() + ", Price: $" + getPrice());
        attributes.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public Map<String, Object> getAttributes() { return attributes; }
}
