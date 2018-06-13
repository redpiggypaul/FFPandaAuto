package utility;

/**
 * Created by zhupx on 2016/11/28.
 */
public class int2StringTest {
    public static void main(String[] args) {
        int[] intArr = new int[1000000];
        String[] strArr1 = new String[1000000];//为了公平分别定义三个数组

        String[] strArr2 = new String[1000000];
        String[] strArr3 = new String[1000000];
      //  String[] strArr4 = new String[1000000];
        //赋值
        Long t1 = System.currentTimeMillis();
        for(int i=0;i<1000000;i++){
            intArr[i]=i+1;
        }
        Long t2 = System.currentTimeMillis();
        for(int i=0;i<1000000;i++){
            strArr1[i] = String.valueOf(intArr[i]);
        }
        Long t3 = System.currentTimeMillis();
        for(int i=0;i<1000000;i++){
            strArr2[i] = Integer.toString(intArr[i]);
        }
        Long t4 = System.currentTimeMillis();
        for(int i=0;i<1000000;i++){
            strArr3[i] = intArr[i]+"";
        }
        Long t5 = System.currentTimeMillis();
    //    for(int i=0;i<1000000;i++){
    //        strArr4[i] = intArr[i]+".";
   //     }
   //     Long t6 = System.currentTimeMillis();
        System.out.println("t1 = "+t1);
        System.out.println("t2 = "+t2);
        System.out.println("t3 = "+t3);
        System.out.println("t4 = "+t4);
        System.out.println("t5 = "+t5);
        System.out.println("赋值："+(t2-t1));
        System.out.println("String.valueOf(i)："+(t3-t2));
        System.out.println("Integer.toString(i)："+(t4-t3));
        System.out.println("i+\"\"："+(t5-t4));
      //  System.out.println("i+ . :"+(t6-t5));
    }


}
