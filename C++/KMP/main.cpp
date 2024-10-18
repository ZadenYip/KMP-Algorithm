#include <iostream>
#include <cstring>

using std::string;

int* BuildPMT(string pattern) {
    int patternLength = pattern.length();
    int* pmt = new int[patternLength];
    pmt[0] = 0;
    int currentMaxLength = 0;
    for (int i = 1; i < patternLength; i++) {
        while (currentMaxLength > 0 && pattern.at(currentMaxLength) != pattern.at(i)) {
            currentMaxLength = pmt[currentMaxLength - 1];
        }
        if (pattern.at(currentMaxLength) == pattern.at(i)) {
            currentMaxLength++;
        }
        pmt[i] = currentMaxLength;
    }
    return pmt;
}

int Search(string text, string pattern) {
    int* pmt = BuildPMT(pattern);
    for (int i = 0, j = 0; i < text.length() && j < pattern.length(); i++) {
        while (j > 0 && text.at(i) != pattern.at(j)) {
            j = pmt[j - 1];
        }
        if (text.at(i) == pattern.at(j)) {
            j++;
        }
        if (j == pattern.length()) {
            delete [] pmt;
            return i - j + 1;
        }
    }
    delete [] pmt;
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
