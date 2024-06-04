package edu.depaul;

public class MockPaymentProcessor implements PaymentProcessor {
    public void processPayment(PaymentDetails details) {
        // Simulate processing payment
        System.out.println("Processing payment with the following details:");
        System.out.println("Card Number: " + details.getCardNumber());
        System.out.println("Expiry Date: " + details.getExpiryDate());
        System.out.println("CVV: " + details.getCvv());
        System.out.println("Amount: $" + details.getAmount());
        // Assume payment is always successful for mock
        System.out.println("Payment Processed Successfully!");
    }
}

