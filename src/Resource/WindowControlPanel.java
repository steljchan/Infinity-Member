package src.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowControlPanel extends JPanel {
    private JButton maximizeBtn;
    private Rectangle normalBounds;
    private final JFrame parentFrame;

    public WindowControlPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        // Minimize button
        add(createControlButton("Min.png", new Color(50, 50, 50), 
            e -> parentFrame.setState(Frame.ICONIFIED)));

        // Maximize/Restore button
        maximizeBtn = createControlButton("Max.png", new Color(50, 50, 50), 
            e -> toggleMaximize());
        add(maximizeBtn);

        // Close button
        add(createControlButton("Close.png", new Color(200, 50, 50), 
            e -> parentFrame.dispatchEvent(new WindowEvent(parentFrame, WindowEvent.WINDOW_CLOSING))));
    }

    private JButton createControlButton(String iconPath, Color bgColor, ActionListener action) {
        JButton button = new JButton();
        
        // Set icon
        ImageIcon icon = loadAndResizeIcon(iconPath);
        if (icon != null) {
            button.setIcon(icon);
        } else {
            button.setText(iconPath.replace(".png", "")); // Fallback text
        }

        // Button styling
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(bgColor);
        button.setPreferredSize(new Dimension(30, 25));
        button.addActionListener(action);

        // Hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(
                    Math.min(bgColor.getRed() + 30, 255),
                    Math.min(bgColor.getGreen() + 30, 255),
                    Math.min(bgColor.getBlue() + 30, 255)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private ImageIcon loadAndResizeIcon(String iconPath) {
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath));
            if (originalIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Icon not found: " + iconPath);
                return null;
            }
            
            // Resize to fit button (16x16 is good for 30x25 button)
            Image scaledImage = originalIcon.getImage()
                .getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Error loading icon: " + e.getMessage());
            return null;
        }
    }

    private void toggleMaximize() {
        if ((parentFrame.getExtendedState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
            parentFrame.setExtendedState(Frame.NORMAL);
            if (normalBounds != null) {
                parentFrame.setBounds(normalBounds);
            }
        } else {
            normalBounds = parentFrame.getBounds();
            parentFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        }
        updateMaximizeButton();
    }

    private void updateMaximizeButton() {
        String iconPath = (parentFrame.getExtendedState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH
            ? "Restore.png" : "Max.png";
        
        ImageIcon icon = loadAndResizeIcon(iconPath);
        if (icon != null) {
            maximizeBtn.setIcon(icon);
        }
    }
}