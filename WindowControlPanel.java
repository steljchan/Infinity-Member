import javax.swing.*;
import javax.swing.BorderFactory;
import java.awt.*;
import java.awt.event.*;

public class WindowControlPanel extends JPanel {
    private JButton maximizeBtn;

    public WindowControlPanel(JFrame parentFrame) {
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        // Minimize button
        add(new ControlButton("—", new Color(50, 50, 50), e -> 
            parentFrame.setState(Frame.ICONIFIED)));
        
        // Maximize/Restore button
        maximizeBtn = new ControlButton("□", new Color(50, 50, 50), e -> {
            toggleMaximize(parentFrame);
            updateMaximizeButton(parentFrame);
        });
        add(maximizeBtn);
        
        // Close button
        add(new ControlButton("×", new Color(200, 50, 50), e -> 
            System.exit(0)));
    }

    private void toggleMaximize(JFrame frame) {
        if ((frame.getExtendedState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
            frame.setExtendedState(Frame.NORMAL);
        } else {
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        }
    }

    private void updateMaximizeButton(JFrame frame) {
        if ((frame.getExtendedState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) {
            maximizeBtn.setText("❐");
        } else {
            maximizeBtn.setText("□");
        }
    }

    // Inner class untuk tombol kontrol
    static class ControlButton extends JButton {
        public ControlButton(String text, Color bgColor, ActionListener action) {
            super(text);
            configureButton(bgColor);
            addActionListener(action);
            setupHoverEffect(bgColor);
        }

        private void configureButton(Color bgColor) {
            setFocusable(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setOpaque(true);
            setBackground(bgColor);
            setPreferredSize(new Dimension(30, 25));
        }

        private void setupHoverEffect(Color bgColor) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(new Color(
                        Math.min(bgColor.getRed() + 30, 255),
                        Math.min(bgColor.getGreen() + 30, 255),
                        Math.min(bgColor.getBlue() + 30, 255)
                    ));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(bgColor);
                }
            });
        }
    }
}