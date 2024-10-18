import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class KMPTest {
    @Test
    public void randomComparisonTest() {
        for (int i = 0; i < 3000; i++) {
            System.out.println("count:" + i);
            String randomText = generateRandomString(30);
            String randomPattern = generateRandomString(1);
            System.out.println("Text:" + randomText);
            System.out.println("Pattern:" + randomPattern);
            int expect = randomText.indexOf(randomPattern);
            testExample(randomText, randomPattern, expect);
            if (expect != -1) {
                System.out.println("find successfully");
                System.out.println(Main.getMarkedText(randomText, randomPattern, expect));
            } else {
                System.out.println("Didn't find pattern:" + randomPattern);
            }
            System.out.println("---------------------------------");
        }
    }

    @Test
    public void simpleComparisonTest() {
        testExample("BBCBDDCCDDCBABCBADDCDABBDCABBB", "DCDD", -1);
        testExample("ABBBBCBCBBBD", "BBBD", 8);
        testExample("ABABDABACDABABCABAB", "ABABCABAB",10);
    }



    private void testExample(String text, String pattern, int expect) {
        //Here can be replaced
        KMP kmp = new KMPWithMachineConcept(pattern);
        Assertions.assertEquals(expect, kmp.search(text));
    }

    private String generateRandomString(int length) {
        String alphabet = "ABCD";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }

        return sb.toString();
    }
}
