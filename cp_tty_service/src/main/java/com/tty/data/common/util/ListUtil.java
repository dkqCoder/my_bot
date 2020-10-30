package com.tty.data.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.apache.commons.collections.CollectionUtils;

/**
 */
public class ListUtil {

    /**
     * @Author shenwei
     * @Date 2017/2/21 16:47
     * @Description 取得list中前n个元素
     */
    public static <T> List<T> take(List<T> list, Integer count) {
        List<T> result = new ArrayList<T>();
        if (CollectionUtils.isNotEmpty(list)) {
            if (count > list.size()) {
                count = list.size();
            }
            for (Integer i = 0; i < count; i++) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    /**
     * @Description 从list中查找符合要求的元素
     */
    public static <T> List<T> filter(Predicate<T> predicate, List<T> sourceList) {
        List<T> destinationList = new ArrayList<T>();
        if (CollectionUtils.isNotEmpty(sourceList)) {
            for (T item : sourceList) {
                if (predicate.test(item)) {
                    destinationList.add(item);
                }
            }
        }
        return destinationList;
    }
}
