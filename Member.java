public class Member {
    private final String name;
    private final String imagePath;
    private final String status;

    public Member(String name, String imagePath, String status) {
        this.name = name;
        this.imagePath = imagePath;
        this.status = status;
    }

    public String getName() { return name; }
    public String getImagePath() { return imagePath; }
    public String getStatus() { return status; }
}