package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        ObservableList<Part> namePart = FXCollections.observableArrayList();
        ObservableList<Part> returnAllParts = Inventory.getAllParts();

        for (Part item : returnAllParts) {
            if (item.getId() == partId) {
                return item;
            }
        }
        return allParts.get(partId);
    }
    public static Product lookupProduct(int productId) {
        ObservableList<Product> nameProduct = FXCollections.observableArrayList();
        ObservableList<Product> returnProducts = Inventory.getAllProducts();

        for (Product item : returnProducts) {
            if (item.getId() == productId) {
                return item;
            }
        }
        return allProducts.get(productId);
    }

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

    public static ObservableList<Product> lookupProduct(String partialProduct) {
        ObservableList<Product> nameProduct = FXCollections.observableArrayList();

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for (Product product : allProducts)
            if (product.getName().contains(partialProduct)) {
                nameProduct.add(product);
            }
        return nameProduct;
    }
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


}
