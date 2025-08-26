package com.main;

import com.mall.Mall;
import com.shop.Shop;
import com.product.Product;
import com.customer.Customer;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Mall mall = new Mall(1, "My Mall", "City Center", null);
        List<Customer> customers = new ArrayList<>();
        int choice;
        do {
            System.out.println("\n===== ShopSync =====");
            System.out.println("1. Add Shop");
            System.out.println("2. Add Product to Shop");
            System.out.println("3. Add Customer");
            System.out.println("4. View Mall Details");
            System.out.println("5. View Shops");
            System.out.println("6. View Customers");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter shop id: ");
                    int shopId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter shop name: ");
                    String shopName = scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    mall.addShop(new Shop(shopId, shopName, category, null));
                    System.out.println("Shop added.");
                    break;
                case 2:
                    System.out.print("Enter shop id to add product: ");
                    int sId = scanner.nextInt();
                    scanner.nextLine();
                    Shop shop = mall.getShop(sId);
                    if (shop == null) {
                        System.out.println("Shop not found.");
                        break;
                    }
                    System.out.print("Enter product id: ");
                    int pId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter product name: ");
                    String pName = scanner.nextLine();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();
                    scanner.nextLine();
                    shop.addProduct(new Product(pId, pName, price, qty, null, null));
                    System.out.println("Product added to shop.");
                    break;
                case 3:
                    System.out.print("Enter customer id: ");
                    int cId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter customer name: ");
                    String cName = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    customers.add(new Customer(cId, cName, email));
                    System.out.println("Customer added.");
                    break;
                case 4:
                    System.out.println(mall.getMallReport());
                    break;
                case 5:
                    for (Shop sh : mall.listAllShops()) {
                        System.out.println(sh.getShopName() + " (" + sh.getCategory() + ")");
                        for (Product prod : sh.listAllProducts()) {
                            System.out.println("  - " + prod.getProductName() + ", Price: " + prod.getPrice() + ", Qty: " + prod.getQuantity());
                        }
                    }
                    break;
                case 6:
                    for (Customer cust : customers) {
                        System.out.println(cust.getCustomerName() + " (" + cust.getEmail() + ")");
                    }
                    break;
                case 0:
                    System.out.println("Exiting Mall Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        scanner.close();
    }
}