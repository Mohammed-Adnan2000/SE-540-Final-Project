package edu.depaul;

import java.util.Scanner;
import java.util.function.BiConsumer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserAuth userAuth = new UserAuth();
        ProductCatalog catalog = new ProductCatalog();
        initializeCatalog(catalog);

        while (true) {
            System.out.println("Please choose an option: \n1. Login \n2. Sign Up \n3. Exit");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": // Login
                    if (performLogin(userAuth, scanner)) {
                        performShopping(scanner, catalog);
                    }
                    break;
                case "2": // Sign Up
                    performSignUp(userAuth, scanner);
                    break;
                case "3": // Exit
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option, please choose 1, 2, or 3.");
            }
        }
    }

    private static boolean performLogin(UserAuth userAuth, Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        if (userAuth.login(username, password)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Login failed. Please check your username and password.");
            return false;
        }
    }

    private static void performSignUp(UserAuth userAuth, Scanner scanner) {
        System.out.println("Enter a new username:");
        String username = scanner.nextLine();
        System.out.println("Enter a new password:");
        String password = scanner.nextLine();

        User newUser = new User(username, password);
        if (userAuth.register(newUser)) {
            System.out.println("Registration successful. You can now log in.");
        } else {
            System.out.println("Registration failed. User may already exist.");
        }
    }

    private static void performShopping(Scanner scanner, ProductCatalog catalog) {
        ShoppingCart cart = ShoppingCart.getInstance(catalog);
        boolean addingProducts = true;
        while (addingProducts) {
            System.out.println("Current Catalog:");
            catalog.getAllProducts().forEach(new BiConsumer<Integer, Product>() {
                @Override
                public void accept(Integer id, Product product) {
                    System.out.println("ID: " + id + ", Name: " + product.getName() + ", Price: $" + product.getPrice());
                }
            });
            System.out.println("Enter product ID to add to the cart, or type 'done' to proceed to checkout:");
            String input = scanner.nextLine();
            if ("done".equalsIgnoreCase(input.trim())) {
                if (!cart.isEmpty()) {
                    checkout(scanner, cart);
                } else {
                    System.out.println("Your shopping cart is empty.");
                }
                addingProducts = false;
            } else {
                try {
                    int productId = Integer.parseInt(input);
                    Product product = catalog.getProduct(productId);
                    if (product != null) {
                        cart.addItem(product);
                        System.out.println(product.getName() + " added to the cart.");
                    } else {
                        System.out.println("Product not found.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid product ID.");
                }
            }
        }
    }

    private static void checkout(Scanner scanner, ShoppingCart cart) {
        boolean validDetails = false;
        while (!validDetails) {
            try {
                System.out.println("Enter your payment details:");
                System.out.println("Card Number:");
                String cardNumber = scanner.nextLine();
                System.out.println("Expiry Date (MM/YY):");
                String expiryDate = scanner.nextLine();
                System.out.println("CVV:");
                String cvv = scanner.nextLine();

                double total = cart.calculateTotal();
                PaymentDetails paymentDetails = new PaymentDetails(cardNumber, expiryDate, cvv, total);
                MockPaymentProcessor paymentProcessor = new MockPaymentProcessor();
                paymentProcessor.processPayment(paymentDetails);

                System.out.println("Total amount: $" + total);
                System.out.println("Type 'yes' to confirm checkout:");
                String confirm = scanner.nextLine();
                if ("yes".equalsIgnoreCase(confirm.trim())) {
                    cart.checkout();
                    System.out.println("Checkout successful. Thank you for shopping with us!");
                    cart.clear();
                    validDetails = true;
                } else {
                    System.out.println("Checkout cancelled.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid card details: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    private static void initializeCatalog(ProductCatalog catalog) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("products.json")));
            JSONArray productsArray = new JSONArray(content);
            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject productObj = productsArray.getJSONObject(i);
                String type = productObj.getString("type");
                int id = productObj.getInt("id");
                String name = productObj.getString("name");
                double price = productObj.getDouble("price");

                if (type.equalsIgnoreCase("electronics")) {
                    String manufacturer = productObj.getString("manufacturer");
                    catalog.addProduct(new Electronics(id, name, price, manufacturer));
                } else if (type.equalsIgnoreCase("clothing")) {
                    String size = productObj.getString("size");
                    String material = productObj.getString("material");
                    catalog.addProduct(new Clothing(id, name, price, size, material));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading product catalog: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Missing or invalid price");
        }
    }
}
