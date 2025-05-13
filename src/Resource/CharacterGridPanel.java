package src.Resource;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class CharacterGridPanel extends JPanel {
    private final List<MemberCard> allCards = new ArrayList<>();

    public CharacterGridPanel() {
        setLayout(new GridLayout(0, 4, 20, 20));
        setBackground(new Color(50, 50, 50));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public void addMemberCard(MemberCard card) {
        allCards.add(card);
        add(card);
        revalidate();
    }

    public void updateFilter(List<Member> filteredMembers) {
        // Clear current filter
        for (MemberCard card : allCards) {
            card.setVisible(false);
        }
        
        // Show only filtered members
        Set<String> filteredNames = new HashSet<>();
        for (Member member : filteredMembers) {
            filteredNames.add(member.getName().toLowerCase());
        }
        
        for (MemberCard card : allCards) {
            if (filteredNames.contains(card.getMember().getName().toLowerCase())) {
                card.setVisible(true);
            }
        }
        
        revalidate();
        repaint();
    }
}