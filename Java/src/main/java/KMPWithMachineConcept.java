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
        int shadowState = 0;
        /*
        状态机0和1肯定是没有公共前后缀, 所以回溯肯定是回到状态0
         */
        moveState[0] = 0;
        moveState[1] = 0;

        int settingState = 2;
        while (settingState < moveState.length) {
            int inputIndex = settingState - 1;
            //pattern.charAt(shadowState)为影子状态到达下一个状态需要的字符
            if (pattern.charAt(inputIndex) == pattern.charAt(shadowState)) {
                moveState[settingState] = shadowState + 1;
                shadowState++;
                settingState++;
            } else {
                if (shadowState > 0) {
                    shadowState = moveState[shadowState];
                    //这里不进入下一阶段是因为回溯了 此时要对比inputIndex与进入下一个状态所需的字符是否一样
                }
                else {
                    //此时revertState已经是0状态又不匹配 推进state即可
                    settingState++;
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
                    //零状态且不匹配不需要回溯 找下一个输入
                    input++;
                }
            }
        }
        return -1;
    }
}
