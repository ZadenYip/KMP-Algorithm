public class KMP {
    private String pattern;
    private int[] pmt;
    public KMP(String pattern) {
        this.pattern = pattern;
        pmt = buildPMT(pattern);
    }

    private int[] buildPMT(String pattern) {
        int patternLength = pattern.length();
        int[] pmt = new int[patternLength];
        pmt[0] = 0;
        int currentMaxLength = 0;
        for (int i = 1; i < patternLength; i++) {
            while (currentMaxLength > 0 && pattern.charAt(currentMaxLength) != pattern.charAt(i)) {
                currentMaxLength = pmt[currentMaxLength - 1];
            }
            if (pattern.charAt(currentMaxLength) == pattern.charAt(i)) {
                currentMaxLength++;
            }
            pmt[i] = currentMaxLength;
        }
        return pmt;
    }

    public int search(String text) {
        for (int i = 0, j = 0; i < text.length() && j < pattern.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = pmt[j - 1];
            }
            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            if (j == pattern.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }
}
