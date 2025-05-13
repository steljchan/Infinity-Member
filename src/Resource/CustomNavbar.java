package src.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomNavbar extends JPanel {
    public CustomNavbar(JFrame parentFrame) {
        setLayout(new BorderLayout());
        setBackground(new Color(40, 40, 40));
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));

        JLabel title = new JLabel("Member Infinity");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);

        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionButtonsPanel.setOpaque(false);

        actionButtonsPanel.add(createLabeledButton("Have Fun.png", "Have Fun With Us", new Color(100, 150, 255)));
        actionButtonsPanel.add(createLabeledButton("Contact Us.png", "Contact Us", new Color(120, 255, 180)));

        rightPanel.add(actionButtonsPanel, BorderLayout.CENTER);
        rightPanel.add(new WindowControlPanel(parentFrame), BorderLayout.EAST);
        add(rightPanel, BorderLayout.EAST);
    }

    private JButton createLabeledButton(String iconPath, String labelText, Color bgColor) {
        // Load the original icon
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath));
        
        // Resize the icon while maintaining aspect ratio
        int iconHeight = 20; // Set desired height
        int iconWidth = (originalIcon.getIconWidth() * iconHeight) / originalIcon.getIconHeight();
        Image scaledImage = originalIcon.getImage().getScaledInstance(
            iconWidth, iconHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        JButton button = new JButton(labelText, scaledIcon);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(10);

        button.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, labelText, "Info", JOptionPane.INFORMATION_MESSAGE));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}