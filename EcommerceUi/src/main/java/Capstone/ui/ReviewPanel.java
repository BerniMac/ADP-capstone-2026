package Capstone.ui;

import Capstone.app.Session;
import Capstone.model.Product;
import Capstone.model.Review;
import Capstone.net.Api;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Reviews: read what others think of a product and add your own rating. */
public class ReviewPanel extends JPanel {

    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("d MMM yyyy");

    private final DefaultListModel<Product> productModel = new DefaultListModel<>();
    private final JList<Product> productList = new JList<>(productModel);

    private final JLabel productTitle = Theme.title("Choose a product");
    private final JLabel summary = Theme.muted(" ");
    private final JPanel reviewsBox = new JPanel();

    private final JComboBox<Integer> rating = new JComboBox<>(new Integer[]{5, 4, 3, 2, 1});
    private final JTextArea comment = new JTextArea(3, 20);
    private final JLabel message = Theme.label(" ", Font.PLAIN, 13f, Theme.DANGER);
    private final JButton submit = Theme.primary("Submit review");

    public ReviewPanel() {
        setLayout(new BorderLayout(16, 0));
        setBackground(Theme.BG);
        setBorder(new EmptyBorder(18, 24, 14, 24));

        add(createProductCard(), BorderLayout.WEST);
        add(createDetailCard(), BorderLayout.CENTER);

        productList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadReviews();
            }
        });
        submit.addActionListener(e -> submitReview());

        loadProducts();
    }

    private JComponent createProductCard() {
        Theme.RoundedPanel card = new Theme.RoundedPanel(16);
        card.setLayout(new BorderLayout(0, 8));
        card.setBorder(new EmptyBorder(14, 14, 14, 14));
        card.setPreferredSize(new Dimension(300, 10));
        card.add(Theme.heading("Products"), BorderLayout.NORTH);

        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productList.setFixedCellHeight(38);
        productList.setFont(Theme.font(Font.PLAIN, 14f));
        productList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean selected, boolean focus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, selected, focus);
                label.setText(((Product) value).getProductName());
                label.setBorder(new EmptyBorder(0, 10, 0, 10));
                label.setBackground(selected ? Theme.PRIMARY_LIGHT : Theme.SURFACE);
                label.setForeground(Theme.TEXT);
                return label;
            }
        });
        card.add(Theme.scroll(productList), BorderLayout.CENTER);
        return card;
    }

    private JComponent createDetailCard() {
        Theme.RoundedPanel card = new Theme.RoundedPanel(16);
        card.setLayout(new BorderLayout(0, 12));
        card.setBorder(new EmptyBorder(16, 18, 16, 18));

        JPanel head = new JPanel(new GridLayout(0, 1));
        head.setOpaque(false);
        head.add(productTitle);
        head.add(summary);
        card.add(head, BorderLayout.NORTH);

        reviewsBox.setLayout(new BoxLayout(reviewsBox, BoxLayout.Y_AXIS));
        reviewsBox.setOpaque(false);
        JPanel wrapper = new Theme.ViewportPanel(new BorderLayout());
        wrapper.add(reviewsBox, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        card.add(scroll, BorderLayout.CENTER);

        card.add(createForm(), BorderLayout.SOUTH);
        return card;
    }

    private JPanel createForm() {
        Theme.RoundedPanel form = new Theme.RoundedPanel(12, new Color(0xF8FAFB));
        form.setLayout(new BorderLayout(0, 8));
        form.add(Theme.heading("Write a review"), BorderLayout.NORTH);

        JPanel fields = new JPanel(new BorderLayout(10, 8));
        fields.setOpaque(false);

        JPanel ratingRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        ratingRow.setOpaque(false);
        ratingRow.add(Theme.muted("Rating"));
        rating.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean selected, boolean focus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, selected, focus);
                label.setText(Theme.stars((Integer) value) + "  " + value);
                label.setFont(Theme.starFont(Font.PLAIN, 14f));
                return label;
            }
        });
        ratingRow.add(rating);
        fields.add(ratingRow, BorderLayout.NORTH);

        comment.setLineWrap(true);
        comment.setWrapStyleWord(true);
        comment.setFont(Theme.font(Font.PLAIN, 14f));
        comment.setBorder(new EmptyBorder(6, 8, 6, 8));
        fields.add(new JScrollPane(comment), BorderLayout.CENTER);
        form.add(fields, BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout());
        south.setOpaque(false);
        south.add(message, BorderLayout.CENTER);
        south.add(submit, BorderLayout.EAST);
        form.add(south, BorderLayout.SOUTH);
        return form;
    }

    // ---------- loading ----------

    private void loadProducts() {
        Api.async(() -> Api.get("/products", new TypeReference<List<Product>>() {}),
                products -> {
                    productModel.clear();
                    if (products != null) {
                        for (Product product : products) {
                            productModel.addElement(product);
                        }
                    }
                    if (!productModel.isEmpty()) {
                        productList.setSelectedIndex(0);
                    }
                },
                error -> summary.setText("Could not load products: " + Theme.friendly(error)));
    }

    private void loadReviews() {
        Product product = productList.getSelectedValue();
        if (product == null) {
            return;
        }
        productTitle.setText(product.getProductName());
        summary.setText("Loading reviews...");

        Api.async(() -> Api.get("/reviews/product/" + product.getProductId(),
                        new TypeReference<List<Review>>() {}),
                reviews -> {
                    List<Review> list = reviews == null ? new ArrayList<>() : reviews;
                    showReviews(list);
                },
                error -> summary.setText("Could not load reviews: " + Theme.friendly(error)));
    }

    private void showReviews(List<Review> reviews) {
        reviewsBox.removeAll();

        if (reviews.isEmpty()) {
            summary.setText("No reviews yet");
            JLabel none = Theme.muted("Nobody has reviewed this product yet. Be the first!");
            none.setBorder(new EmptyBorder(16, 16, 16, 16));
            reviewsBox.add(none);
        } else {
            double sum = 0;
            for (Review review : reviews) {
                sum += review.getRating();
            }
            summary.setText(String.format("%s  %.1f out of 5  (%d %s)",
                    Theme.stars((int) Math.round(sum / reviews.size())), sum / reviews.size(),
                    reviews.size(), reviews.size() == 1 ? "review" : "reviews"));
            summary.setFont(Theme.starFont(summary.getFont().getStyle(), summary.getFont().getSize2D()));

            for (Review review : reviews) {
                Theme.RoundedPanel item = new Theme.RoundedPanel(12);
                item.setLayout(new BorderLayout(0, 4));
                item.setBorder(new EmptyBorder(10, 14, 10, 14));
                item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

                JPanel line = new JPanel(new BorderLayout());
                line.setOpaque(false);
                line.add(Theme.starLabel(Theme.stars(review.getRating()), 15f, Theme.WARN),
                        BorderLayout.WEST);
                line.add(Theme.muted(review.getReviewDate() == null ? "" : review.getReviewDate().format(DATE)),
                        BorderLayout.EAST);
                item.add(line, BorderLayout.NORTH);
                item.add(Theme.label(Theme.html(review.getComment(), 420), Font.PLAIN, 14f, Theme.TEXT),
                        BorderLayout.CENTER);

                reviewsBox.add(item);
                reviewsBox.add(Box.createVerticalStrut(8));
            }
        }
        reviewsBox.revalidate();
        reviewsBox.repaint();
    }

    // ---------- submitting ----------

    private void submitReview() {
        Product product = productList.getSelectedValue();
        if (product == null) {
            message.setText("Please choose a product first.");
            return;
        }
        String text = comment.getText().trim();
        if (text.isEmpty()) {
            message.setText("Please write a short comment.");
            return;
        }
        int stars = (Integer) rating.getSelectedItem();

        message.setText(" ");
        submit.setEnabled(false);

        Api.async(() -> {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("customer", Map.of("customerId", Session.customerId()));
            body.put("product", Map.of("productId", product.getProductId()));
            body.put("rating", stars);
            body.put("comment", text);
            Api.post("/reviews", body);
            return Boolean.TRUE;
        }, ok -> {
            submit.setEnabled(true);
            comment.setText("");
            message.setForeground(Theme.SUCCESS);
            message.setText("Thanks for your review!");
            loadReviews();
        }, error -> {
            submit.setEnabled(true);
            message.setForeground(Theme.DANGER);
            message.setText("<html>" + Theme.friendly(error) + "</html>");
        });
    }
}
