public class Main {

    public static void main(String[] args) {
        String text = "ABBBBCBCBBBD";
        String pattern = "BBBD";
        KMP kmp = new KMPWithPMT(pattern);

        System.out.println(getMarkedText(text, pattern, kmp.search(text)));
    }

    public static String getMarkedText(String text, String pattern, int index) {
        if (index == -1) {
            return "FAIL";
        }
        StringBuilder builder = new StringBuilder(text);
        builder.insert(index + pattern.length(), "*");
        builder.insert(index, "*");
        return builder.toString();
    }
}
