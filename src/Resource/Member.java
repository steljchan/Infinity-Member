package src.Resource;

public class Member {
    private final String name;
    private final String imagePath;
    private final String status;
    private final String major;

    public Member(String name, String imagePath, String status, String major) {
        this.name = (name == null || name.trim().isEmpty()) ? "Unknown" : name.trim();
        this.imagePath = imagePath != null ? imagePath : "";
        this.status = status != null ? status : "";
        this.major = major != null ? major : "";
    }

    // Getter methods
    public String getName() { return name; }
    public String getImagePath() { return imagePath; }
    public String getStatus() { return status; }
    public String getMajor() { return major; }

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

    @Override
    public String toString() {
        return "Member{name='" + name + "', status='" + status + "', major='" + major + "'}";
    }

    public static class Builder {
        private String name;
        private String imagePath = "";
        private String status = "";
        private String major = "";

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

        public Builder major(String major) {
            this.major = major;
            return this;
        }

        public Member build() {
            return new Member(name, imagePath, status, major);
        }
    }
}