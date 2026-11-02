# Legendary Sprays - what changed

## Purpose
An online store for cleaning sprays (home, kitchen, car). Customers sign in, browse and search
products, fill a cart, check out, track the delivery and leave reviews.

## Frontend (EcommerceUi)
- Sign in / create account (no more hardcoded customer ID).
- Shop: product cards, search, sort, stock badges, star ratings, add to cart.
- Cart: change quantities, VAT (15%) and delivery (free over R500) calculated.
- Checkout: validated address and card form, then a REAL order is created:
  order + items + payment + invoice + shipment, and stock is reduced.
  If any step fails the order is removed again. Only the last 4 card digits are stored; the CVV is never sent.
- My Orders: order list, items with product names, totals, shipment tracker
  (Processing -> Shipped -> Delivered) and a demo button that advances the shipment.
- Reviews: pick a product, read reviews, add your own (the old version sent the wrong JSON).
- My Account: profile, orders placed, total spent, saved addresses, notifications.
- Modern theme (FlatLaf is added in pom.xml; the app still runs without it).

## Backend (Capstone-EcommerceApp)
- NEW config/DataSeeder.java: adds a demo customer (demo@legendarysprays.co.za), 8 spray products and
  stock records on start-up. It only adds what is missing, so it is safe to run again.
- domain/OrderItem.java: the product is now included in the JSON (the order items table showed blank product names).
