package edu.depaul;

public class OrderProcessor {
    private final ProductStorage productStorage;
    private final ShoppingCart cart;
    private final PaymentProcessor paymentProcessor;

    // Constructor injection for dependencies
    public OrderProcessor(ProductStorage productStorage, ShoppingCart cart, PaymentProcessor paymentProcessor) {
        this.productStorage = productStorage;
        this.cart = cart;
        this.paymentProcessor = paymentProcessor;
    }

    // Processes an order for a specific product by ID
    public void processProductOrder(int productId) {
        Product product = productStorage.getProduct(productId);
        if (product != null) {
            System.out.println("Processing order for: " + product.getName());
            // Here you could add product to cart or directly process payment
            cart.addItem(product);  // Assuming a method to add product directly to the cart
            processCartOrder();  // Now process the cart
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    // Processes all items in the shopping cart
    public void processCartOrder() {
        if (cart.isEmpty()) {
            System.out.println("Shopping Cart is empty.");
            return;
        }

        double totalAmount = cart.calculateTotal();
        PaymentDetails paymentDetails = cart.getPaymentDetails();  // Assuming this method fetches details

        paymentProcessor.processPayment(paymentDetails);
        cart.clear();

        System.out.println("Order has been processed and cart is cleared. Total paid: $" + totalAmount);
    }
}
