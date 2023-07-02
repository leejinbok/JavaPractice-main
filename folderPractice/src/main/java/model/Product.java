package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Part;

public class Product {

    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id, stock, min, max;
    private String name;
    private double price;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.price = price;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    public static ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    public static boolean deleteAssociatedPart(Part associatedPart) {
        associatedParts.remove(associatedPart);
        return true;
    }
}
