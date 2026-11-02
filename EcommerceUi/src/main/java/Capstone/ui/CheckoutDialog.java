package Capstone.ui;

import Capstone.app.Cart;
import Capstone.app.CheckoutService;
import Capstone.app.Session;
import Capstone.model.Address;
import Capstone.net.Api;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

/** Collects delivery and card details, then places a real order through the backend. */
public class CheckoutDialog extends JDialog {

    private static final String[] PROVINCES = {
            "Western Cape", "Eastern Cape", "Northern Cape", "Gauteng", "KwaZulu-Natal",
            "Free State", "Limpopo", "Mpumalanga", "North West"};

    private final JTextField street = Theme.field(20);
    private final JTextField city = Theme.field(20);
    private final JComboBox<String> province = new JComboBox<>(PROVINCES);
    private final JTextField postal = Theme.field(20);
    private final JTextField country = Theme.field(20);

    private final JTextField holder = Theme.field(20);
    private final JTextField number = Theme.field(20);
    private final JTextField expiry = Theme.field(20);
    private final JPasswordField cvv = new JPasswordField(20);

    private final JLabel problem = Theme.label(" ", Font.PLAIN, 13f, Theme.DANGER);
    private final JLabel progress = Theme.muted(" ");
    private final JButton pay = Theme.accent("Place order");
    private final JButton cancel = Theme.ghost("Cancel");

    private boolean orderPlaced = false;

    public CheckoutDialog(JFrame owner) {
        super(owner, "Checkout", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(0, 14));
        root.setBackground(Theme.BG);
        root.setBorder(new EmptyBorder(18, 22, 16, 22));

        root.add(Theme.title("Checkout"), BorderLayout.NORTH);

        JPanel centre = new JPanel(new GridLayout(1, 2, 16, 0));
        centre.setOpaque(false);

        JPanel forms = new JPanel();
        forms.setOpaque(false);
        forms.setLayout(new BoxLayout(forms, BoxLayout.Y_AXIS));
        forms.add(createDeliveryCard());
        forms.add(Box.createVerticalStrut(14));
        forms.add(createPaymentCard());
        centre.add(forms);
        centre.add(createSummaryCard());
        root.add(centre, BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout(0, 6));
        south.setOpaque(false);
        JPanel messages = new JPanel(new GridLayout(0, 1));
        messages.setOpaque(false);
        messages.add(problem);
        messages.add(progress);
        south.add(messages, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);
        cancel.addActionListener(e -> dispose());
        pay.setText("Place order  -  " + Theme.money(Cart.get().total()));
        pay.addActionListener(e -> placeOrder());
        buttons.add(cancel);
        buttons.add(pay);
        south.add(buttons, BorderLayout.EAST);
        root.add(south, BorderLayout.SOUTH);

        setContentPane(root);
        setSize(960, 700);
        setLocationRelativeTo(owner);
        getRootPane().setDefaultButton(pay);

        cvv.setFont(Theme.font(Font.PLAIN, 14f));
        cvv.setMargin(new Insets(6, 8, 6, 8));
        country.setText("South Africa");
        prefillAddress();
    }

    public boolean isOrderPlaced() {
        return orderPlaced;
    }

    // ---------- layout ----------

    private JPanel createDeliveryCard() {
        Theme.RoundedPanel card = new Theme.RoundedPanel(16);
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = constraints();
        addHeading(card, gbc, "Delivery address");
        addRow(card, gbc, 1, "Street", street);
        addRow(card, gbc, 2, "City", city);
        addRow(card, gbc, 3, "Province", province);
        addRow(card, gbc, 4, "Postal code", postal);
        addRow(card, gbc, 5, "Country", country);
        addFiller(card, gbc, 6);
        return card;
    }

    private JPanel createPaymentCard() {
        Theme.RoundedPanel card = new Theme.RoundedPanel(16);
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = constraints();
        addHeading(card, gbc, "Payment (demo - no real charge)");
        addRow(card, gbc, 1, "Name on card", holder);
        addRow(card, gbc, 2, "Card number", number);
        addRow(card, gbc, 3, "Expiry (MM/YY)", expiry);
        addRow(card, gbc, 4, "CVV", cvv);
        addFiller(card, gbc, 5);
        return card;
    }

    private JPanel createSummaryCard() {
        Cart cart = Cart.get();
        Theme.RoundedPanel card = new Theme.RoundedPanel(16);
        card.setLayout(new BorderLayout(0, 10));

        card.add(Theme.heading("Order summary"), BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setOpaque(false);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        for (Cart.Line line : cart.getLines()) {
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
            row.add(Theme.label(line.getQuantity() + " x " + line.getProduct().getProductName(),
                    Font.PLAIN, 14f, Theme.TEXT), BorderLayout.WEST);
            row.add(Theme.label(Theme.money(line.getLineTotal()), Font.PLAIN, 14f, Theme.TEXT),
                    BorderLayout.EAST);
            list.add(row);
        }
        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        card.add(scroll, BorderLayout.CENTER);

        JPanel totals = new JPanel(new GridLayout(0, 2, 6, 6));
        totals.setOpaque(false);
        addTotal(totals, "Subtotal", Theme.money(cart.subtotal()), false);
        addTotal(totals, "VAT (15%)", Theme.money(cart.vat()), false);
        addTotal(totals, "Delivery", cart.shipping() == 0 ? "Free" : Theme.money(cart.shipping()), false);
        addTotal(totals, "Total", Theme.money(cart.total()), true);
        addTotal(totals, "Estimated delivery", "3-5 business days", false);

        card.add(totals, BorderLayout.SOUTH);
        return card;
    }

    private void addTotal(JPanel panel, String label, String value, boolean bold) {
        panel.add(Theme.label(label, bold ? Font.BOLD : Font.PLAIN, bold ? 17f : 14f,
                bold ? Theme.TEXT : Theme.MUTED));
        JLabel right = Theme.label(value, bold ? Font.BOLD : Font.PLAIN, bold ? 17f : 14f, Theme.TEXT);
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(right);
    }

    private GridBagConstraints constraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void addFiller(JPanel panel, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        panel.add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0;
        gbc.gridwidth = 1;
    }

    private void addHeading(JPanel panel, GridBagConstraints gbc, String text) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(Theme.heading(text), gbc);
        gbc.gridwidth = 1;
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridy = row;
        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(Theme.label(label, Font.PLAIN, 13f, Theme.MUTED), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    // ---------- behaviour ----------

    private void prefillAddress() {
        Api.async(() -> Api.get("/addresses/customer/" + Session.customerId(),
                        new TypeReference<List<Address>>() {}),
                addresses -> {
                    if (addresses != null && !addresses.isEmpty() && street.getText().isEmpty()) {
                        Address a = addresses.get(addresses.size() - 1);
                        street.setText(a.getStreetAddress());
                        city.setText(a.getCity());
                        postal.setText(a.getPostalCode());
                        if (a.getCountry() != null) {
                            country.setText(a.getCountry());
                        }
                        if (a.getState() != null) {
                            province.setSelectedItem(a.getState());
                        }
                    }
                },
                error -> { /* a missing saved address is fine */ });
    }

    private String checkInputs() {
        if (street.getText().isBlank() || city.getText().isBlank()
                || postal.getText().isBlank() || country.getText().isBlank()) {
            return "Please complete your delivery address.";
        }
        if (!postal.getText().trim().matches("\\d{4}")) {
            return "Postal code must be 4 digits.";
        }
        if (holder.getText().isBlank()) {
            return "Please enter the name on the card.";
        }
        String digits = number.getText().replaceAll("[\\s-]", "");
        if (!digits.matches("\\d{13,19}")) {
            return "Card number must be 13 to 19 digits.";
        }
        String exp = expiry.getText().trim();
        if (!exp.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            return "Expiry must look like 08/28.";
        }
        YearMonth expires = YearMonth.parse(exp, DateTimeFormatter.ofPattern("MM/yy"));
        if (expires.isBefore(YearMonth.now())) {
            return "That card has expired.";
        }
        if (!new String(cvv.getPassword()).matches("\\d{3,4}")) {
            return "CVV must be 3 or 4 digits.";
        }
        return null;
    }

    private void placeOrder() {
        if (Cart.get().isEmpty()) {
            problem.setText("Your cart is empty.");
            return;
        }
        String error = checkInputs();
        if (error != null) {
            problem.setText(error);
            return;
        }
        problem.setText(" ");

        CheckoutService.Delivery delivery = new CheckoutService.Delivery(
                street.getText().trim(), city.getText().trim(),
                String.valueOf(province.getSelectedItem()),
                postal.getText().trim(), country.getText().trim());
        CheckoutService.CardInfo card = new CheckoutService.CardInfo(
                holder.getText().trim(),
                number.getText().replaceAll("[\\s-]", ""),
                expiry.getText().trim());

        List<Cart.Line> lines = Cart.get().getLines();

        pay.setEnabled(false);
        cancel.setEnabled(false);

        SwingWorker<CheckoutService.Receipt, String> worker = new SwingWorker<>() {
            @Override
            protected CheckoutService.Receipt doInBackground() throws Exception {
                return CheckoutService.place(Session.customer(), lines, delivery, card, this::publish);
            }

            @Override
            protected void process(List<String> messages) {
                progress.setText(messages.get(messages.size() - 1));
            }

            @Override
            protected void done() {
                try {
                    CheckoutService.Receipt receipt = get();
                    orderPlaced = true;
                    Cart.get().clear();
                    dispose();
                    JOptionPane.showMessageDialog(getOwner(),
                            "Thank you! Your order " + receipt.shortId() + " is confirmed.\n\n"
                                    + "Total paid: " + Theme.money(receipt.total()) + "\n"
                                    + "Estimated delivery: "
                                    + receipt.estimatedDelivery().toLocalDate() + "\n\n"
                                    + "Track it on the My Orders tab.",
                            "Order confirmed", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    Throwable cause = e.getCause() != null ? e.getCause() : e;
                    problem.setText("<html>" + Theme.friendly(cause) + "</html>");
                    progress.setText(" ");
                    pay.setEnabled(true);
                    cancel.setEnabled(true);
                }
            }
        };
        worker.execute();
    }
}
