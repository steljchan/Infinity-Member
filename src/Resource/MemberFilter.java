package src.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MemberFilter {

    public enum FilterCategory {
        A_TO_Z,
        IMT,
        VCD,
        MAN,
        HOD,
        KOOR,
        MEDIA,
        ACD,
        MALE,
        FEMALE
    }

    public static List<Member> filterMembers(List<Member> members, FilterCategory category) {
        List<Member> filtered = new ArrayList<>();

        switch (category) {
            case A_TO_Z:
                filtered.addAll(members);
                Collections.sort(filtered, Comparator.comparing(m -> m.getName().toLowerCase()));
                break;
                
            case IMT:
                filtered.addAll(filterByNames(members, "Abel", "Stella"));
                break;
                
            case VCD:
                filtered.addAll(filterByNames(members, "Clarice", "Lisa", "Audrey"));
                break;
                
            case MAN:
                filtered.addAll(getRemainingMembers(members, 
                    List.of("Abel", "Stella", "Clarice", "Lisa", "Audrey")));
                break;
                
            case HOD:
                filtered.addAll(filterByNames(members, 
                    "Angel", "Kellen", "Isel", "Apin", "Deline", "Ceje"));
                break;
                
            case KOOR:
                filtered.addAll(filterByNames(members, "Lavi", "Nikho", "Audrey"));
                break;
                
            case MEDIA:
                filtered.addAll(filterByNames(members, 
                    "Opet", "Erika", "Regina", "Clarice", "Audrey", "Lisa"));
                break;
                
            case ACD:
                filtered.addAll(filterByNames(members, "Nathan"));
                // Add event members if any
                break;
                
            case MALE:
                filtered.addAll(filterByNames(members, 
                    "Alfain", "Abel", "Apin", "Kellen", "Wellson", 
                    "Rafif", "Reski", "Edrick", "Arya", "Nathan"));
                break;
                
            case FEMALE:
                List<String> maleNames = List.of(
                    "Alfain", "Abel", "Apin", "Kellen", "Wellson", 
                    "Rafif", "Reski", "Edrick", "Arya", "Nathan");
                filtered.addAll(getRemainingMembers(members, maleNames));
                break;
        }

        return filtered;
    }

    private static List<Member> filterByNames(List<Member> members, String... names) {
        List<Member> result = new ArrayList<>();
        for (Member member : members) {
            for (String name : names) {
                if (member.getName().equalsIgnoreCase(name)) {
                    result.add(member);
                    break;
                }
            }
        }
        return result;
    }

    private static List<Member> getRemainingMembers(List<Member> members, List<String> excludedNames) {
        List<Member> result = new ArrayList<>();
        for (Member member : members) {
            boolean isExcluded = false;
            for (String name : excludedNames) {
                if (member.getName().equalsIgnoreCase(name)) {
                    isExcluded = true;
                    break;
                }
            }
            if (!isExcluded) {
                result.add(member);
            }
        }
        return result;
    }
}