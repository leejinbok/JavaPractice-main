package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Part;

/**
 * Supplied class Product.java
 *
 * @author Jin Lee
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id, stock, min, max;
    private String name;
    private double price;

    /**
     * set the default constructor for product
     * @param id - set id
     * @param name - set name
     * @param price - set price
     * @param stock - set stock
     * @param min - set min
     * @param max - set max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.price = price;
    }

    /**
     * getter for ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * setter for ID
     * @param id - sets the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter for Inventory level
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * setter for Inventory level
     * @param stock - sets the stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * getter for Min value
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * setter for Min value
     * @param min - sets Min value
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * getter for Max value
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * setter for Max value
     * @param max - sets max value
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * getter for price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * setter for price
     * @param price - sets price value
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * getter for name value
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for name value
     * @param name - sets the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * adds part to associatedParts observableList
     * @param part - adds part to associatedParts observable list
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * returns all part values stored in observableList associatedParts
     * @return
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * deletes part from associatedParts observableList
     * @param associatedPart - removes Part associatedPart from observableList
     * @return true
     */
    public boolean deleteAssociatedPart(Part associatedPart) {
        associatedParts.remove(associatedPart);
        return true;
    }
}
