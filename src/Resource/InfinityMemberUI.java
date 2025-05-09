package src.Resource;

import javax.swing.*;
import java.awt.*;

public class InfinityMemberUI extends JFrame {
    private MemberManager memberManager;
    private JPanel characterGrid;

    public InfinityMemberUI() {
        initializeUI();
        memberManager = new MemberManager();
        loadAllMembers();
    }

    private void initializeUI() {
        setTitle("Member Infinity");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        setUndecorated(true);

        characterGrid = new CharacterGridPanel();
        add(new CustomNavbar(this), BorderLayout.NORTH);
        add(new SidebarPanel(), BorderLayout.WEST);
        add(new JScrollPane(characterGrid), BorderLayout.CENTER);
    }

    private void loadAllMembers() {
        String basePath = "Image/"; // Path relatif ke folder Image
        
        String[][] members = {
            {"Angel", "Angel.png", "President"},
            {"Kellen", "Kellen.png", "Vice President"},
            {"Isel", "Isel.png", "Sekretaris"},
            {"Apin", "Calvin.png", "Sekretaris"},
            {"Deline", "Deline.png", "Bendahara"},
            {"Ceje", "Ceje.png", "Bendahara"},
            {"Nikho", "Nikho.png", "Koor Event"},
            {"Lavi", "Lavi.png", "Koor Event"},
            {"Audrey", "Audrey.png", "Koor Media"},
            {"Stella", "Stella.png", "Anggota Event"},
            {"Abel", "Abel.png", "Anggota Event"},
            {"Alfain", "Alfain.png", "Anggota Event"},
            {"Arya", "Arya.png", "Anggota Event"},
            {"Ayesha", "Ayesha.png", "Anggota Event"},
            {"Chelsea", "Chelsea.png", "Anggota Event"},
            {"Clarice", "Clarice (1).png", "Anggota Media"},
            {"Edrick", "Edrick.png", "Anggota Event"},
            {"Erika", "Erika.png", "Anggota Media"},
            {"Nathan", "Ko Nathan.png", "ACD"},
            {"Lisa", "Lisa blekping.png", "Anggota Media"},
            {"Opet", "opet 6.png", "Anggota Media"},
            {"Cia", "Patricia.png", "Anggota Event"},
            {"Rafif", "Rafif.png", "Anggota Event"},
            {"Regina", "Regina.png", "Anggota Media"},
            {"Reski", "Reski.png", "Anggota Event"},
            {"Sisi", "Sisi.png", "Anggota Event"},
            {"Wellson", "Wellson.png", "Anggota Event"}
        };

        for (String[] member : members) {
            addMember(member[0], basePath + member[1], member[2]);
        }
    }

    public void addMember(String name, String imagePath, String status) {
        try {
            Member newMember = new Member(name, imagePath, status);
            memberManager.addMember(newMember);
            characterGrid.add(new MemberCard(newMember));
            characterGrid.revalidate();
        } catch (Exception e) {
            System.err.println("Error loading member: " + name);
            characterGrid.add(new MemberCard(new Member(name, "", status)));
        }
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            InfinityMemberUI ui = new InfinityMemberUI();
            ui.setLocationRelativeTo(null);
            ui.setVisible(true);
        });
    }
}