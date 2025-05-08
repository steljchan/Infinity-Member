import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControlButton extends JButton {
    public ControlButton(String text, Color bgColor, ActionListener action) {
        super(text);
        setFocusable(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 16));
        setOpaque(true);
        setBackground(bgColor);
        setPreferredSize(new Dimension(40, 25));
        
        if (action != null) addActionListener(action);

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                setBackground(getBackground().brighter());
            }
            public void mouseExited(MouseEvent evt) {
                setBackground(bgColor);
            }
        });
    }
} 