package src.Resource;

import java.util.*;
import java.util.stream.Collectors;

public class MemberFilter {
    public enum FilterCategory {
        IMT, VCD, MAN, HOD, KOOR, EVENT, MEDIA, ACD, MALE, FEMALE
    }

    // Immutable mapping of filter categories to member names
    private static final Map<FilterCategory, Set<String>> FILTER_MAPPING;
    
    static {
        Map<FilterCategory, Set<String>> mapping = new EnumMap<>(FilterCategory.class);
        mapping.put(FilterCategory.IMT, Collections.unmodifiableSet(new HashSet<>(Arrays.asList("Abel", "Stella"))));
        mapping.put(FilterCategory.VCD, Collections.unmodifiableSet(new HashSet<>(Arrays.asList("Clarice", "Lisa", "Audrey"))));
        mapping.put(FilterCategory.HOD, Collections.unmodifiableSet(new HashSet<>(Arrays.asList("Angel", "Kellen", "Isel", "Apin", "Deline", "Ceje"))));
        mapping.put(FilterCategory.KOOR, Collections.unmodifiableSet(new HashSet<>(Arrays.asList("Lavi", "Nikho", "Audrey"))));
        mapping.put(FilterCategory.MEDIA, Collections.unmodifiableSet(new HashSet<>(Arrays.asList("Opet", "Erika", "Regina", "Clarice", "Audrey", "Lisa"))));
        mapping.put(FilterCategory.ACD, Collections.unmodifiableSet(Collections.singleton("Nathan")));
        mapping.put(FilterCategory.EVENT, Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "Nikho", "Lavi", "Stella", "Abel", "Alfain", "Arya",
            "Ayesha", "Chelsea", "Edrick", "Cia", "Rafif",
            "Reski", "Sisi", "Wellson"))));
        mapping.put(FilterCategory.MALE, Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "Alfain", "Abel", "Apin", "Kellen", "Wellson",
            "Rafif", "Reski", "Edrick", "Arya", "Nathan", "Nikho", "Opet"))));
        
        FILTER_MAPPING = Collections.unmodifiableMap(mapping);
    }

    // Precomputed sets for optimization
    private static final Set<String> MALE_MEMBERS = FILTER_MAPPING.get(FilterCategory.MALE);
    private static final Set<String> IMT_MEMBERS = FILTER_MAPPING.get(FilterCategory.IMT);
    private static final Set<String> VCD_MEMBERS = FILTER_MAPPING.get(FilterCategory.VCD);

    public static List<Member> filterMembers(List<Member> members, FilterCategory category) {
        if (members == null || members.isEmpty()) {
            return Collections.emptyList();
        }

        switch (category) {
            case FEMALE:
                return filterFemaleMembers(members);
            case MAN:
                return filterManagementMembers(members);
            default:
                return filterByCategory(members, category);
        }
    }

    private static List<Member> filterFemaleMembers(List<Member> members) {
        return members.stream()
                .filter(m -> !MALE_MEMBERS.contains(m.getName()))
                .collect(Collectors.toList());
    }

    private static List<Member> filterManagementMembers(List<Member> members) {
        Set<String> excludedNames = new HashSet<>();
        excludedNames.addAll(IMT_MEMBERS);
        excludedNames.addAll(VCD_MEMBERS);
        
        return members.stream()
                .filter(m -> !excludedNames.contains(m.getName()))
                .collect(Collectors.toList());
    }

    private static List<Member> filterByCategory(List<Member> members, FilterCategory category) {
        Set<String> names = FILTER_MAPPING.getOrDefault(category, Collections.emptySet());
        return members.stream()
                .filter(m -> names.contains(m.getName()))
                .collect(Collectors.toList());
    }

    // Utility method to get all categories (optional)
    public static List<FilterCategory> getAllCategories() {
        return Arrays.asList(FilterCategory.values());
    }
}