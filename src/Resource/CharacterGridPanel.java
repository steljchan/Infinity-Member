package src.Resource;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterGridPanel extends JPanel {
    private final List<MemberCard> allCards = new ArrayList<>();
    private final JPanel gridPanel;
    private final static int CARD_WIDTH = 220;
    private final static int CARD_HEIGHT = 280;
    private final static int CARDS_PER_ROW = 4;
    private final static int HORIZONTAL_GAP = 20;
    private final static int VERTICAL_GAP = 20;

    public CharacterGridPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(50, 50, 50));
        
        // Create grid panel with fixed size
        gridPanel = new JPanel(new GridLayout(0, CARDS_PER_ROW, HORIZONTAL_GAP, VERTICAL_GAP));
        gridPanel.setBackground(new Color(50, 50, 50));
        
        // Container panel to maintain fixed size
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(50, 50, 50));
        container.add(gridPanel, BorderLayout.NORTH);
        
        // Wrap in scroll pane
        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // Set fixed viewport size
        scrollPane.setPreferredSize(new Dimension(
            (CARD_WIDTH * CARDS_PER_ROW) + (HORIZONTAL_GAP * (CARDS_PER_ROW + 1)),
            CARD_HEIGHT * 3 // Show about 3 rows by default
        ));
        
        add(scrollPane, BorderLayout.CENTER);
    }

    public void addMemberCard(MemberCard card) {
        card.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        allCards.add(card);
        gridPanel.add(card);
        gridPanel.revalidate();
    }

    public void updateFilter(List<Member> filteredMembers) {
        gridPanel.removeAll();
        
        for (Member member : filteredMembers) {
            MemberCard card = new MemberCard(member);
            card.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            gridPanel.add(card);
        }
        
        // Add empty panels to maintain layout
        int remainingSlots = CARDS_PER_ROW - (filteredMembers.size() % CARDS_PER_ROW);
        if (remainingSlots < CARDS_PER_ROW && filteredMembers.size() > 0) {
            for (int i = 0; i < remainingSlots; i++) {
                gridPanel.add(createEmptyPanel());
            }
        }
        
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JPanel createEmptyPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(50, 50, 50));
        panel.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        return panel;
    }

    public void resetFilter() {
        gridPanel.removeAll();
        for (MemberCard card : allCards) {
            card.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            gridPanel.add(card);
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }
}