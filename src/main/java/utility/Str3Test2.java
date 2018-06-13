package utility;

import java.util.Calendar;

/**
 * Created by appledev131 on 9/2/16.
 */
public class Str3Test2 {
    public static void main(String[] args) {
        Str3Test2 test = new Str3Test2();

        System.out.println(test.testString());
        System.out.println(test.testStringBuilder_o());
        System.out.println("~~~~~~");

        for (int i = 0; i < 5; i++) {
            System.out.println(test.testStringBuilder() + "---" + test.testStringBuilder2());
        }
    }


    public long testString() {
        System.out.println("testString");
        String a = "";
        long start = Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < 100000; i++) {
            a += i;
        }
        long end = Calendar.getInstance().getTimeInMillis();
        return end - start;
    }

    public long testStringBuilder_o() {
        System.out.println("testStringBuilder_o");
        StringBuilder a = new StringBuilder();
        long start = Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < 100000; i++) {
            a.append(i);
        }
        long end = Calendar.getInstance().getTimeInMillis();
        return end - start;
    }

    public long testStringBuilder() {
        StringBuilder a = new StringBuilder();
        long start = Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < 10000000; i++) {
            a.append(1);
        }
        long end = Calendar.getInstance().getTimeInMillis();
        return end - start;
    }

    public long testStringBuilder2() {
        StringBuilder a = new StringBuilder(10000000);
        long start = Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < 10000000; i++) {
            a.append(1);
        }
        long end = Calendar.getInstance().getTimeInMillis();
        return end - start;
    }
}
