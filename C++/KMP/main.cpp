#include <iostream>
#include <cstring>
#include <exception>

using std::string;

int* BuildMoveState(string pattern) {
    if (pattern.length() == 0)
    {
        throw std::invalid_argument("字符串长度不能为0");
    }
    int pattern_length = pattern.length();
    int move_state_length = pattern_length + 1;
    //moveState状态机数组比pattern长度是多一个的，最后一个状态表示终止状态
    int* move_state = new int[move_state_length];
    int revert_state = 0;
    /*
    状态机0和1肯定是没有公共前后缀, 所以回溯肯定是回到状态0
     */
    move_state[0] = 0;
    move_state[1] = 0;

    int state = 2;
    while (state < move_state_length) {
        int suffixIndex = state - 1;
        //pattern.charAt(revertState)为到达下一个状态需要的字符
        if (pattern.at(suffixIndex) == pattern.at(revert_state)) {
            revert_state++;
            move_state[state] = revert_state;
            state++;
        }
        else {
            if (revert_state > 0) {
                revert_state = move_state[revert_state];
                //这里不进入下一阶段是因为回溯了 此时要对比suffixIndex与进入下一个状态所需的字符是否一样
            }
            else {
                //此时已经是0状态 推进state即可
                state++;
            }
        }
    }
    return move_state;
}

int Search(string text, string pattern) {
    int* move_state = BuildMoveState(pattern);
    int move_state_length = pattern.length() + 1;
    /* 不妨把i 作为每次text作为一个队列弹出的第一个元素的pop为输入给状态机判断是否进入下一个状态 */
    int input = 0;
    int state = 0;
    while (input < text.length()) {
        //pattern.charAt(state)为到达下一个状态需要的字符
        if (text.at(input) == pattern.at(state)) {
            state++;
            input++;
            if (state == move_state_length - 1) {
                delete [] move_state;
                return input - pattern.length();
            }
        }
        else {
            if (state > 0) {
                state = move_state[state];
                //这里不进行input++, 因为回溯后要查看输入等于回溯后进入下一个状态的条件
            }
            else {
                //零状态直接推进
                input++;
            }
        }
    }
    delete [] move_state;
    return -1;
}

int main()
{
    string text = "ABBBBCBCBBBD";
    string pattern = "BBBD";
    int actual = Search(text, pattern);
    int expect = text.find(pattern);
    std::cout << "Expect: " << expect << std::endl;
    std::cout << "Acutal: " << actual << std::endl;
    return 0;
}
