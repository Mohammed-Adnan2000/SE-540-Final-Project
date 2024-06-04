package edu.depaul;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PaymentDetails {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private double amount;

    public PaymentDetails(String cardNumber, String expiryDate, String cvv, double amount) {
        this.cardNumber = validateAndFormatCardNumber(cardNumber);
        this.expiryDate = validateExpiryDate(expiryDate);
        this.cvv = validateCVV(cvv, cardNumber);
        this.amount = amount;
    }

    private String validateAndFormatCardNumber(String cardNumber) {
        String sanitizedCardNumber = cardNumber.replaceAll("[\\s-]", "");
        if (!sanitizedCardNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Card number must be numerical.");
        }

        int length = sanitizedCardNumber.length();
        if (length != 16 && length != 15 && length != 14 && length != 19) {
            throw new IllegalArgumentException("Invalid card number length.");
        }

        return sanitizedCardNumber;
    }

    private String validateExpiryDate(String expiryDate) {
        if (!expiryDate.matches("^(0[1-9]|1[0-2])\\/\\d{2}$")) {
            throw new IllegalArgumentException("Expiry date must be in MM/YY format.");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth expiry = YearMonth.parse(expiryDate, formatter);
            YearMonth current = YearMonth.now();
            if (expiry.isBefore(current)) {
                throw new IllegalArgumentException("Expiry date must be in the future.");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid expiry date format.");
        }

        return expiryDate;
    }

    private String validateCVV(String cvv, String cardNumber) {
        int length = cardNumber.length();
        if ((length == 16 || length == 14 || length == 19) && !cvv.matches("^\\d{3}$")) {
            throw new IllegalArgumentException("CVV must be 3 digits.");
        } else if (length == 15 && !cvv.matches("^\\d{4}$")) {
            throw new IllegalArgumentException("CVV must be 4 digits for American Express.");
        } else if (!cvv.matches("\\d+")) {
            throw new IllegalArgumentException("CVV must be numerical.");
        }
        return cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public double getAmount() {
        return amount;
    }
}
