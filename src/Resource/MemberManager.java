package src.Resource;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class MemberManager {
    private final List<Member> members = new CopyOnWriteArrayList<>();

    public void addMember(Member member) {
        members.add(member);
    }

    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    public Optional<Member> getMemberByName(String name) {
        return members.stream()
            .filter(m -> m.getName().equalsIgnoreCase(name))
            .findFirst();
    }
}