package src.Resource;

import javax.swing.*;
import java.awt.*;

public class MemberDetailDialog extends JDialog {
    public MemberDetailDialog(JFrame parent, Member member) {
        super(parent, "Detail Anggota", true);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel nameLabel = new JLabel("Nama: " + member.getName());
        JLabel statusLabel = new JLabel("Jabatan: " + member.getStatus());
        JLabel majorLabel = new JLabel("Jurusan: " + member.getMajor());

        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        majorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        contentPanel.add(nameLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(statusLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(majorLabel);

        add(contentPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Tutup");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }
}