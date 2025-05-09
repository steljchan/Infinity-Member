package src.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class MemberCard extends JPanel {
    private static final int IMAGE_SIZE = 200;
    private static final Color BORDER_COLOR = new Color(230, 230, 230);
    private static final Color TEXT_COLOR = new Color(60, 60, 60);
    private static final Font NAME_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font STATUS_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public MemberCard(Member member) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        setPreferredSize(new Dimension(IMAGE_SIZE + 30, IMAGE_SIZE + 80));

        // Image loading with better error handling
        JLabel imageLabel = loadMemberImage(member);
        add(imageLabel);
        
        // Text components with consistent styling
        add(Box.createVerticalStrut(15));
        add(createTextLabel(member.getName(), NAME_FONT));
        add(Box.createVerticalStrut(5));
        add(createTextLabel(member.getStatus(), STATUS_FONT));
    }

    private JLabel loadMemberImage(Member member) {
        JLabel imageLabel = new JLabel("No Image", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(IMAGE_SIZE, IMAGE_SIZE));
        
        try {
            File imageFile = new File(member.getImagePath());
            if (imageFile.exists()) {
                BufferedImage originalImage = ImageIO.read(imageFile);
                if (originalImage != null) {
                    Image scaledImage = originalImage.getScaledInstance(
                        IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
                    
                    // Create high-quality scaled image
                    BufferedImage highQualityImage = new BufferedImage(
                        IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = highQualityImage.createGraphics();
                    g2d.setRenderingHint(
                        RenderingHints.KEY_INTERPOLATION, 
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.drawImage(scaledImage, 0, 0, null);
                    g2d.dispose();
                    
                    imageLabel.setIcon(new ImageIcon(highQualityImage));
                    imageLabel.setText("");
                }
            }
        } catch (IOException e) {
            imageLabel.setText("Image Error");
            imageLabel.setForeground(Color.RED);
        }
        
        return imageLabel;
    }

    private JLabel createTextLabel(String text, Font font) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}