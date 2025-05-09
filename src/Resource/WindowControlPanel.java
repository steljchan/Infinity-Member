package src.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class WindowControlPanel extends JPanel {
    private static final int BUTTON_SIZE = 30;
    private static final Color HOVER_OFFSET = new Color(30, 30, 30);
    
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

    private ControlButton createControlButton(String iconPath, Color bgColor, ActionListener action) {
        ImageIcon icon = loadScaledIcon("/Resource/" + iconPath, BUTTON_SIZE - 10);
        return new ControlButton(icon, bgColor, action);
    }

    private ImageIcon loadScaledIcon(String path, int size) {
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(path));
            if (originalIcon.getImage() == null) {
                return createPlaceholderIcon(size);
            }
            
            Image scaled = originalIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            BufferedImage buffered = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = buffered.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.drawImage(scaled, 0, 0, null);
            g2d.dispose();
            
            return new ImageIcon(buffered);
        } catch (Exception e) {
            System.err.println("Error loading icon: " + path);
            return createPlaceholderIcon(size);
        }
    }

    private ImageIcon createPlaceholderIcon(int size) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, size, size);
        g2d.dispose();
        return new ImageIcon(img);
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
        // Update icon if needed (you can add different icons for maximize/restore)
        ImageIcon icon = loadScaledIcon("/Resource/Max.png", BUTTON_SIZE - 10);
        maximizeBtn.setIcon(icon);
    }

    static class ControlButton extends JButton {
        private final Color originalBgColor;

        public ControlButton(ImageIcon icon, Color bgColor, ActionListener action) {
            super(icon);
            this.originalBgColor = bgColor;
            configureButton();
            setupHoverEffects();
            addActionListener(action);
        }

        private void configureButton() {
            setFocusable(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(true);
            setBackground(originalBgColor);
            setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
            
            // Center icon
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
        }

        private void setupHoverEffects() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(new Color(
                        Math.min(originalBgColor.getRed() + HOVER_OFFSET.getRed(), 255),
                        Math.min(originalBgColor.getGreen() + HOVER_OFFSET.getGreen(), 255),
                        Math.min(originalBgColor.getBlue() + HOVER_OFFSET.getBlue(), 255)
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(originalBgColor);
                }
            });
        }
    }
}