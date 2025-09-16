# TODO: Complete Billing System for Mall

## Step 1: Create Cart Class ✅
- Create `com/cart/Cart.java` with methods to add/remove products, calculate subtotal, apply discounts.

## Step 2: Modify Transaction Class ✅
- Edit `com/transaction/Transaction.java` to support multiple products via Cart instead of single product.

## Step 3: Enhance Payment Class ✅
- Edit `com/payment/Payment.java` to support payment types (Cash, Card, etc.), improve processing logic.

## Step 4: Add Billing Tab to MainApp
- Edit `com/main/MainApp.java` to add a new "Billing" tab with:
  - Customer selection dropdown
  - Shop selection dropdown
  - Product search and add to cart functionality
  - Cart display with remove items
  - Discount/loyalty points application
  - Subtotal, tax, total calculation (10% tax)
  - Payment method selection
  - Process payment and generate invoice
  - Update stock and customer history

## Step 5: Update Customer Management Tab
- Add transaction history view in Customer Management tab in `MainApp.java`.

## Step 6: Update Customer Class
- Edit `com/customer/Customer.java` to add a method for purchasing from cart.

## Step 7: Testing and Followup
- Test the billing flow in the GUI.
- Add any missing features like refunds if needed.
