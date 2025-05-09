package src.Resource;
import java.util.ArrayList;
import java.util.List;

public class MemberManager {
    private List<Member> members = new ArrayList<>();

    public void addMember(Member member) {
        members.add(member);
    }

    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }
}