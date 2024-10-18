public class KMPWithMachineConcept implements KMP{
    private String pattern;
    private int[] moveState;
    public KMPWithMachineConcept(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new NullPointerException();
        }
        this.pattern = pattern;
        moveState = buildMoveState(pattern);
    }

    private int[] buildMoveState(String pattern) {
        int patternLength = pattern.length();
        int[] moveState = new int[patternLength + 1];
        int currentMaxLength = 0;
        moveState[0] = 0;
        moveState[1] = 0;
        for (int state = 2; state < moveState.length; state++) {
            int index = state - 1;
            while (currentMaxLength > 0 && pattern.charAt(currentMaxLength) != pattern.charAt(index)) {
                currentMaxLength = moveState[currentMaxLength - 1];
            }
            if (pattern.charAt(currentMaxLength) == pattern.charAt(index)) {
                currentMaxLength++;
            }
            moveState[state] = currentMaxLength;
        }
        return moveState;
    }

    public int search(String text) {
        for (int i = 0, j = 0; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = moveState[j];
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
