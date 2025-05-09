package src.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class CustomNavbar extends JPanel {
    private static final int ICON_SIZE = 30; // Uniform icon size
    private static final int BUTTON_PADDING = 8;
    private static final int TEXT_GAP = 10;

    public CustomNavbar(JFrame parentFrame) {
        setLayout(new BorderLayout());
        setBackground(new Color(40, 40, 40));
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));

        // Title Label
        JLabel title = new JLabel("Member Infinity");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.WEST);

        // Right-side Panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);

        // Action Buttons
        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionButtonsPanel.setOpaque(false);

        actionButtonsPanel.add(createLabeledButton("Have Fun.png", "Have Fun With Us", 
            new Color(100, 150, 255)));
        actionButtonsPanel.add(createLabeledButton("Contact Us.png", "Contact Us", 
            new Color(120, 255, 180)));

        rightPanel.add(actionButtonsPanel, BorderLayout.CENTER);
        rightPanel.add(new WindowControlPanel(parentFrame), BorderLayout.EAST);
        add(rightPanel, BorderLayout.EAST);
    }

    private JButton createLabeledButton(String iconPath, String labelText, Color bgColor) {
        // Load and scale icon
        ImageIcon icon = loadAndScaleIcon("/Resource/" + iconPath, ICON_SIZE, ICON_SIZE);
        
        // Create button with consistent styling
        JButton button = new JButton(labelText, icon);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setOpaque(true);
        
        // Layout settings
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setIconTextGap(TEXT_GAP);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1),
            BorderFactory.createEmptyBorder(BUTTON_PADDING, BUTTON_PADDING*2, BUTTON_PADDING, BUTTON_PADDING*2)
        ));

        // Hover effects
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        // Action
        button.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, labelText, "Info", JOptionPane.INFORMATION_MESSAGE));

        return button;
    }

    private ImageIcon loadAndScaleIcon(String path, int width, int height) {
        try {
            // Load original icon
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(path));
            if (originalIcon.getImage() == null) {
                return createPlaceholderIcon(width, height);
            }
            
            // Create high-quality scaled version
            Image scaled = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = buffered.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.drawImage(scaled, 0, 0, null);
            g2d.dispose();
            
            return new ImageIcon(buffered);
        } catch (Exception e) {
            System.err.println("Error loading icon: " + path);
            return createPlaceholderIcon(width, height);
        }
    }

    private ImageIcon createPlaceholderIcon(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
        return new ImageIcon(img);
    }
}