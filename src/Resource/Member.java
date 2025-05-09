package src.Resource;

public class Member {
    private final String name;
    private final String imagePath;
    private final String status;

    public Member(String name, String imagePath, String status) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
        this.imagePath = imagePath != null ? imagePath : "";
        this.status = status != null ? status : "";
    }

    // Getter methods
    public String getName() { return name; }
    public String getImagePath() { return imagePath; }
    public String getStatus() { return status; }

    // Equality based on name (case-insensitive)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return name.equalsIgnoreCase(member.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }

    // Better debugging
    @Override
    public String toString() {
        return "Member{name='" + name + "', status='" + status + "'}";
    }

    // Builder Pattern (Opsional)
    public static class Builder {
        private String name;
        private String imagePath = "";
        private String status = "";

        public Builder(String name) {
            this.name = name;
        }

        public Builder imagePath(String path) {
            this.imagePath = path;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Member build() {
            return new Member(name, imagePath, status);
        }
    }
}