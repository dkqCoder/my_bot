package com.tty.data.common.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangdi
 * @date 17/4/17
 * @Description
 */
public class JCOptimize {
    /// <summary>
    /// 优化
    /// </summary>
    /// <param name="onePList">单注奖金列表</param>
    /// <param name="playBuy"></param>
    /// <param name="type"></param>
    /// <param name="checkedIndexList">选择的索引</param>
    /// <returns>倍数列表</returns>
    public static List<Integer> optimize(List<Double> onePList, int playBuy, String type)
    {

        //按照原先顺序的注数
        List<Integer> countList;
        switch (type)
        {
            case "btnAverage":
                if (playBuy < onePList.size() * 20)
                    countList = balPrizesLowBuy(onePList, playBuy);
                else
                    countList = balPrizesHigh(onePList, playBuy);
                break;

            case "btnHot":
                countList = hotPrizes(onePList, playBuy);
                break;

            case "btnCold":
                countList = coldPrizes(onePList, playBuy);
                break;

            default:
                countList = new ArrayList<Integer>();
                break;
        }
        return countList;
    }

    /// <summary>
    /// 热门优化算法
    /// </summary>
    /// <param name="onePList">单注奖金列表</param>
    /// <param name="playBuy">本金</param>
    /// <returns>倍数列表</returns>
    private static List<Integer> hotPrizes(List<Double> onePList, int playBuy)
    {
        List<Integer> countList = new ArrayList<Integer>();
        //【第一步】：优化前先有1注底注，同时找出热门单注索引
        int hotIndex = 0;
        double hotPrize = onePList.get(0);
        for (int i = 0; i < onePList.size(); i++)
        {
            countList.add(1);
            if (onePList.get(i) < hotPrize)
            {
                hotIndex = i;
                hotPrize = onePList.get(i);
            }
        }
        //各1注后剩下的资金
        int leftMoney = playBuy - countList.size() * 2;
        if (leftMoney <= 0) return countList;
        //hotPrize = onePList[hotIndex];
        //【第二步】：优先考虑热门保本
        int hotCount = 1;
        while (hotPrize * hotCount < playBuy)
        {
            hotCount++;
            //如果资金用完了
            //if (leftMoney - hotCount * 2 <= 0)
            //    return countList;
            //countList[hotIndex] = hotCount;
            //如果资金用完了
            if (leftMoney - hotCount * 2 <= 0)
            {
                if (leftMoney == 2 & hotCount == 2)
                {
                    countList.set(0, 2);
                }
                else if (leftMoney == 4 && hotCount == 2)
                {
                    countList.set(0, 3);
                }
                else
                {
                    countList.set(0,hotCount + 1);
                }
                return countList;
            }
            else
            {
                countList.set(hotIndex, hotCount);
            }
        }
        leftMoney = leftMoney - (hotCount - 1) * 2;
        //【第三步】：如果热门保本后还有剩余资金，使其他注保本
        boolean allBreak = false;//全部保本
        while (!allBreak)
        {
            allBreak = true;
            //遍历增加每一注
            for (int i = 0; i < onePList.size(); i++)
            {
                //已经保本的不考虑
                if (i == hotIndex||onePList.get(i) * countList.get(i) >= playBuy){ continue;}
                //如果有未保本的
                allBreak = false;
                int tmp =countList.get(i)+1;
                countList.set(i,tmp);
                leftMoney = leftMoney - 2;
                //本钱用完了
                if (leftMoney <= 0){ return countList;}
            }
        }
        //【第四步】：剩余的钱全部用在热门上
        int tmp =countList.get(hotIndex)+leftMoney / 2;
        countList.set(hotIndex,tmp);
        return countList;
    }

    /// <summary>
    /// 冷门优化算法
    /// </summary>
    /// <param name="onePList">单注奖金列表</param>
    /// <param name="playBuy">本金</param>
    /// <returns>倍数列表</returns>
    private static List<Integer> coldPrizes(List<Double> onePList, int playBuy)
    {
        List<Integer> countList = new ArrayList<Integer>();
        //【第一步】：优化前先有1注底注，同时找出冷门单注索引
        int coldIndex = 0;
        double coldPrize = onePList.get(0);
        for (int i = 0; i < onePList.size(); i++)
        {
            countList.add(1);
            if (onePList.get(i) > coldPrize)
            {
                coldIndex = i;
                coldPrize = onePList.get(i);
            }
        }
        //各1注后剩下的资金
        int leftMoney = playBuy - countList.size() * 2;
        if (leftMoney <= 0) return countList;
        //coldPrize = onePList[coldIndex];
        //【第二步】：优先考虑冷门保本
        int coldCount = 1;
        while (coldPrize * coldCount < playBuy)
        {
            coldCount++;
            //如果资金用完了
            //if (leftMoney - coldCount * 2 <= 0)
            //    return countList;
            //countList[coldIndex] = coldCount;
            //如果资金用完了
            if (leftMoney - coldCount * 2 <= 0)
            {
                if (leftMoney == 2 & coldCount == 2)
                {
                    countList.set(0, 2);
                }
                else if (leftMoney == 4 && coldCount == 2)
                {
                    countList.set(0, 3);
                }
                else
                {
                    countList.set(0,coldCount + 1);
                }
                return countList;
            }
            else
            {
                countList.set(coldIndex,coldCount);
            }
        }
        leftMoney = leftMoney - (coldCount - 1) * 2;
        //【第三步】：如果冷门保本后还有剩余资金，使其他注保本
        boolean allBreak = false;//全部保本
        while (!allBreak)
        {
            allBreak = true;
            //遍历增加每一注
            for (int i = 0; i < onePList.size(); i++)
            {
                //已经保本的不考虑
                if (i == coldIndex||onePList.get(i) * countList.get(i) >= playBuy) {continue;}
                //如果有未保本的
                allBreak = false;
                int tmp =countList.get(i)+1;
                countList.set(i,tmp);
                leftMoney = leftMoney - 2;
                //本钱用完了
                if (leftMoney <= 0){ return countList;}
            }
        }
        //【第四步】：剩余的钱全部用在冷门上
        int tmp =countList.get(coldIndex)+leftMoney / 2;
        countList.set(coldIndex,tmp);
        return countList;
    }

    /// <summary>
    /// 保本优化算法
    /// </summary>
    private static List<Integer> protectPrizes(List<Double> onePList, int playBuy, List<Integer> checkedIndexs)
    {
        List<Integer> countList = new ArrayList<Integer>();
        //【第一步】：优化前先有1注底注\获得用户选择的行索引
        for (int i = 0; i < onePList.size(); i++)
        {
            countList.add(1);
        }
        if (checkedIndexs == null) {return countList;}
        //各1注后剩下的资金
        int leftMoney = playBuy - countList.size() * 2;
        if (leftMoney == 0){ return countList;}
        //【第二步】保本指定的投注
        boolean allBreak = false;
        while (!allBreak)
        {
            allBreak = true;
            //遍历增加每一注
            for (int i : checkedIndexs)
            {
                //已经保本的不考虑
                if (onePList.get(i) * countList.get(i) >= playBuy){ continue;}
                //如果有未保本的
                allBreak = false;
                int tmp =countList.get(i)+1;
                countList.set(i,tmp);
                leftMoney = leftMoney - 2;
                //本钱用完了
                if (leftMoney <= 0) return countList;
            }
        }
        //【第三步】剩余资金平均优化在其他投注上
        List<Double> leftPrizelist = new ArrayList<Double>();
        for (int i = 0; i < onePList.size(); i++)
        {
            if (checkedIndexs.contains(i)){ continue;}
            leftPrizelist.add(onePList.get(i));
        }
        List<Integer> leftCountList;
        //分高金额和低金额的平衡，为提高运算效率和准确度，区分做
        if (leftMoney < onePList.size() * 2)
            leftCountList = balPrizesLowBuy(leftPrizelist, leftMoney);
        else
            leftCountList = balPrizesHigh(leftPrizelist, leftMoney);
        int j = 0;
        for (int i = 0; i < countList.size(); i++)
        {
            if (checkedIndexs.contains(i)){ continue;}
            int tmp = countList.get(i)+leftCountList.get(j++);
            countList.set(i,tmp);
        }
        return countList;
    }

    /// <summary>
    /// 高金额平均优化算法
    /// </summary>
    /// <param name="onePList">单注奖金列表</param>
    /// <param name="money">剩余本金</param>
    /// <returns>倍数列表</returns>
    private static List<Integer> balPrizesHigh(List<Double> onePList, int playBuy)
    {
        List<Integer> countList = new ArrayList<Integer>();
        //【第一步】：优化前先有1注底注\获得用户选择的行索引
        for (int i = 0; i < onePList.size(); i++)
        {
            countList.add(1);
        }
        //各1注后剩下的资金
        int money = playBuy - countList.size() * 2;
        if (money <= 0) {return countList;}
        List<Double> douCounts = new ArrayList<Double>();
        double temp = 0;
        double prize0 = onePList.get(0);
        for (double prize : onePList)
        {
            temp += prize0 / prize;
        }
        double count0 = (money / (2 * temp));
        douCounts.add(count0);
        int minIndex = 0; //记录最小单注奖金，用来最后微调本金
        double minPrize = prize0;
        for (int i = 1; i < onePList.size(); i++)
        {
          double count_i = (prize0 * count0 / onePList.get(i));
            douCounts.add(count_i);
            if (minPrize > onePList.get(i))
            {
                minPrize = onePList.get(i);
                minIndex = i;
            }
        }
        int realbuy = 0;
        double item; int count;
        for (int i = 0; i < douCounts.size(); i++)
        {
            item = douCounts.get(i);
            //四舍五入
            count = item - (int)item >= 0.5 ? (int)item + 1 : (int)item;
            countList.set(i,countList.get(i) + count);
            realbuy += countList.get(i) * 2;
        }
        //foreach (var item in douCounts)
        //{
        //    //四舍五入
        //    int count = item -(int)item >= 0.5 ?(int)item + 1 :(int)item;
        //    countList.add(count);
        //    realbuy += count * 2;
        //}
        int countGap = (int)(realbuy - playBuy) / 2;
        if (countGap > 0)
        {
            int i;
            for (i = 0; i < countGap; i++)
            {
                countList.set(i, countList.get(i) - 1);
            }
            countGap -= i + 1;
        }
        else if (countGap < 0)
        {
            countList.set(countList.size() - 1,countList.get(countList.size() - 1) + 1);
        }
        //小Sp投注减去多余的注数
        //while (countGap > 0)
        //{
        //    int i;
        //    for (i = 0; i < countGap; i++)
        //    {
        //        countList[i] = countList[i] - 1;
        //    }
        //    countGap -= i + 1;
        //}

        //int allCount = 0;
        //foreach (var c in countList)
        //{
        //    allCount += c;
        //}
        //realbuy = allCount * 2;
        return countList;
    }

    /// <summary>
    /// 低金额递归平衡奖金
    /// </summary>
    private static List<Integer> balPrizesLowBuy(List<Double> onePList, double money)
    {
        List<Integer> countList = new ArrayList<Integer>();
        //平衡前先有1注底注
        for (Double item : onePList)
        {
            countList.add(1);
        }
        //各1注后剩下的资金
        double leftMoney = money - countList.size() * 2;
        if (leftMoney == 0){ return countList;}
        addPrizes(onePList, leftMoney, countList);
        return countList;
    }

    /// <summary>
    /// 归添加最少奖金注数
    /// </summary>
    private static void addPrizes(List<Double> onePList, double leftMoney, List<Integer> countList)
    {
        if (leftMoney <= 0) return;
        //找出最少奖金注
        double minPrize = onePList.get(0) * countList.get(0);
        int minIndex = 0;
        for (int i = 1; i < onePList.size(); i++)
        {
            if (onePList.get(i) * countList.get(i) < minPrize)
            {
                minPrize = onePList.get(i) * countList.get(i);
                minIndex = i;
            }
        }
        countList.set(minIndex,countList.get(minIndex) + 1);
        leftMoney = leftMoney - 2;
        addPrizes(onePList, leftMoney, countList);
    }
}
