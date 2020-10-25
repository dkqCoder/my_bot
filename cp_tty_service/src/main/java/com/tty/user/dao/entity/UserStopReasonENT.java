package com.tty.user.dao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 
 * @create 2017-04-03 18:37:51
 **/

@Entity
@Table(name = "user_stop_reason")
public class UserStopReasonENT implements java.io.Serializable {

    /** n_id */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 操作者 */
    private String operator;

    private Integer type;

    /** 操作时间 */
    private Date operateTime;

    /** 禁用理由 */
    private String reason;




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
     * 获取 用户ID 的值
     * @return Long 
     */
    @Column(name = "n_user_id")
    public Long  getUserId() {
        return userId;
    }

    /**
     * 设置用户ID 的值
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取 操作者 的值
     * @return String 
     */
    @Column(name = "s_operator")
    public String  getOperator() {
        return operator;
    }

    /**
     * 设置操作者 的值
     * @param operator 操作者
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name = "n_type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取 操作时间 的值
     * @return Date 
     */
    @Column(name = "d_operate_time")
    public Date  getOperateTime() {
        return operateTime;
    }

    /**
     * 设置操作时间 的值
     * @param operateTime 操作时间
     */
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * 获取 禁用理由 的值
     * @return String 
     */
    @Column(name = "s_reason")
    public String  getReason() {
        return reason;
    }

    /**
     * 设置禁用理由 的值
     * @param reason 禁用理由
     */
    public void setReason(String reason) {
        this.reason = reason;
    }


}