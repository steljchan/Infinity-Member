import javax.swing.*;
import java.awt.*;

public class CharacterGridPanel extends JPanel {
    public CharacterGridPanel() {
        setLayout(new GridLayout(0, 4, 20, 20));
        setBackground(new Color(50, 50, 50));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }
}