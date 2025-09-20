package com.main;

import com.mall.Mall;
import com.shop.Shop;
import com.product.Product;
import com.customer.Customer;
import com.cart.Cart;
import com.transaction.Transaction;
import com.payment.Payment;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
        // Load previously stored customers on startup
        List<Customer> loadedCustomers = Customer.loadCustomers();
        customers.addAll(loadedCustomers);

        // Load previously stored shops on startup
        List<Shop> loadedShops = Shop.loadShops();
        for (Shop shop : loadedShops) {
            mall.addShop(shop);
        }

        // Load previously stored products on startup and assign to shops
        List<Product> loadedProducts = Product.loadProducts();
        for (Product product : loadedProducts) {
            Shop shop = mall.getShop(product.getShopId());
            if (shop != null) {
                shop.addProduct(product);
            }
        }

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

        // Billing Tab
        Tab billingTab = new Tab("Billing");
        billingTab.setClosable(false);
        billingTab.setContent(createBillingPane());

        // Mall Details Tab
        Tab mallTab = new Tab("Mall Details");
        mallTab.setClosable(false);
        mallTab.setContent(createMallDetailsPane());

        tabPane.getTabs().addAll(shopTab, productTab, customerTab, billingTab, mallTab);

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
                Shop shop = new Shop(id, name, category, null);
                mall.addShop(shop);
                shop.saveShopData();
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
                int shopId = Integer.parseInt(selected.split(": ")[1].split(" - ")[0]);
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
                int shopId = Integer.parseInt(selectedShop.split(": ")[1].split(" - ")[0]);
                Shop shop = mall.getShop(shopId);
                int id = Integer.parseInt(prodIdField.getText());
                String name = prodNameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int qty = Integer.parseInt(qtyField.getText());
                shop.addProduct(new Product(id, name, price, qty, null, null, shopId));
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
            int shopId = Integer.parseInt(selectedShop.split(": ")[1].split(" - ")[0]);
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

    private Pane createBillingPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        // Customer Selector
        HBox customerSelector = new HBox(10);
        ComboBox<String> customerCombo = new ComboBox<>();
        updateCustomerCombo(customerCombo);
        customerSelector.getChildren().addAll(new Label("Select Customer:"), customerCombo);

        // Shop Selector
        HBox shopSelector = new HBox(10);
        ComboBox<String> shopCombo = new ComboBox<>();
        updateShopCombo(shopCombo);
        shopSelector.getChildren().addAll(new Label("Select Shop:"), shopCombo);

        // Product Search and Add
        HBox productSearch = new HBox(10);
        TextField productSearchField = new TextField();
        productSearchField.setPromptText("Search Product by Name");
        Button searchBtn = new Button("Search");
        productSearch.getChildren().addAll(productSearchField, searchBtn);

        // Product List for selection
        ListView<String> productListView = new ListView<>();
        ObservableList<String> productItems = FXCollections.observableArrayList();
        productListView.setItems(productItems);

        // Add to Cart
        HBox addToCart = new HBox(10);
        TextField qtyField = new TextField();
        qtyField.setPromptText("Quantity");
        Button addToCartBtn = new Button("Add to Cart");
        addToCart.getChildren().addAll(qtyField, addToCartBtn);

        // Cart Display
        ListView<String> cartListView = new ListView<>();
        ObservableList<String> cartItems = FXCollections.observableArrayList();
        cartListView.setItems(cartItems);
        Button removeFromCartBtn = new Button("Remove from Cart");

        // Totals
        HBox totals = new HBox(10);
        Label subtotalLabel = new Label("Subtotal: 0.00");
        Label taxLabel = new Label("Tax (10%): 0.00");
        Label totalLabel = new Label("Total: 0.00");
        totals.getChildren().addAll(subtotalLabel, taxLabel, totalLabel);

        // Loyalty Points
        HBox loyalty = new HBox(10);
        TextField pointsField = new TextField();
        pointsField.setPromptText("Redeem Points");
        Button redeemBtn = new Button("Redeem");
        loyalty.getChildren().addAll(pointsField, redeemBtn);

        // Payment
        HBox paymentBox = new HBox(10);
        ComboBox<String> paymentTypeCombo = new ComboBox<>();
        paymentTypeCombo.getItems().addAll("Cash", "Card");
        paymentTypeCombo.setValue("Cash");
        Button processPaymentBtn = new Button("Process Payment");
        paymentBox.getChildren().addAll(new Label("Payment Type:"), paymentTypeCombo, processPaymentBtn);

        // Invoice
        TextArea invoiceArea = new TextArea();
        invoiceArea.setEditable(false);
        invoiceArea.setPrefHeight(200);

        // Cart instance
        Cart cart = new Cart();

        // Event handlers
        shopCombo.setOnAction(e -> {
            String selected = shopCombo.getValue();
            if (selected != null) {
                int shopId = Integer.parseInt(selected.split(": ")[1].split(" - ")[0]);
                Shop shop = mall.getShop(shopId);
                updateProductList(productItems, shop);
            }
        });

        searchBtn.setOnAction(e -> {
            String selectedShop = shopCombo.getValue();
            if (selectedShop == null) {
                showAlert("Select a shop first");
                return;
            }
            int shopId = Integer.parseInt(selectedShop.split(": ")[1].split(" - ")[0]);
            Shop shop = mall.getShop(shopId);
            String searchTerm = productSearchField.getText().toLowerCase();
            productItems.clear();
            for (Product p : shop.listAllProducts()) {
                if (p.getProductName().toLowerCase().contains(searchTerm)) {
                    productItems.add("ID: " + p.getProductId() + ", Name: " + p.getProductName() + ", Price: " + p.getPrice() + ", Qty: " + p.getQuantity());
                }
            }
        });

        addToCartBtn.setOnAction(e -> {
            String selectedProduct = productListView.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                showAlert("Select a product first");
                return;
            }
            try {
                int prodId = Integer.parseInt(selectedProduct.split(",")[0].split(": ")[1]);
                int qty = Integer.parseInt(qtyField.getText());
                String selectedShop = shopCombo.getValue();
                int shopId = Integer.parseInt(selectedShop.split(": ")[1].split(" - ")[0]);
                Shop shop = mall.getShop(shopId);
                Product product = shop.getProduct(prodId);
                if (product != null && product.getQuantity() >= qty) {
                    cart.addProduct(product, qty);
                    updateCartList(cartItems, cart);
                    updateTotals(subtotalLabel, taxLabel, totalLabel, cart);
                } else {
                    showAlert("Insufficient stock");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid quantity");
            }
        });

        removeFromCartBtn.setOnAction(e -> {
            String selected = cartListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                String[] parts = selected.split(", ");
                String name = parts[0].split(": ")[1];
                String selectedShop = shopCombo.getValue();
                int shopId = Integer.parseInt(selectedShop.split(": ")[1].split(" - ")[0]);
                Shop shop = mall.getShop(shopId);
                Product product = shop.listAllProducts().stream()
                    .filter(p -> p.getProductName().equals(name))
                    .findFirst().orElse(null);
                if (product != null) {
                    cart.removeProduct(product);
                    updateCartList(cartItems, cart);
                    updateTotals(subtotalLabel, taxLabel, totalLabel, cart);
                }
            }
        });

        redeemBtn.setOnAction(e -> {
            String selectedCustomer = customerCombo.getValue();
            if (selectedCustomer == null) {
                showAlert("Select a customer first");
                return;
            }
            int custId = Integer.parseInt(selectedCustomer.split(",")[0].split(": ")[1]);
            Customer customer = customers.stream()
                .filter(c -> c.getCustomerId() == custId)
                .findFirst().orElse(null);
            if (customer != null) {
                try {
                    int points = Integer.parseInt(pointsField.getText());
                    if (customer.redeemPoints(points)) {
                        // Apply discount based on points (e.g., 1 point = 1 currency)
                        double discount = points;
                        double subtotal = cart.calculateSubtotal();
                        subtotal -= discount;
                        if (subtotal < 0) subtotal = 0;
                        double tax = subtotal * 0.1;
                        double total = subtotal + tax;
                        subtotalLabel.setText("Subtotal: " + String.format("%.2f", subtotal));
                        taxLabel.setText("Tax (10%): " + String.format("%.2f", tax));
                        totalLabel.setText("Total: " + String.format("%.2f", total));
                    } else {
                        showAlert("Insufficient points");
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Invalid points");
                }
            }
        });

        processPaymentBtn.setOnAction(e -> {
            String selectedCustomer = customerCombo.getValue();
            if (selectedCustomer == null) {
                showAlert("Select a customer first");
                return;
            }
            if (cart.isEmpty()) {
                showAlert("Cart is empty");
                return;
            }
            int custId = Integer.parseInt(selectedCustomer.split(",")[0].split(": ")[1]);
            Customer customer = customers.stream()
                .filter(c -> c.getCustomerId() == custId)
                .findFirst().orElse(null);
            if (customer != null) {
                String paymentType = paymentTypeCombo.getValue();
                Payment payment = new Payment(java.util.UUID.randomUUID().toString(), paymentType);
                Transaction transaction = new Transaction(java.util.UUID.randomUUID().toString(), customer, new Date(), cart);
                if (transaction.processPayment(payment)) {
                    // Update stock
                    for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
                        entry.getKey().updateStock(-entry.getValue());
                    }
                    // Add to history
                    customer.addToPurchaseHistory(transaction);
                    // Generate invoice
                    invoiceArea.setText(transaction.generateInvoice());
                    // Clear cart
                    cart.clear();
                    updateCartList(cartItems, cart);
                    updateTotals(subtotalLabel, taxLabel, totalLabel, cart);
                    showAlert("Payment successful!");
                } else {
                    showAlert("Payment failed");
                }
            }
        });

        vbox.getChildren().addAll(customerSelector, shopSelector, productSearch, productListView, addToCart, new Label("Cart:"), cartListView, removeFromCartBtn, totals, loyalty, paymentBox, new Label("Invoice:"), invoiceArea);
        return vbox;
    }

    private void updateCustomerCombo(ComboBox<String> combo) {
        combo.getItems().clear();
        for (Customer c : customers) {
            combo.getItems().add("ID: " + c.getCustomerId() + ", Name: " + c.getCustomerName());
        }
    }

    private void updateCartList(ObservableList<String> items, Cart cart) {
        items.clear();
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            items.add("Name: " + entry.getKey().getProductName() + ", Qty: " + entry.getValue() + ", Price: " + entry.getKey().getPrice());
        }
    }

    private void updateTotals(Label subtotalLabel, Label taxLabel, Label totalLabel, Cart cart) {
        double subtotal = cart.calculateSubtotal();
        double tax = subtotal * 0.1;
        double total = subtotal + tax;
        subtotalLabel.setText("Subtotal: " + String.format("%.2f", subtotal));
        taxLabel.setText("Tax (10%): " + String.format("%.2f", tax));
        totalLabel.setText("Total: " + String.format("%.2f", total));
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
