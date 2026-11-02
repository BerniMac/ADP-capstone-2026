# Legendary Sprays – E-Commerce Capstone

An online store for cleaning sprays (home, kitchen, car). Customers sign in, browse and search products, fill a cart, check out, track their delivery and leave reviews.

The project has two parts:

| Folder | What it is |
| --- | --- |
| `Capstone-EcommerceApp` | Spring Boot backend (REST API + MySQL database) |
| `EcommerceUi` | Java Swing desktop frontend |

## Requirements

You need all of the following installed **before** running the app:

- **MySQL Server** (the database the backend stores its data in)
- **MySQL Workbench** (to check that the server is running and to view the data)
- **Java JDK 23**
- **Maven** (or an IDE such as IntelliJ IDEA that handles Maven for you)

> ⚠️ The app will **not** run without MySQL Server and MySQL Workbench set up. The backend connects to MySQL on start-up and will fail if it cannot reach it.

## Database setup

1. Install MySQL Server and MySQL Workbench.
2. Open MySQL Workbench and connect to your local server (`127.0.0.1`, port `3306`). Make sure the server is **running**.
3. The backend is configured (`Capstone-EcommerceApp/src/main/resources/application.properties`) to use:

   ```properties
   spring.datasource.url=jdbc:mysql://127.0.0.1:3306/e_commerce?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=root
   ```

   - Your MySQL user must be `root` with password `root`, **or** change the username and password in `application.properties` to match your own MySQL login.
   - You do not need to create the database or tables yourself. The `e_commerce` database and all tables are created automatically on first run.

## How to run the app

You must start the two parts **in this order**:

### Step 1 – Run `EcommerceApplicationApp.java` (backend)

Located at:
`Capstone-EcommerceApp/src/main/java/za/EcommerceApplicationApp.java`

Run it and wait until the console shows that Spring Boot has started (the API runs on `http://localhost:8080`). On start-up it also adds demo data (a demo customer and 8 spray products with stock). It only adds what is missing, so it is safe to run again.

### Step 2 – Run `Main.java` (frontend)

Located at:
`EcommerceUi/src/main/java/Capstone/Main.java`

Only run this **after** the backend from Step 1 is up. The sign-in window will open, followed by the shop.

> If you run `Main.java` first (or close the backend), the app will not be able to load products or sign in, because the frontend talks to the backend at `http://localhost:8080/api`.

## Demo login

You can sign in straight away without registering:

- **Email:** `demo@legendarysprays.co.za`
- **Password:** `Demo@1234`

You can also create your own account from the sign-in window.

## Features

- Sign in / create account
- Shop with product cards, search, sort, stock badges and star ratings
- Cart with quantity changes, VAT (15%) and delivery (free over R500)
- Checkout with validated address and card form (creates the order, items, payment, invoice and shipment, and reduces stock)
- My Orders with shipment tracker (Processing → Shipped → Delivered)
- Product reviews
- My Account: profile, orders, total spent, saved addresses and notifications

## Troubleshooting

| Problem | Fix |
| --- | --- |
| Backend fails with a connection / "Access denied" error | Make sure MySQL Server is running and the username/password in `application.properties` match your MySQL login. |
| Frontend shows a connection error | Make sure `EcommerceApplicationApp.java` is running first and port `8080` is free. |
| Build errors about Java version | Both projects target Java 23. Install JDK 23 and set it as the project SDK. |
