package utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

//import org.apache.commons.lang.ObjectUtils;
//import org.apache.commons.lang.math.NumberUtils;

/**
 * Created by appledev131 on 8/29/16.
 */
public class tryTreeMap {


    /**
     * @param args
     */
    public static void main(String[] args) {
        HashMap<Integer, String> source = new HashMap<Integer, String>(0);
        //      source.put( NumberUtils.createInteger( "5" ), "五" );
        //      source.put( NumberUtils.createInteger( "9" ), "九" );
        //     source.put( NumberUtils.createInteger( "1" ), "一" );
        //      source.put( NumberUtils.createInteger( "4" ), "四" );
        System.out.println("排序前的資料如下：");
        for (Iterator<Integer> iter = source.keySet().iterator(); iter.hasNext(); ) {
            Integer key = iter.next();
            System.out.println(key + " = " + source.get(key));
        }

        /**
         * 開始排序
         */
        TreeMap<Integer, String> target = new TreeMap<Integer, String>();
        target.putAll(source);
        System.out.println("排序後的資料如下(以升冪輸出)：");
        for (Iterator<Integer> iter = target.keySet().iterator(); iter.hasNext(); ) {
            Integer key = iter.next();
            System.out.println(key + " = " + target.get(key));
        }

        System.out.println("排序後的資料如下(以降冪輸出)：");
        Object[] objs = target.keySet().toArray();
        int size = objs.length;
        for (int i = (size - 1); i >= 0; i--) {
            //      Integer key = NumberUtils.createInteger( ObjectUtils.toString( objs[i] ) );
            //      System.out.println( key + " = " + target.get( key ) );
        }

        /**
         * 釋放資源
         */
        if (target != null) {
            target.clear();
            target = null;
        }
        if (source != null) {
            source.clear();
            source = null;
        }
    }
}
