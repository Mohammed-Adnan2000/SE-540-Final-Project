package edu.depaul;

public class Electronics extends Product {
    private String manufacturer;

    public Electronics(int id, String name, double price, String manufacturer) {
        super(id, name, price);
        this.manufacturer = manufacturer;
    }

    @Override
    public void displayProductDetails() {
        System.out.println("Product ID: " + getId() +
                           "\nName: " + getName() +
                           "\nPrice: $" + getPrice() +
                           "\nManufacturer: " + getManufacturer() +
                           "\nCategory: Electronics");
    }

    public String getManufacturer() {
        return manufacturer;
    }
}
