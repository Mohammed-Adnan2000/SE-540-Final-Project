package edu.depaul;

public class CartBuilder {
    private ShoppingCart cart;
    private ProductCatalog catalog;

    public CartBuilder(ProductCatalog catalog) {
        this.catalog = catalog;
        this.cart = ShoppingCart.getInstance(catalog);
    }

    public CartBuilder addItem(Product product) {
        cart.addItem(product);
        return this;
    }

    public CartBuilder setPaymentDetails(PaymentDetails paymentDetails) {
        cart.setPaymentDetails(paymentDetails);
        return this;
    }

    public ShoppingCart build() {
        return cart;
    }
}
