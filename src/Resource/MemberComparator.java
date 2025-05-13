package src.Resource;

import java.util.Comparator;

public class MemberComparator implements Comparator<Member> {
    @Override
    public int compare(Member m1, Member m2) {
        return m1.getName().compareToIgnoreCase(m2.getName());
    }
}