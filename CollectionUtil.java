package com.ccue.dispatch.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 该类提供对集合类的高效操作
 * @author fph
 * 2018-03-21
 *
 */
public class CollectionUtil {
	
	public static void main(String[] args) {
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		for (int i = 0; i < 10000; i++) {
			list1.add("test" + i);
			list2.add("test" + i);
		}
		for (int i = 10000; i < 10010; i++) {
			list1.add("test" + i);
			list2.add("test" + i*2);
		}
		List<String>  stringList= getDiffrent(list1,list2);
		for (String i : stringList) {
			System.out.println(i);
		}
	}

	/**
     * 获取两个List的不同元素
     * @param list1
     * @param list2
     * @return
     */
    public static  List<String> getDiffrent(List<String> list1, List<String> list2) {
         List<String> diff = new ArrayList<String>();
         List<String> maxList = list1;
         List<String> minList = list2;
         Map<String,Integer> map = new HashMap<String,Integer>(maxList.size());
         for (String string : maxList) {
             map.put(string, 1);
         }
         for (String string : minList) {
             if(map.get(string)!=null)
             {
                 map.put(string, 2);
                 continue;
             }
             diff.add(string);
         }
        return diff;
    }
}
