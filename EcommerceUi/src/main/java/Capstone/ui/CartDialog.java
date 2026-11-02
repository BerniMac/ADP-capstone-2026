package Capstone.ui;

import Capstone.app.Cart;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/** Shows the cart, lets the customer change quantities, and leads to checkout. */
public class CartDialog extends JDialog {

    private final Cart cart = Cart.get();
    private final JPanel rows = new JPanel();
    private final JPanel totals = new JPanel(new GridLayout(0, 2, 6, 6));
    private final JLabel notice = Theme.label(" ", Font.PLAIN, 13f, Theme.WARN);
    private final JButton checkout = Theme.accent("Checkout");
    private boolean checkoutRequested = false;

    public CartDialog(JFrame owner) {
        super(owner, "Your cart", true);

        JPanel root = new JPanel(new BorderLayout(0, 12));
        root.setBackground(Theme.BG);
        root.setBorder(new EmptyBorder(18, 20, 16, 20));

        root.add(Theme.title("Your cart"), BorderLayout.NORTH);

        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS));
        rows.setOpaque(false);
        JPanel rowsWrapper = new Theme.ViewportPanel(new BorderLayout());
        rowsWrapper.add(rows, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(rowsWrapper);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        root.add(scroll, BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout(0, 10));
        south.setOpaque(false);

        Theme.RoundedPanel totalsCard = new Theme.RoundedPanel(14);
        totalsCard.setLayout(new BorderLayout());
        totals.setOpaque(false);
        totalsCard.add(totals, BorderLayout.CENTER);
        south.add(totalsCard, BorderLayout.NORTH);
        south.add(notice, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);
        JButton keepShopping = Theme.ghost("Continue shopping");
        keepShopping.addActionListener(e -> dispose());
        JButton clear = Theme.ghost("Empty cart");
        clear.addActionListener(e -> {
            cart.clear();
            refresh();
        });
        checkout.addActionListener(e -> {
            checkoutRequested = true;
            dispose();
        });
        buttons.add(clear);
        buttons.add(keepShopping);
        buttons.add(checkout);
        south.add(buttons, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);

        setContentPane(root);
        setSize(640, 560);
        setLocationRelativeTo(owner);
        refresh();
    }

    public boolean isCheckoutRequested() {
        return checkoutRequested;
    }

    private void refresh() {
        rows.removeAll();

        if (cart.isEmpty()) {
            JPanel empty = new JPanel(new BorderLayout());
            empty.setOpaque(false);
            empty.setBorder(new EmptyBorder(40, 0, 40, 0));
            JLabel text = Theme.muted("Your cart is empty. Add some sprays from the Shop tab.");
            text.setHorizontalAlignment(SwingConstants.CENTER);
            empty.add(text, BorderLayout.CENTER);
            rows.add(empty);
        }

        for (Cart.Line line : cart.getLines()) {
            rows.add(createRow(line));
            rows.add(Box.createVerticalStrut(8));
        }

        totals.removeAll();
        addTotal("Subtotal", Theme.money(cart.subtotal()), false);
        addTotal("VAT (15%)", Theme.money(cart.vat()), false);
        String shipping = cart.shipping() == 0 && !cart.isEmpty()
                ? "Free" : Theme.money(cart.shipping());
        addTotal("Delivery", shipping, false);
        addTotal("Total", Theme.money(cart.total()), true);

        double toFree = Cart.FREE_SHIPPING_FROM - cart.subtotal();
        if (!cart.isEmpty() && toFree > 0) {
            notice.setForeground(Theme.WARN);
            notice.setText("Spend " + Theme.money(toFree) + " more for free delivery.");
        } else {
            notice.setText(" ");
        }

        checkout.setEnabled(!cart.isEmpty());
        rows.revalidate();
        rows.repaint();
        totals.revalidate();
        totals.repaint();
    }

    private void addTotal(String label, String value, boolean bold) {
        JLabel left = Theme.label(label, bold ? Font.BOLD : Font.PLAIN, bold ? 17f : 14f,
                bold ? Theme.TEXT : Theme.MUTED);
        JLabel right = Theme.label(value, bold ? Font.BOLD : Font.PLAIN, bold ? 17f : 14f, Theme.TEXT);
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        totals.add(left);
        totals.add(right);
    }

    private JPanel createRow(Cart.Line line) {
        Theme.RoundedPanel row = new Theme.RoundedPanel(14);
        row.setLayout(new BorderLayout(12, 0));
        row.setBorder(new EmptyBorder(10, 14, 10, 14));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        JPanel info = new JPanel(new GridLayout(0, 1));
        info.setOpaque(false);
        info.add(Theme.label(line.getProduct().getProductName(), Font.BOLD, 15f, Theme.TEXT));
        info.add(Theme.muted(Theme.money(line.getProduct().getCurrentPrice()) + " each"));
        row.add(info, BorderLayout.CENTER);

        JPanel qty = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        qty.setOpaque(false);
        JButton minus = Theme.ghost("-");
        JButton plus = Theme.ghost("+");
        minus.setBorder(new EmptyBorder(6, 12, 6, 12));
        plus.setBorder(new EmptyBorder(6, 12, 6, 12));
        minus.addActionListener(e -> {
            cart.setQuantity(line, line.getQuantity() - 1);
            refresh();
        });
        plus.addActionListener(e -> {
            if (!cart.setQuantity(line, line.getQuantity() + 1)) {
                notice.setForeground(Theme.WARN);
                notice.setText("Only " + line.getMaxStock() + " of "
                        + line.getProduct().getProductName() + " in stock.");
            } else {
                refresh();
            }
        });
        qty.add(minus);
        qty.add(Theme.label(String.valueOf(line.getQuantity()), Font.BOLD, 15f, Theme.TEXT));
        qty.add(plus);
        row.add(qty, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        right.add(Theme.label(Theme.money(line.getLineTotal()), Font.BOLD, 15f, Theme.PRIMARY_DARK));
        JButton remove = Theme.ghost("Remove");
        remove.addActionListener(e -> {
            cart.remove(line);
            refresh();
        });
        right.add(remove);
        row.add(right, BorderLayout.EAST);
        return row;
    }
}
