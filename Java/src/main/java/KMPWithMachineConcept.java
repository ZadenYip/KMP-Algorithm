public class KMPWithMachineConcept implements KMP{
    private String pattern;
    private int[] moveState;
    public KMPWithMachineConcept(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.pattern = pattern;
        moveState = buildMoveState(pattern);
    }

    private int[] buildMoveState(String pattern) {
        int patternLength = pattern.length();
        //moveState状态机数组比pattern长度是多一个的，最后一个状态表示终止状态
        int[] moveState = new int[patternLength + 1];
        int revertState = 0;
        /*
        状态机0和1肯定是没有公共前后缀, 所以回溯肯定是回到状态0
         */
        moveState[0] = 0;
        moveState[1] = 0;

        int state = 2;
        while (state < moveState.length) {
            int suffixIndex = state - 1;
            //pattern.charAt(revertState)为到达下一个状态需要的字符
            if (pattern.charAt(suffixIndex) == pattern.charAt(revertState)) {
                revertState++;
                moveState[state] = revertState;
                state++;
            } else {
                if (revertState > 0) {
                    revertState = moveState[revertState];
                    //这里不进入下一阶段是因为回溯了 此时要对比suffixIndex与进入下一个状态所需的字符是否一样
                }
                else {
                    //此时已经是0状态 推进state即可
                    state++;
                }
            }
        }
        return moveState;
    }

    public int search(String text) {
        /* 不妨把i 作为每次text作为一个队列弹出的第一个元素的pop为输入给状态机判断是否进入下一个状态 */
        int input = 0;
        int state = 0;
        while (input < text.length()) {
            //pattern.charAt(state)为到达下一个状态需要的字符
            if (text.charAt(input) == pattern.charAt(state)) {
                state++;
                input++;
                if (state == moveState.length - 1) {
                    return input - pattern.length();
                }
            } else {
                if (state > 0) {
                    state = moveState[state];
                    //这里不进行input++, 因为回溯后要查看输入等于回溯后进入下一个状态的条件
                } else {
                    //零状态直接推进
                    input++;
                }
            }
        }
        return -1;
    }
}
