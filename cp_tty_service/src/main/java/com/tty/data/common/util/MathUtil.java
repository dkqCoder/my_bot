package com.tty.data.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangdi
 * @date 17/4/17
 * @Description
 */
public class MathUtil {
    /// <summary>
    /// 从n中 取出 m的组合个数
    /// n!/(m!*(n-m)!)
    /// </summary>
    /// <param name="n"></param>
    /// <param name="m"></param>
    /// <returns></returns>
    public static int combination(int n, int m) {
        return factorial(n, n - m) / factorial(m, 0);
    }

    /// <summary>
    /// 阶乘
    /// </summary>
    /// <param name="n"></param>
    /// <returns></returns>
    private static int factorial(int n, int limit) {
        int res = 1;
        for (int i = n; i > limit; i--) {
            res = res * i;
        }
        return res;
    }

    /**
     * 从n个数字中选择m个数字
     * @param a
     * @param m
     * @return
     */
    public static List<int[]> combine(int[] a,int m){
        int n = a.length;
        if(m>n){
            return null;
        }
        if(m==n){
            ArrayList<int[]> ints = new ArrayList<>();
            ints.add(a);
            return ints;
        }

        List<int[]> result = new ArrayList<int[]>();

        int[] bs = new int[n];
        for(int i=0;i<n;i++){
            bs[i]=0;
        }
        //初始化
        for(int i=0;i<m;i++){
            bs[i]=1;
        }
        boolean flag = true;
        boolean tempFlag = false;
        int pos = 0;
        int sum = 0;
        //首先找到第一个10组合，然后变成01，同时将左边所有的1移动到数组的最左边
        do{
            sum = 0;
            pos = 0;
            tempFlag = true;
            result.add(print(bs,a,m));

            for(int i=0;i<n-1;i++){
                if(bs[i]==1 && bs[i+1]==0 ){
                    bs[i]=0;
                    bs[i+1]=1;
                    pos = i;
                    break;
                }
            }
            //将左边的1全部移动到数组的最左边

            for(int i=0;i<pos;i++){
                if(bs[i]==1){
                    sum++;
                }
            }
            for(int i=0;i<pos;i++){
                if(i<sum){
                    bs[i]=1;
                }else{
                    bs[i]=0;
                }
            }

            //检查是否所有的1都移动到了最右边
            for(int i= n-m;i<n;i++){
                if(bs[i]==0){
                    tempFlag = false;
                    break;
                }
            }
            if(tempFlag==false){
                flag = true;
            }else{
                flag = false;
            }

        }while(flag);
        result.add(print(bs,a,m));

        return result;
    }

    private static int[] print(int[] bs,int[] a,int m){
        int[] result = new int[m];
        int pos= 0;
        for(int i=0;i<bs.length;i++){
            if(bs[i]==1){
                result[pos]=a[i];
                pos++;
            }
        }
        return result ;
    }

    /**
     * 排列选择
     * @param dataArray
     * @param n
     * @return
     */
    public static List<List<Long>> arrangementSelect(Long[] dataArray, int n) {
        List<List<Long>> collect = new ArrayList<>();
        arrangementSelect(dataArray, new Long[n], 0, collect);
        return collect;
    }

    /**
     * 组合选择
     * @param dataList
     * @param n
     * @return
     */
    public static List<List<Long>> combinationSelect(Long[] dataList, int n) {
        List<List<Long>> collect = new ArrayList<>();
        combinationSelect(dataList, 0, new Long[n], 0, collect);
        return collect;
    }

    /**
     * 排列选择
     * @param dataList 待选列表
     * @param resultList 前面（resultIndex-1）个的排列结果
     * @param index 选择索引，从0开始
     */
    private static void arrangementSelect (Long[] dataList, Long[] resultList, int index, List<List<Long>> collect) {
        if (index >= resultList.length) { // 全部选择完时，输出排列结果
            collect.add(Arrays.asList(resultList.clone()));
            return;
        }
        for (int i = 0; i < dataList.length; i++) {
            // 判断待选项是否存在于排列结果中
            boolean exists = false;
            for (int j = 0; j < index; j++) {
                if (dataList[i].equals(resultList[j])) {
                    exists = true;
                    break;
                }
            }
            if (!exists) { // 排列结果不存在该项，才可选择
                resultList[index] = dataList[i];
                arrangementSelect(dataList, resultList, index + 1, collect);
            }
        }
    }

    /**
     * 组合选择
     * @param dataList 待选列表
     * @param dataIndex 待选开始索引
     * @param resultList 前面（resultIndex-1）个的组合结果
     * @param index 选择索引，从0开始
     */
    private static void combinationSelect(Long[] dataList,int dataIndex, Long[] resultList, int index, List<List<Long>> collect) {
        int resultLen = resultList.length;
        int resultCount = index + 1;
        if (resultCount > resultLen) { // 全部选择完时，输出组合结果
            collect.add(Arrays.asList(resultList.clone()));
            return;
        }

        // 递归选择下一个
        for (int i = dataIndex; i < dataList.length + resultCount - resultLen; i++) {
            resultList[index] = dataList[i];
            combinationSelect(dataList, i + 1, resultList, index + 1, collect);
        }
    }
}
