package src.Resource;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MemberCard extends JPanel {
    // Constants for styling
    private static final int IMAGE_SIZE = 200;
    private static final int CARD_WIDTH = 220;
    private static final int CARD_HEIGHT = 280;
    private static final Color BORDER_COLOR = new Color(230, 230, 230);
    private static final Color HOVER_BORDER_COLOR = new Color(100, 150, 255);
    private static final Color TEXT_COLOR = new Color(60, 60, 60);
    private static final Font NAME_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font STATUS_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final int TEXT_AREA_WIDTH = 180;

    // Image cache to improve performance
    private static final Map<String, ImageIcon> IMAGE_CACHE = new HashMap<>();

    private final Member member;

    public MemberCard(Member member) {
        this.member = member;
        initUI();
        setupInteractions();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(createCardBorder(false));
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));

        add(createImageLabel(), BorderLayout.CENTER);
        add(createTextPanel(), BorderLayout.SOUTH);
    }

    private void setupInteractions() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMemberDetailsDialog();
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
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );
    }

    private void showMemberDetailsDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Detail Anggota");
        dialog.setModal(true);
        dialog.setSize(350, 400);
        dialog.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        try {
            ImageIcon icon = loadCachedImage(member.getImagePath());
            if (icon != null) {
                JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage()
                    .getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
                imagePanel.add(imageLabel);
            }
        } catch (IOException e) {
            JLabel noImageLabel = new JLabel("No Image Available");
            imagePanel.add(noImageLabel);
        }

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel nameLabel = createLabel(member.getName(), NAME_FONT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel statusLabel = createLabel("Jabatan: " + member.getStatus(), STATUS_FONT);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel majorLabel = createLabel("Jurusan: " + member.getMajor(), STATUS_FONT);
        majorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(imagePanel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(nameLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(statusLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(majorLabel);

        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Tutup");
        closeButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(closeButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private JPanel createTextPanel() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
    
        // Nama
        JLabel nameLabel = createLabel(member.getName(), NAME_FONT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Jabatan/Status
        JLabel statusLabel = createLabel(member.getStatus(), STATUS_FONT);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Tambahkan ke panel
        textPanel.add(Box.createVerticalStrut(10)); // Jarak atas
        textPanel.add(nameLabel);
        textPanel.add(Box.createVerticalStrut(5));  // Jarak antara nama dan status
        textPanel.add(statusLabel);
    
        return textPanel;
    }    

    private JLabel createImageLabel() {
        JLabel imageLabel = new JLabel("", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(IMAGE_SIZE, IMAGE_SIZE));

        try {
            ImageIcon icon = loadCachedImage(member.getImagePath());
            if (icon != null) {
                imageLabel.setIcon(icon);
            } else {
                imageLabel.setText("No Image");
                imageLabel.setForeground(Color.GRAY);
                imageLabel.setHorizontalTextPosition(SwingConstants.CENTER);
                imageLabel.setVerticalTextPosition(SwingConstants.CENTER);
            }
        } catch (Exception e) {
            imageLabel.setText("Image Error");
            imageLabel.setForeground(Color.RED);
            imageLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            imageLabel.setVerticalTextPosition(SwingConstants.CENTER);
        }

        return imageLabel;
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private ImageIcon loadCachedImage(String imagePath) throws IOException {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }

        return IMAGE_CACHE.computeIfAbsent(imagePath, path -> {
            try {
                BufferedImage originalImage = ImageIO.read(new File(path));
                if (originalImage != null) {
                    BufferedImage scaledImage = new BufferedImage(
                        IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = scaledImage.createGraphics();
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.drawImage(originalImage.getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH), 0, 0, null);
                    g2d.dispose();
                    return new ImageIcon(scaledImage);
                }
            } catch (IOException e) {
                System.err.println("Error loading image: " + path);
            }
            return null;
        });
    }

    public Member getMember() {
        return member;
    }
}
