package src.Resource;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main extends JFrame {
    private final MemberManager memberManager = new MemberManager();
    private final CharacterGridPanel characterGrid = new CharacterGridPanel();
    private final JButton resetButton = new JButton("Reset");

    public Main() {
        try {
            initializeUI();
            loadAllMembers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Failed to initialize: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void initializeUI() {
        setTitle("Member Infinity");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        setUndecorated(true);
        setLocationRelativeTo(null);

        // Setup navbar
        add(new CustomNavbar(this), BorderLayout.NORTH);

        // Setup sidebar with filter options
        JPanel sidebarPanel = new SidebarPanel(this::handleFilter);
        configureResetButton();
        sidebarPanel.add(resetButton);
        add(sidebarPanel, BorderLayout.WEST);

        // Setup main content area with scroll
        JScrollPane scrollPane = new JScrollPane(characterGrid);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void configureResetButton() {
        resetButton.addActionListener(e -> {
            characterGrid.resetFilter();
            // Immediately scroll to top-left
            SwingUtilities.invokeLater(() -> {
                JScrollPane scrollPane = getScrollPane();
                scrollPane.getVerticalScrollBar().setValue(0);
                scrollPane.getHorizontalScrollBar().setValue(0);
            });
        });
        resetButton.setBackground(new Color(220, 220, 220));
        resetButton.setFocusPainted(false);
        resetButton.setPreferredSize(new Dimension(100, 30));
    }

    private JScrollPane getScrollPane() {
        return (JScrollPane) ((BorderLayout) getContentPane().getLayout())
            .getLayoutComponent(BorderLayout.CENTER);
    }

    private void loadAllMembers() {
        String[][] membersData = {
            {"Angel", "Angel.png", "President", "Management"},
            {"Kellen", "Kellen.png", "Vice President", "Management"},
            {"Isel", "Isel.png", "Secretary", "Management"},
            {"Apin", "Calvin.png", "Secretary", "Management"},
            {"Deline", "Deline.png", "Treasurer", "Management"},
            {"Ceje", "Ceje.png", "Treasurer", "Management"},
            {"Nikho", "Nikho.png", "Event Coordinator", "Management"},
            {"Lavi", "Lavi.png", "Event Coordinator", "Management"},
            {"Audrey", "Audrey.png", "Media Coordinator", "Visual Communication Design"},
            {"Stella", "Stella.png", "Event Member", "Informatics"},
            {"Abel", "Abel.png", "Event Member", "Informatics"},
            {"Alfain", "Alfain.png", "Event Member", "Management"},
            {"Arya", "Arya.png", "Event Member", "Management"},
            {"Ayesha", "Ayesha.png", "Event Member", "Management"},
            {"Chelsea", "Chelsea.png", "Event Member", "Management"},
            {"Clarice", "Clarice (1).png", "Media Member", "Visual Communication Design"},
            {"Edrick", "Edrick.png", "Event Member", "Management"},
            {"Erika", "Erika.png", "Media Member", "Management"},
            {"Nathan", "Ko Nathan.png", "ACD", "Management"},
            {"Lisa", "Lisa blekping.png", "Media Member", "Visual Communication Design"},
            {"Opet", "opet 6.png", "Media Member", "Management"},
            {"Cia", "Patricia.png", "Event Member", "Management"},
            {"Rafif", "Rafif.png", "Event Member", "Management"},
            {"Regina", "Regina.png", "Media Member", "Management"},
            {"Reski", "Reski.png", "Event Member", "Management"},
            {"Sisi", "Sisi.png", "Event Member", "Management"},
            {"Wellson", "Wellson.png", "Event Member", "Management"}
        };

        for (String[] data : membersData) {
            addMember(data[0], data[1], data[2], data[3]);
        }
    }

    private void addMember(String name, String imageName, String status, String major) {
        try {
            String imagePath = "Image/" + imageName;
            Member member = new Member(name, imagePath, status, major);
            memberManager.addMember(member);
            characterGrid.addMemberCard(new MemberCard(member));
        } catch (Exception e) {
            System.err.println("Error adding member: " + name);
            Member fallbackMember = new Member(name, "", status, major);
            characterGrid.addMemberCard(new MemberCard(fallbackMember));
        }
    }

    private void handleFilter(MemberFilter.FilterCategory category) {
        SwingUtilities.invokeLater(() -> {
            try {
                List<Member> filteredMembers = MemberFilter.filterMembers(
                    memberManager.getMembers(), category);
                characterGrid.updateFilter(filteredMembers);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Filter error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}