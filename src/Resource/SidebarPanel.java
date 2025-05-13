package src.Resource;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class SidebarPanel extends JPanel {
    public SidebarPanel(Consumer<MemberFilter.FilterCategory> filterAction) {
        setBackground(new Color(40, 40, 40));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Daftar button filter
        String[] buttonLabels = {
            "IMT", "VCD", "MAN", "HOD", 
            "KOOR", "EVENT", "MEDIA", "ACD",
            "LAKI-LAKI", "PEREMPUAN"
        };

        MemberFilter.FilterCategory[] categories = {
            MemberFilter.FilterCategory.IMT,
            MemberFilter.FilterCategory.VCD,
            MemberFilter.FilterCategory.MAN,
            MemberFilter.FilterCategory.HOD,
            MemberFilter.FilterCategory.KOOR,
            MemberFilter.FilterCategory.EVENT,
            MemberFilter.FilterCategory.MEDIA,
            MemberFilter.FilterCategory.ACD,
            MemberFilter.FilterCategory.MALE,
            MemberFilter.FilterCategory.FEMALE
        };

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = createFilterButton(buttonLabels[i], categories[i], filterAction);
            add(button);
            add(Box.createVerticalStrut(10));
        }
    }

    private JButton createFilterButton(String label, MemberFilter.FilterCategory category, 
                                     Consumer<MemberFilter.FilterCategory> action) {
        JButton button = new JButton(label);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        
        button.addActionListener(e -> {
            action.accept(category);
            button.setBackground(new Color(0, 80, 180)); // Feedback visual
            Timer timer = new Timer(200, ev -> {
                button.setBackground(new Color(0, 120, 215));
                ((Timer)ev.getSource()).stop();
            });
            timer.start();
        });
        
        return button;
    }
}