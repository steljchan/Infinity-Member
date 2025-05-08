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
        
        actionButtonsPanel.add(createActionButton("Have Fun", "🎉", "Join our events!"));
        actionButtonsPanel.add(createActionButton("Contact", "✉️", "Email: contact@infinity.com"));
        
        rightPanel.add(actionButtonsPanel, BorderLayout.CENTER);
        rightPanel.add(new WindowControlPanel(parentFrame), BorderLayout.EAST);
        add(rightPanel, BorderLayout.EAST);
    }

    private JButton createActionButton(String text, String icon, String message) {
        JButton button = new JButton(text + " " + icon);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, message, text, JOptionPane.INFORMATION_MESSAGE));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(200, 230, 255));
            }
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
        });
        return button;
    }
}