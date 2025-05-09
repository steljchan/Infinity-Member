package src.Resource;
import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {
    public SidebarPanel() {
        setBackground(new Color(40, 40, 40));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        setPreferredSize(new Dimension(200, Integer.MAX_VALUE));
        
        String[] buttonLabels = {"A-Z", "IMT", "VCD", "MAN", "HOD", "Koor", 
                               "Event", "Media", "ACD", "Laki-laki", "Perempuan"};
        
        for (String label : buttonLabels) {
            add(createSidebarButton(label));
            add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }

    private JButton createSidebarButton(String label) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setPreferredSize(new Dimension(180, 40));
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }
}