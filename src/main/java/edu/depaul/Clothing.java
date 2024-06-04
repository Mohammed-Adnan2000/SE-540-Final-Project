package edu.depaul;

public class Clothing extends Product {
    private String size;
    private String material;

    public Clothing(int id, String name, double price, String size, String material) {
        super(id, name, price);
        this.size = size;
        this.material = material;
    }

    @Override
    public void displayProductDetails() {
        System.out.println("Product ID: " + getId() + "\nName: " + getName() + "\nPrice: $" + getPrice() +
                           "\nSize: " + size + "\nMaterial: " + material + "\nCategory: Clothing");
    }

    // Getters for clothing specific properties
    public String getSize() {
        return size;
    }

    public String getMaterial() {
        return material;
    }
}
