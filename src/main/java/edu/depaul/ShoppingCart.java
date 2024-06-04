package edu.depaul;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class ShoppingCart {
    private static volatile ShoppingCart instance;
    private List<CartItem> items;
    private PaymentDetails paymentDetails;
    private static final String CART_FILE = "Cart.json";
    private ProductCatalog catalog;

    private ShoppingCart(ProductCatalog catalog) {
        this.catalog = catalog;
        items = new ArrayList<>();
        initializeCartFile();
        loadCart();
    }

    private void initializeCartFile() {
        try {
            if (!FileManager.fileExists(CART_FILE)) {
                FileManager.writeFile(CART_FILE, "[]");
            }
        } catch (IOException e) {
            System.out.println("Error initializing cart file: " + e.getMessage());
        }
    }

    private void loadCart() {
        try {
            String data = FileManager.readFile(CART_FILE);
            JSONArray cartArray = new JSONArray(data);
            for (int i = 0; i < cartArray.length(); i++) {
                JSONObject obj = cartArray.getJSONObject(i);
                Product product = catalog.getProduct(obj.getInt("productId"));
                int quantity = obj.getInt("quantity");
                items.add(new CartItem(product, quantity));
            }
        } catch (Exception e) {
            System.out.println("Error loading cart: " + e.getMessage());
        }
    }

    public static ShoppingCart getInstance(ProductCatalog catalog) {
        if (instance == null) {
            synchronized (ShoppingCart.class) {
                if (instance == null) {
                    instance = new ShoppingCart(catalog);
                }
            }
        }
        return instance;
    }

    public void addItem(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.incrementQuantity();
                saveCart();
                return;
            }
        }
        items.add(new CartItem(product, 1));
        saveCart();
    }

    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().getId() == product.getId());
        saveCart();
    }

    private void saveCart() {
        JSONArray cartArray = new JSONArray();
        for (CartItem item : items) {
            JSONObject obj = new JSONObject();
            obj.put("productId", item.getProduct().getId());
            obj.put("quantity", item.getQuantity());
            cartArray.put(obj);
        }
        try {
            FileManager.writeFile(CART_FILE, cartArray.toString());
        } catch (IOException e) {
            System.out.println("Error saving cart: " + e.getMessage());
        }
    }

    public double calculateTotal() {
        return items.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public PaymentDetails getPaymentDetails() {
        return this.paymentDetails;
    }

    public void clear() {
        items.clear();
        saveCart();
    }

    public void checkout() {
        if (isEmpty()) {
            System.out.println("Checkout attempt failed: Shopping cart is empty.");
            return;
        }
        double total = calculateTotal();
        System.out.println("Checkout successful. Total amount: $" + total);
        clear(); // Clears the cart after checkout
    }
}
