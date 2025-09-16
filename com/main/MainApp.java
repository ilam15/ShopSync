package com.main;

import com.mall.Mall;
import com.shop.Shop;
import com.product.Product;
import com.customer.Customer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Mall mall = new Mall(1, "My Mall", "City Center", null);
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        TabPane tabPane = new TabPane();

        // Shop Management Tab
        Tab shopTab = new Tab("Shop Management");
        shopTab.setClosable(false);
        shopTab.setContent(createShopManagementPane());

        // Product Management Tab
        Tab productTab = new Tab("Product Management");
        productTab.setClosable(false);
        productTab.setContent(createProductManagementPane());

        // Customer Management Tab
        Tab customerTab = new Tab("Customer Management");
        customerTab.setClosable(false);
        customerTab.setContent(createCustomerManagementPane());

        // Mall Details Tab
        Tab mallTab = new Tab("Mall Details");
        mallTab.setClosable(false);
        mallTab.setContent(createMallDetailsPane());

        tabPane.getTabs().addAll(shopTab, productTab, customerTab, mallTab);

        Scene scene = new Scene(tabPane, 800, 600);
        primaryStage.setTitle("ShopSync - Mall Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane createShopManagementPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        // Add Shop Form
        HBox addForm = new HBox(10);
        TextField shopIdField = new TextField();
        shopIdField.setPromptText("Shop ID");
        TextField shopNameField = new TextField();
        shopNameField.setPromptText("Shop Name");
        TextField categoryField = new TextField();
        categoryField.setPromptText("Category");
        Button addShopBtn = new Button("Add Shop");
        addForm.getChildren().addAll(new Label("ID:"), shopIdField, new Label("Name:"), shopNameField, new Label("Category:"), categoryField, addShopBtn);

        // Shop List
        ListView<String> shopListView = new ListView<>();
        ObservableList<String> shopItems = FXCollections.observableArrayList();
        shopListView.setItems(shopItems);
        Button deleteShopBtn = new Button("Delete Selected Shop");

        addShopBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(shopIdField.getText());
                String name = shopNameField.getText();
                String category = categoryField.getText();
                mall.addShop(new Shop(id, name, category, null));
                updateShopList(shopItems);
                shopIdField.clear();
                shopNameField.clear();
                categoryField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid input for Shop ID");
            }
        });

        deleteShopBtn.setOnAction(e -> {
            String selected = shopListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int id = Integer.parseInt(selected.split(",")[0].split(": ")[1]);
                mall.removeShop(id);
                updateShopList(shopItems);
            }
        });

        updateShopList(shopItems);

        vbox.getChildren().addAll(addForm, new Label("Shops:"), shopListView, deleteShopBtn);
        return vbox;
    }

    private void updateShopList(ObservableList<String> items) {
        items.clear();
        for (Shop s : mall.listAllShops()) {
            items.add("ID: " + s.getShopId() + ", Name: " + s.getShopName() + " (" + s.getCategory() + ")");
        }
    }

    private Pane createProductManagementPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        // Shop Selector
        HBox shopSelector = new HBox(10);
        ComboBox<String> shopCombo = new ComboBox<>();
        updateShopCombo(shopCombo);
        Button refreshShopsBtn = new Button("Refresh Shops");
        shopSelector.getChildren().addAll(new Label("Select Shop:"), shopCombo, refreshShopsBtn);

        refreshShopsBtn.setOnAction(e -> updateShopCombo(shopCombo));

        // Add Product Form
        HBox addForm = new HBox(10);
        TextField prodIdField = new TextField();
        prodIdField.setPromptText("Product ID");
        TextField prodNameField = new TextField();
        prodNameField.setPromptText("Product Name");
        TextField priceField = new TextField();
        priceField.setPromptText("Price");
        TextField qtyField = new TextField();
        qtyField.setPromptText("Quantity");
        Button addProdBtn = new Button("Add Product");
        addForm.getChildren().addAll(new Label("ID:"), prodIdField, new Label("Name:"), prodNameField, new Label("Price:"), priceField, new Label("Qty:"), qtyField, addProdBtn);

        // Product List
        ListView<String> prodListView = new ListView<>();
        ObservableList<String> prodItems = FXCollections.observableArrayList();
        prodListView.setItems(prodItems);
        Button deleteProdBtn = new Button("Delete Selected Product");

        shopCombo.setOnAction(e -> {
            String selected = shopCombo.getValue();
            if (selected != null) {
                int shopId = Integer.parseInt(selected.split(": ")[1]);
                Shop shop = mall.getShop(shopId);
                updateProductList(prodItems, shop);
            }
        });

        addProdBtn.setOnAction(e -> {
            String selectedShop = shopCombo.getValue();
            if (selectedShop == null) {
                showAlert("Select a shop first");
                return;
            }
            try {
                int shopId = Integer.parseInt(selectedShop.split(": ")[1]);
                Shop shop = mall.getShop(shopId);
                int id = Integer.parseInt(prodIdField.getText());
                String name = prodNameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int qty = Integer.parseInt(qtyField.getText());
                shop.addProduct(new Product(id, name, price, qty, null, null));
                updateProductList(prodItems, shop);
                prodIdField.clear();
                prodNameField.clear();
                priceField.clear();
                qtyField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid input");
            }
        });

        deleteProdBtn.setOnAction(e -> {
            String selectedShop = shopCombo.getValue();
            if (selectedShop == null) return;
            int shopId = Integer.parseInt(selectedShop.split(": ")[1]);
            Shop shop = mall.getShop(shopId);
            String selectedProd = prodListView.getSelectionModel().getSelectedItem();
            if (selectedProd != null) {
                int prodId = Integer.parseInt(selectedProd.split(",")[0].split(": ")[1]);
                shop.removeProduct(prodId);
                updateProductList(prodItems, shop);
            }
        });

        vbox.getChildren().addAll(shopSelector, addForm, new Label("Products:"), prodListView, deleteProdBtn);
        return vbox;
    }

    private void updateShopCombo(ComboBox<String> combo) {
        combo.getItems().clear();
        for (Shop s : mall.listAllShops()) {
            combo.getItems().add("ID: " + s.getShopId() + " - " + s.getShopName());
        }
    }

    private void updateProductList(ObservableList<String> items, Shop shop) {
        items.clear();
        if (shop != null) {
            for (Product p : shop.listAllProducts()) {
                items.add("ID: " + p.getProductId() + ", Name: " + p.getProductName() + ", Price: " + p.getPrice() + ", Qty: " + p.getQuantity());
            }
        }
    }

    private Pane createCustomerManagementPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        // Add Customer Form
        HBox addForm = new HBox(10);
        TextField custIdField = new TextField();
        custIdField.setPromptText("Customer ID");
        TextField custNameField = new TextField();
        custNameField.setPromptText("Customer Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        Button addCustBtn = new Button("Add Customer");
        addForm.getChildren().addAll(new Label("ID:"), custIdField, new Label("Name:"), custNameField, new Label("Email:"), emailField, addCustBtn);

        // Customer List
        ListView<String> custListView = new ListView<>();
        ObservableList<String> custItems = FXCollections.observableArrayList();
        custListView.setItems(custItems);
        Button deleteCustBtn = new Button("Delete Selected Customer");

        addCustBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(custIdField.getText());
                String name = custNameField.getText();
                String email = emailField.getText();
                customers.add(new Customer(id, name, email));
                updateCustomerList(custItems);
                custIdField.clear();
                custNameField.clear();
                emailField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid input for Customer ID");
            }
        });

        deleteCustBtn.setOnAction(e -> {
            String selected = custListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                int id = Integer.parseInt(selected.split(",")[0].split(": ")[1]);
                customers.removeIf(c -> c.getCustomerId() == id);
                updateCustomerList(custItems);
            }
        });

        updateCustomerList(custItems);

        vbox.getChildren().addAll(addForm, new Label("Customers:"), custListView, deleteCustBtn);
        return vbox;
    }

    private void updateCustomerList(ObservableList<String> items) {
        items.clear();
        for (Customer c : customers) {
            items.add("ID: " + c.getCustomerId() + ", Name: " + c.getCustomerName() + " (" + c.getEmail() + ")");
        }
    }

    private Pane createMallDetailsPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        Label detailsLabel = new Label(mall.getMallReport());
        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnAction(e -> detailsLabel.setText(mall.getMallReport()));
        vbox.getChildren().addAll(detailsLabel, refreshBtn);
        return vbox;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
