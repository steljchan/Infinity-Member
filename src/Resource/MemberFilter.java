package src.Resource;

import java.util.*;
import java.util.stream.Collectors;

public class MemberFilter {
    public enum FilterCategory {
        IMT, VCD, MAN, HOD, KOOR, EVENT, MEDIA, ACD, MALE, FEMALE
    }

    private static final Map<FilterCategory, Set<String>> FILTER_MAPPING = Map.ofEntries(
        Map.entry(FilterCategory.IMT, Set.of("Abel", "Stella")),
        Map.entry(FilterCategory.VCD, Set.of("Clarice", "Lisa", "Audrey")),
        Map.entry(FilterCategory.HOD, Set.of("Angel", "Kellen", "Isel", "Apin", "Deline", "Ceje")),
        Map.entry(FilterCategory.KOOR, Set.of("Lavi", "Nikho", "Audrey")),
        Map.entry(FilterCategory.MEDIA, Set.of("Opet", "Erika", "Regina", "Clarice", "Audrey", "Lisa")),
        Map.entry(FilterCategory.ACD, Set.of("Nathan")),
        Map.entry(FilterCategory.EVENT, Set.of(
            "Nikho", "Lavi", "Stella", "Abel", "Alfain", "Arya",
            "Ayesha", "Chelsea", "Edrick", "Cia", "Rafif",
            "Reski", "Sisi", "Wellson")),
        Map.entry(FilterCategory.MALE, Set.of(
            "Alfain", "Abel", "Apin", "Kellen", "Wellson",
            "Rafif", "Reski", "Edrick", "Arya", "Nathan", "Nikho", "Opet"))
    );

    public static List<Member> filterMembers(List<Member> members, FilterCategory category) {
        if (members == null || members.isEmpty()) {
            return Collections.emptyList();
        }

        return switch (category) {
            case FEMALE -> filterFemaleMembers(members);
            case MAN -> filterManagementMembers(members);
            default -> filterByCategory(members, category);
        };
    }

    private static List<Member> filterFemaleMembers(List<Member> members) {
        Set<String> males = FILTER_MAPPING.get(FilterCategory.MALE);
        return members.stream()
            .filter(m -> !males.contains(m.getName()))
            .collect(Collectors.toList());
    }

    private static List<Member> filterManagementMembers(List<Member> members) {
        Set<String> excludedNames = new HashSet<>();
        excludedNames.addAll(FILTER_MAPPING.getOrDefault(FilterCategory.IMT, Collections.emptySet()));
        excludedNames.addAll(FILTER_MAPPING.getOrDefault(FilterCategory.VCD, Collections.emptySet()));
    
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
}