package utility.readProperity;

import java.io.*;
import java.lang.reflect.Constructor;

public class SingletonTester {
    public static <T> void checkClassNewInstance(Class<T> c){

        try {
            T t1 = c.newInstance();
            T t2 = c.newInstance();
            if(t1 != t2){
                System.out.println("Class.newInstance校验失败，可以创建两个实例");
            }else{
                System.out.println("Class.newInstance校验通过");
            }
        } catch (Exception e) {
            System.out.println("不能用Class.newInstance创建，因此Class.newInstance校验通过");
        }
    }

    public static <T> void checkContructorInstance(Class<T> c){
        try {
            Constructor<T> ctt = c.getDeclaredConstructor();
            ctt.setAccessible(true);
            T t1 = ctt.newInstance();
            T t2 = ctt.newInstance();
            if(t1 != t2){
                System.out.println("ContructorInstance校验失败，可以创建两个实例");
            }else{
                System.out.println("ContructorInstance校验通过");
            }
        } catch (Exception e) {
            System.out.println("不能用反射方式创建，因此ContructorInstance校验通过");
        }
    }

    public static <T> void testSerializable(T t1){
        File objectF = new File("/object");
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(objectF));
            out.writeObject(t1);
            out.flush();
            out.close();
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(objectF));
            T t2 = (T) in.readObject();
            in.close();

            if(t1 != t2){
                System.out.println("Serializable校验失败，可以创建两个实例");
            }else{
                System.out.println("Serializable校验通过");
            }
        } catch (Exception e) {
            System.out.println("不能用反序列化方式创建，因此Serializable校验通过");
        }

    }

    public static void main(String[] args) {
     //   SingleTonReadProperity reader = new SingleTonReadProperity();
        checkClassNewInstance(SingleTonReadProperity.class);
        checkContructorInstance(SingleTonReadProperity.class);
        testSerializable(SingleTonReadProperity.getInstance());

    }
}
