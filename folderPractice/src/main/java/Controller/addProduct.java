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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
    private  TextField nameTxt;
    @FXML
    private  TextField invTxt;
    @FXML
    private  TextField priceTxt;
    @FXML
    private  TextField maxTxt;
    @FXML
    private  TextField minTxt;
    @FXML
    private  TableView productTableView;
    @FXML
    private  TableColumn prodIDCol;
    @FXML
    private  TableColumn prodNameCol;
    @FXML
    private  TableColumn prodPriceCol;
    @FXML
    private  TableColumn prodInvLevelCol;
    @FXML
    private  TableView partsTblView;
    @FXML
    private  TableColumn partIdCol;
    @FXML
    private  TableColumn partNameCol;
    @FXML
    private  TableColumn partInvLevelCol;
    @FXML
    private  TableColumn partPriceCol;
    Stage stage;
    Parent scene;

    /**
     * On press of Cancel button, return to main screen
     *
     * @param event
     */
    @FXML
    void cancelButtonOnAction(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        try {
            scene = FXMLLoader.load(getClass().getResource("mainForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * save highlighted data from Parts list to associated part to product on Button click
     * @param actionEvent
     */
    public void addButtonOnAction(ActionEvent actionEvent){
        Product.addAssociatedPart((Part)(partsTblView.getSelectionModel().getSelectedItem()));
    }
    /**
     * On press of save button, store items in observable list Product
     * @param actionEvent
     * @throws IOException
     */
    public void saveButtonOnAction(ActionEvent actionEvent) throws IOException {
        try {
            int id = newProdId();
            String name = nameTxt.getText();
            int stock = Integer.parseInt(invTxt.getText());
            double price = Double.parseDouble(priceTxt.getText());
            int max = Integer.parseInt(maxTxt.getText());
            int min = Integer.parseInt(minTxt.getText());

            //conditional to address that max has to be greater than min
            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Min must be less than or equal to Max");
                alert.showAndWait();

            //conditional to address that stock has to be within ranges of min and max
            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Inv must be within range between Min and Max");
                alert.showAndWait();

            //conditional to address that name field cannot be empty
            } else if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Name cannot be blank");
                alert.showAndWait();
            }

            Inventory.addProduct(new Product(id, name, price, stock, min, max));

        } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Please enter valid values for all fields");
                alert.showAndWait();
        }
    }
    /**
     * remove highlighted data from associated parts list on Button click
     * @param actionEvent
     */
    public void removePart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Product.deleteAssociatedPart((Part)(productTableView.getSelectionModel().getSelectedItem()));
        }
    }
    /**
     * a simple function to return new auto-generated product ID.
     *
     * @return newID starting at ID 1001.
     */
    public static int newProdId() {
        int newID = 1000;
        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            newID++;
        }
        return newID;
    }

    /**
     * Search box to look up either part ID or name of part
     * @param actionEvent
     */
    public void searchPartOnAction(ActionEvent actionEvent) {
        String search = searchPart.getText();
        ObservableList<Part> searchPartList = Inventory.lookupPart(search);

        if (searchPartList.size() == 0) {
            int partID = Integer.parseInt(search);
            Part item = Inventory.lookupPart(partID);
            if (item != null) {
                searchPartList.add(item);
            }
        }
    }

    /**
     * Initialize two tables - one for parts and one for associated parts to product.
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partsTblView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Product.getAllAssociatedParts());
        prodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

}
