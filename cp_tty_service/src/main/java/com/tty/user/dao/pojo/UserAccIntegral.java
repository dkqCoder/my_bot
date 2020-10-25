package com.tty.user.dao.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenlongfei
 * @date 2016-12-12
 * @version v1.0
 * 说明：表【user_acc_integral】实体类，由hibernateToolv1.0工具自动生成
 */
@Entity
@Table(name = "user_acc_integral")
public class UserAccIntegral implements java.io.Serializable {

    private Long userId;
    private Long userIntegral;

    @Id
    @Column(name = "n_user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "n_user_integral")
    public Long getUserIntegral() {
        return userIntegral;
    }

    public void setUserIntegral(Long userIntegral) {
        this.userIntegral = userIntegral;
    }

}
