package com.tty.user.dao.entity;

import javax.persistence.*;
import java.io.Serializable;



/**
 * 
 * @author zxh
 * @create 2017-08-23 11:09:55
 **/

@Entity
@Table(name = "user_mobile_whitelist")
public class UserMobileWhitelistENT implements Serializable {

    /** n_id */
    private Long id;

    /** s_mobile */
    private String mobile;




    /**
     * 获取 n_id 的值
     * @return Long 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    public Long  getId() {
        return id;
    }

    /**
     * 设置n_id 的值
     * @param id n_id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取 s_mobile 的值
     * @return String 
     */
    @Column(name = "s_mobile")
    public String  getMobile() {
        return mobile;
    }

    /**
     * 设置s_mobile 的值
     * @param mobile s_mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }



}