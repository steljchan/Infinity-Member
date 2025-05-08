import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class MemberCard extends JPanel {
    private static final int IMAGE_SIZE = 200;
    
    public MemberCard(Member member) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        try {
            BufferedImage originalImage = ImageIO.read(new File(member.getImagePath()));
            Image scaledImage = originalImage.getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
            BufferedImage highQualityImage = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = highQualityImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();
            
            JLabel photoLabel = new JLabel(new ImageIcon(highQualityImage));
            photoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(photoLabel);
        } catch (IOException e) {
            JLabel errorLabel = new JLabel("Image not found");
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(errorLabel);
        }
        
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(createLabel(member.getName(), 16, Font.BOLD, new Color(50, 50, 50)));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(createLabel(member.getStatus(), 12, Font.PLAIN, new Color(120, 120, 120)));
    }

    private JLabel createLabel(String text, int size, int style, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", style, size));
        label.setForeground(color);
        return label;
    }
}