package junit.runner;

public class Version {
    private Version() {
    }

    public static String id() {
        return "4.11";
    }

    public static void main(String[] args) {
        System.out.println(id());
    }
}
