package Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * addProduct - a controller for adding products to the inventory.
 */
public class addProduct implements Initializable {

    @FXML
    private TextField searchPart;
    @FXML
    private TextField idTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField invTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private TableView productTableView;
    @FXML
    private TableColumn prodIDCol;
    @FXML
    private TableColumn prodNameCol;
    @FXML
    private TableColumn prodPriceCol;
    @FXML
    private TableColumn prodInvLevelCol;
    @FXML
    private TableView partsTblView;
    @FXML
    private TableColumn partIdCol;
    @FXML
    private TableColumn partNameCol;
    @FXML
    private TableColumn partInvLevelCol;
    @FXML
    private TableColumn partPriceCol;
    Stage stage;
    Parent scene;
    private Product currentProduct = new Product(1,"new",10,5,2, 20);

    /**
     * @param event - On press of Cancel button, return to main screen
     */
    @FXML
    void cancelButtonOnAction(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param actionEvent - save highlighted data from Parts list to associated part to product on Button click
     */
    public void addButtonOnAction(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTblView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "ERROR");
            alert.setContentText("Please select a part to associate with product");
            alert.show();
        } else if (!currentProduct.getAllAssociatedParts().contains(selectedPart)){
            currentProduct.addAssociatedPart(selectedPart);
            productTableView.setItems(currentProduct.getAllAssociatedParts());
        }
    }

    /**
     * @param actionEvent - On press of save button, store items in observable list Product
     * @throws IOException
     */
    public void saveButtonOnAction(ActionEvent actionEvent) throws IOException {
        try {
            currentProduct.setId(newProdId());
            currentProduct.setName(nameTxt.getText());
            currentProduct.setStock(Integer.parseInt(invTxt.getText()));
            currentProduct.setPrice(Double.parseDouble(priceTxt.getText()));
            currentProduct.setMax(Integer.parseInt(maxTxt.getText()));
            currentProduct.setMin(Integer.parseInt(minTxt.getText()));

            if (currentProduct.getMin() > currentProduct.getMax()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Min must be less than or equal to Max");
                alert.showAndWait();
                return;
            } else if (currentProduct.getStock() > currentProduct.getMax() || currentProduct.getStock() < currentProduct.getMin()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Inv must be within range between Min and Max");
                alert.showAndWait();
                return;
            } else if (currentProduct.getName().isEmpty() || currentProduct.getName().equals(" ")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Name cannot be blank");
                alert.showAndWait();
                return;
            }
                Inventory.addProduct(currentProduct);
                Inventory.updateProduct(currentProduct.getId() - 1, currentProduct);

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            try {
                scene = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid values for all fields");
            alert.showAndWait();
        }
    }

    /**
     * @param actionEvent - remove highlighted data from associated parts list on Button click
     */
    public void removePart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            currentProduct.deleteAssociatedPart((Part) (productTableView.getSelectionModel().getSelectedItem()));
        }
    }

    /**
     * a simple function to return new auto-generated product ID.
     *
     * @return newID starting at ID 1001.
     */
    public static int newProdId() {
        int newID = 3;
        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            newID++;
        }
        return newID;
    }

    /**
     * @param actionEvent - Search box to look up either part ID or name of part
     */
    public void searchPartOnAction(ActionEvent actionEvent) {
        String search = searchPart.getText();
        ObservableList<Part> searchPartList = Inventory.lookupPart(search);
        try {
            if (searchPartList.size() == 0) {
                int partID = Integer.parseInt(search);
                Part item = Inventory.lookupPart(partID);
                if (item != null) {
                    searchPartList.add(item);
                } else {
                    Alert nullField = new Alert(Alert.AlertType.WARNING);
                    nullField.setTitle("WARNING");
                    nullField.setContentText("Please enter a valid value");
                    nullField.showAndWait();
                }
            }
        } catch (NumberFormatException e) {
            Alert searchField = new Alert(Alert.AlertType.WARNING);
            searchField.setTitle("ERROR");
            searchField.setContentText("Part not found");
            searchField.showAndWait();
        } catch (IndexOutOfBoundsException e) {
            Alert numField = new Alert(Alert.AlertType.WARNING);
            numField.setTitle("ERROR");
            numField.setContentText("Please search for a valid part ID within range");
            numField.showAndWait();
        }
    }

    /**
     * Initialize two tables - one for parts and one for associated parts to product.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partsTblView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

      //  productTableView.setItems(Product.getAllAssociatedParts());
        prodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

}
