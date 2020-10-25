package com.tty.user.dao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zxh
 * @create 2017-03-06 18:02:01
 **/

@Entity
@Table(name = "user_master_info")
public class UserMasterInfoENT implements java.io.Serializable {

    /** n_id */
    private Long id;

    /** n_user_id */
    private Long userId;

    /** s_nick_name */
    private String nickName;

    /** s_user_face */
    private String userFace;




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
     * 获取 n_user_id 的值
     * @return Long 
     */
    @Column(name = "n_user_id")
    public Long  getUserId() {
        return userId;
    }

    /**
     * 设置n_user_id 的值
     * @param userId n_user_id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取 s_nick_name 的值
     * @return String 
     */
    @Column(name = "s_nick_name")
    public String  getNickName() {
        return nickName;
    }

    /**
     * 设置s_nick_name 的值
     * @param nickName s_nick_name
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取 s_user_face 的值
     * @return String 
     */
    @Column(name = "s_user_face")
    public String  getUserFace() {
        return userFace;
    }

    /**
     * 设置s_user_face 的值
     * @param userFace s_user_face
     */
    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }



}