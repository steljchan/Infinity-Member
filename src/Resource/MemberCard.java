// File: src/Resource/MemberCard.java
package src.Resource;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

public class MemberCard extends JPanel {
    private static final int CARD_WIDTH = 220;
    private static final int CARD_HEIGHT = 320;
    private static final int IMAGE_WIDTH = 150;
    private static final int IMAGE_HEIGHT = 180;
    private static final Color BORDER_COLOR = new Color(230, 230, 230);
    private static final Color HOVER_BORDER_COLOR = new Color(100, 150, 255);
    private static final Color TEXT_COLOR = new Color(60, 60, 60);
    private static final Font NAME_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font STATUS_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private static final Map<String, ImageIcon> IMAGE_CACHE = new HashMap<>();
    private final Member member;

    public MemberCard(Member member) {
        this.member = member;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setMaximumSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setMinimumSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setBackground(Color.WHITE);
        setBorder(createCardBorder(false));
        setAlignmentY(Component.TOP_ALIGNMENT);

        add(Box.createVerticalStrut(10));
        add(createImageLabel());
        add(Box.createVerticalStrut(10));
        add(createTextPanel());
        add(Box.createVerticalGlue());

        if (!isEmptyCard()) {
            setupInteractions();
        }
    }

    private void setupInteractions() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMemberDetails();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setBorder(createCardBorder(true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                setBorder(createCardBorder(false));
            }
        });
    }

    private Border createCardBorder(boolean hover) {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(hover ? HOVER_BORDER_COLOR : BORDER_COLOR, hover ? 2 : 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }

    private JLabel createImageLabel() {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        label.setMaximumSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (!isEmptyCard()) {
            try {
                ImageIcon icon = loadCachedImage(member.getImagePath());
                if (icon != null) {
                    Image scaled = icon.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(scaled));
                } else {
                    label.setText("No Image");
                    label.setForeground(Color.GRAY);
                }
            } catch (Exception e) {
                label.setText("Image Error");
                label.setForeground(Color.RED);
            }
        }
        return label;
    }

    private JPanel createTextPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (!isEmptyCard()) {
            JLabel nameLabel = new JLabel(member.getName(), SwingConstants.CENTER);
            nameLabel.setFont(NAME_FONT);
            nameLabel.setForeground(TEXT_COLOR);
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel statusLabel = new JLabel(member.getStatus(), SwingConstants.CENTER);
            statusLabel.setFont(STATUS_FONT);
            statusLabel.setForeground(TEXT_COLOR);
            statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(nameLabel);
            panel.add(Box.createVerticalStrut(5));
            panel.add(statusLabel);
        }

        return panel;
    }

    private void showMemberDetails() {
        if (isEmptyCard()) return;

        JDialog dialog = new JDialog();
        dialog.setTitle("Member Details");
        dialog.setModal(true);
        dialog.setSize(350, 400);
        dialog.setLayout(new BorderLayout());

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        try {
            ImageIcon icon = loadCachedImage(member.getImagePath());
            if (icon != null) {
                imagePanel.add(new JLabel(new ImageIcon(icon.getImage()
                    .getScaledInstance(200, 200, Image.SCALE_SMOOTH))));
            }
        } catch (IOException e) {
            imagePanel.add(new JLabel("No Image Available"));
        }

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        detailPanel.add(createDetailLabel("Name: " + member.getName(), NAME_FONT));
        detailPanel.add(Box.createVerticalStrut(10));
        detailPanel.add(createDetailLabel("Position: " + member.getStatus(), STATUS_FONT));
        detailPanel.add(Box.createVerticalStrut(5));
        detailPanel.add(createDetailLabel("Major: " + member.getMajor(), STATUS_FONT));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(imagePanel);
        content.add(detailPanel);

        dialog.add(new JScrollPane(content), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton, BorderLayout.SOUTH);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JLabel createDetailLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private ImageIcon loadCachedImage(String path) throws IOException {
        if (path == null || path.isEmpty()) return null;

        return IMAGE_CACHE.computeIfAbsent(path, p -> {
            try {
                BufferedImage img = ImageIO.read(new File(p));
                if (img != null) {
                    BufferedImage scaled = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = scaled.createGraphics();
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.drawImage(img.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH), 0, 0, null);
                    g2d.dispose();
                    return new ImageIcon(scaled);
                }
            } catch (IOException e) {
                System.err.println("Error loading image: " + p);
            }
            return null;
        });
    }

    private boolean isEmptyCard() {
        return member == null || member.getName() == null || member.getName().isEmpty();
    }

    public Member getMember() {
        return member;
    }
}