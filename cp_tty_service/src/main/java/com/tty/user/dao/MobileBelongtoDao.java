package com.tty.user.dao;

import com.tty.user.dao.entity.MobileBelongtoENT;

import java.util.List;
import java.util.Map;


/**
 * @author zxh
 * @create 2017-07-28 10:23:53
 **/
public interface MobileBelongtoDao {

    void saveMobileBelongto(MobileBelongtoENT ent);

    void updateMobileBelongto(MobileBelongtoENT ent);

    void saveOrUpdateMobileBelongto(MobileBelongtoENT ent);

    MobileBelongtoENT findMobileBelong(String mobile_seg);

    List<Map<String, String>> findMobileBelongByProvince(String province);
}