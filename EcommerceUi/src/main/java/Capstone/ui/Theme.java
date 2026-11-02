package Capstone.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Locale;

/** Colours, fonts and small reusable components so every screen looks the same. */
public final class Theme {

    public static final Color BG = new Color(0xF3F6F8);
    public static final Color SURFACE = Color.WHITE;
    public static final Color DARK = new Color(0x0B2E3B);
    public static final Color PRIMARY = new Color(0x0E7C86);
    public static final Color PRIMARY_DARK = new Color(0x0A5F67);
    public static final Color PRIMARY_LIGHT = new Color(0xDDF1F2);
    public static final Color ACCENT = new Color(0xF28C28);
    public static final Color ACCENT_DARK = new Color(0xD97611);
    public static final Color TEXT = new Color(0x1B2A33);
    public static final Color MUTED = new Color(0x6B7C87);
    public static final Color BORDER = new Color(0xDCE4E9);
    public static final Color SUCCESS = new Color(0x2E9E5B);
    public static final Color WARN = new Color(0xD98E04);
    public static final Color DANGER = new Color(0xD64545);

    private static final String FONT_NAME = "Segoe UI";

    private Theme() {}

    // ---------- set-up ----------

    /** Uses the modern FlatLaf look when it is available, otherwise the system look. */
    public static void install() {
        try {
            UIManager.put("defaultFont", new Font(FONT_NAME, Font.PLAIN, 14));
            UIManager.put("Component.arc", 12);
            UIManager.put("Button.arc", 12);
            UIManager.put("TextComponent.arc", 10);
            UIManager.put("ScrollBar.thumbArc", 999);
            UIManager.put("ScrollBar.width", 12);
            UIManager.put("TabbedPane.tabHeight", 40);
            UIManager.put("TabbedPane.selectedBackground", Color.WHITE);
            UIManager.put("TabbedPane.underlineColor", PRIMARY);
            UIManager.put("TabbedPane.inactiveUnderlineColor", BORDER);
            UIManager.put("Component.focusColor", PRIMARY);
            UIManager.put("Component.focusedBorderColor", PRIMARY);
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception flatLafMissing) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // keep the default look
            }
        }
    }

    // ---------- text helpers ----------

    public static Font font(int style, float size) {
        return new Font(FONT_NAME, style, 14).deriveFont(style, size);
    }

    public static JLabel label(String text, int style, float size, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font(style, size));
        label.setForeground(color);
        return label;
    }

    public static JLabel title(String text) {
        return label(text, Font.BOLD, 22f, TEXT);
    }

    public static JLabel heading(String text) {
        return label(text, Font.BOLD, 16f, TEXT);
    }

    public static JLabel muted(String text) {
        return label(text, Font.PLAIN, 13f, MUTED);
    }

    public static String money(double value) {
        return String.format(Locale.US, "R %,.2f", value);
    }

    public static String html(String text, int widthPx) {
        String safe = text == null ? "" : text.replace("&", "&amp;").replace("<", "&lt;");
        return "<html><div style='width:" + widthPx + "px'>" + safe + "</div></html>";
    }

    private static final String[] STAR_FONTS =
            {"Segoe UI Symbol", "Apple Symbols", "DejaVu Sans", Font.DIALOG};

    /** A font that can really draw the star characters (Segoe UI cannot, so they showed as empty squares). */
    public static Font starFont(int style, float size) {
        for (String name : STAR_FONTS) {
            Font candidate = new Font(name, style, 14);
            boolean installed = candidate.getFamily().equalsIgnoreCase(name);
            if (installed && candidate.canDisplay('\u2605') && candidate.canDisplay('\u2606')) {
                return candidate.deriveFont(style, size);
            }
        }
        return font(style, size);
    }

    /** A label for star ratings, drawn in a font that has the stars. */
    public static JLabel starLabel(String text, float size, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(starFont(Font.PLAIN, size));
        label.setForeground(color);
        return label;
    }

    public static String stars(int rating) {
        int r = Math.max(0, Math.min(5, rating));
        return "★".repeat(r) + "☆".repeat(5 - r);
    }

    // ---------- panels ----------

    /** A white card with rounded corners and a thin border. */
    public static class RoundedPanel extends JPanel {
        private final int arc;
        private Color fill;
        private Color line = BORDER;

        public RoundedPanel(int arc) {
            this(arc, SURFACE);
        }

        public RoundedPanel(int arc, Color fill) {
            this.arc = arc;
            this.fill = fill;
            setOpaque(false);
            setBorder(new EmptyBorder(14, 16, 14, 16));
        }

        public RoundedPanel borderColor(Color color) {
            this.line = color;
            return this;
        }

        public RoundedPanel fillColor(Color color) {
            this.fill = color;
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(fill);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arc, arc));
            if (line != null) {
                g2.setColor(line);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, arc, arc));
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** A panel that always matches the width of the scroll pane it sits in (no sideways scrolling). */
    public static class ViewportPanel extends JPanel implements Scrollable {
        public ViewportPanel(LayoutManager layout) {
            super(layout);
            setOpaque(false);
        }

        @Override public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
        @Override public int getScrollableUnitIncrement(Rectangle r, int o, int d) { return 24; }
        @Override public int getScrollableBlockIncrement(Rectangle r, int o, int d) { return 120; }
        @Override public boolean getScrollableTracksViewportWidth() { return true; }
        @Override public boolean getScrollableTracksViewportHeight() { return false; }
    }

    // ---------- buttons ----------

    public static class RoundButton extends JButton {
        private final Color base;
        private final Color hover;
        private final Color text;
        private final Color outline;
        private boolean over;

        RoundButton(String label, Color base, Color hover, Color text, Color outline) {
            super(label);
            this.base = base;
            this.hover = hover;
            this.text = text;
            this.outline = outline;
            setForeground(text);
            setFont(font(Font.BOLD, 14f));
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(9, 20, 9, 20));
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    over = true;
                    repaint();
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    over = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color fillColor = !isEnabled() ? new Color(0xC9D3D9) : (over ? hover : base);
            if (fillColor != null) {
                g2.setColor(fillColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14));
            }
            if (outline != null && isEnabled()) {
                g2.setColor(outline);
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth() - 1, getHeight() - 1, 14, 14));
            }
            g2.dispose();
            setForeground(isEnabled() ? text : Color.WHITE);
            super.paintComponent(g);
        }
    }

    public static JButton primary(String label) {
        return new RoundButton(label, PRIMARY, PRIMARY_DARK, Color.WHITE, null);
    }

    public static JButton accent(String label) {
        return new RoundButton(label, ACCENT, ACCENT_DARK, Color.WHITE, null);
    }

    public static JButton ghost(String label) {
        return new RoundButton(label, SURFACE, PRIMARY_LIGHT, PRIMARY_DARK, BORDER);
    }

    public static JButton onDark(String label) {
        return new RoundButton(null, new Color(255, 255, 255, 40), new Color(255, 255, 255, 70),
                Color.WHITE, null) {{ setText(label); }};
    }

    // ---------- badge ----------

    public static class Badge extends JLabel {
        private final Color background;

        public Badge(String text, Color background) {
            super(text);
            this.background = background;
            setForeground(Color.WHITE);
            setFont(font(Font.BOLD, 12f));
            setBorder(new EmptyBorder(3, 10, 3, 10));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(background);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // ---------- tables ----------

    public static JTable table(DefaultTableModel model) {
        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isRowSelected(row)) {
                    c.setBackground(PRIMARY_LIGHT);
                    c.setForeground(TEXT);
                } else {
                    c.setBackground(row % 2 == 0 ? SURFACE : new Color(0xF8FAFB));
                    c.setForeground(TEXT);
                }
                return c;
            }
        };
        table.setRowHeight(36);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        table.setFont(font(Font.PLAIN, 14f));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setFont(font(Font.BOLD, 13f));
        header.setBackground(new Color(0xEEF3F5));
        header.setForeground(MUTED);

        DefaultTableCellRenderer padded = new DefaultTableCellRenderer();
        padded.setBorder(new EmptyBorder(0, 10, 0, 10));
        table.setDefaultRenderer(Object.class, padded);
        return table;
    }

    public static DefaultTableModel readOnlyModel(String... columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public static JScrollPane scroll(Component view) {
        JScrollPane pane = new JScrollPane(view);
        pane.setBorder(BorderFactory.createLineBorder(BORDER));
        pane.getViewport().setBackground(SURFACE);
        return pane;
    }

    // ---------- misc ----------

    public static JTextField field(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(font(Font.PLAIN, 14f));
        field.setMargin(new Insets(6, 8, 6, 8));
        return field;
    }

    public static void error(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Something went wrong", JOptionPane.ERROR_MESSAGE);
    }

    public static String friendly(Throwable t) {
        return t.getMessage() == null ? t.toString() : t.getMessage();
    }
}
