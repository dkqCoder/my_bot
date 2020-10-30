package com.tty.data.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zxh
 * @create 2017-03-02 15:31:50
 **/

@Entity
@Table(name = "basedata_lottery_tag_relationship")
public class BasedataLotteryTagRelationshipENT implements java.io.Serializable {

    /** n_id */
    private Integer id;

    /** n_lottery_id */
    private Integer lotteryId;

    /** s_tag_code */
    private String tagCode;




    /**
     * 获取 n_id 的值
     * @return Integer 
     */
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    public Integer  getId() {
        return id;
    }

    /**
     * 设置n_id 的值
     * @param id n_id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取 n_lottery_id 的值
     * @return Integer 
     */
    @Column(name = "n_lottery_id")
    public Integer  getLotteryId() {
        return lotteryId;
    }

    /**
     * 设置n_lottery_id 的值
     * @param lotteryId n_lottery_id
     */
    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    /**
     * 获取 s_tag_code 的值
     * @return String 
     */
    @Column(name = "s_tag_code")
    public String  getTagCode() {
        return tagCode;
    }

    /**
     * 设置s_tag_code 的值
     * @param tagCode s_tag_code
     */
    public void setTagCode(String tagCode) {
        this.tagCode = tagCode;
    }



}