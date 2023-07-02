package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class stores values of Parts and Products in observable lists.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * adds a Part to the allParts observable list
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * adds a product to the allProducts observable list
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * lookup a partID from a list of all parts to return the Part if found
     * @param partId - takes input partId as lookup value
     * @return Part item
     */
    public static Part lookupPart(int partId) {
        ObservableList<Part> returnAllParts = Inventory.getAllParts();

        for (Part item : returnAllParts) {
            if (item.getId() == partId) {
                return item;
            }
        }
        return allParts.get(partId);
    }

    /**
     * lookup a partID from a list of all products to return the Product if found
     * @param productId - takes input partId as lookup value
     * @return Product item
     */
    public static Product lookupProduct(int productId) {
        ObservableList<Product> returnProducts = Inventory.getAllProducts();

        for (Product item : returnProducts) {
            if (item.getId() == productId) {
                return item;
            }
        }
        return allProducts.get(productId);
    }

    /**
     * creates an overloaded lookupPart method to add a string value of Part's name values. Iterates search values over a loop and returns the part if found
     * @param partialPart - takes string values of Part's name as input value
     * @return namePart - name of Part if found
     */
    public static ObservableList<Part> lookupPart(String partialPart) {
        ObservableList<Part> namePart = FXCollections.observableArrayList();
        ObservableList<Part> allPart = Inventory.getAllParts();

        for (Part part : allPart) {
            if (part.getName().contains(partialPart)){
                namePart.add(part);
            }
        }
        return namePart;
    }
    /**
     * creates an overloaded lookupProduct method to add a string value of Product's name values. Iterates search values over a loop and returns the Product if found
     * @param partialProduct - takes string values of Product's name as input value
     * @return nameProduct - name of Part if found
     */
    public static ObservableList<Product> lookupProduct(String partialProduct) {
        ObservableList<Product> nameProduct = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product product : allProducts)
            if (product.getName().contains(partialProduct)) {
                nameProduct.add(product);
            }
        return nameProduct;
    }

    /**
     * updates part in the observable list
     * @param index - takes index value of part
     * @param selectedPart - takes Part value
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * updates product in the observable list
     * @param index - takes index value of product
     * @param selectedProduct - takes Product value
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * deletes part in the observable list
     * @param selectedPart - takes Part value
     * @return boolean value for deleting Part
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * deletes product in the observable list
     * @param selectedProduct - takes Product value
     * @return boolean value for deleting product
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * returns all parts from allParts observable list
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * returns all products from allProducts observable list
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
