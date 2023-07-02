package model;
/**
 * Supplied class Part.java
 */

/**
 *
 * @author Jin Lee
 */
public abstract class Part {
    private int id, stock, min, max;
    private double price;
    private String name;

    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.price = price;
        this.name = name;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id sets the id
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }
    /**
     * @param stock sets the inventory
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }
    /**
     * @param min sets the min value
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }
    /**
     * @param max sets the max value
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    /**
     * @param price sets the price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name sets the name
     */
    public void setName(String name) {
        this.name = name;
    }
}