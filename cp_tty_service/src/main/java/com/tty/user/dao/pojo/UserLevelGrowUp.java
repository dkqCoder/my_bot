package com.tty.user.dao.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author LiuJin
 * @date 2017-01-13
 * @version v1.0
 * 说明：表【user_level_growup】实体类，由hibernateToolv1.0工具自动生成
 */
@Entity
@Table(name = "user_level_growup")
public class UserLevelGrowUp implements java.io.Serializable {

    private Long userId;
    private Long userGrowups;
    private Integer userLevel;
    private Date levelChangetime;

    @Id
    @Column(name = "n_user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "n_user_growups")
    public Long getUserGrowups() {
        return userGrowups;
    }

    public void setUserGrowups(Long userGrowups) {
        this.userGrowups = userGrowups;
    }

    @Column(name = "n_user_level")
    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    @Column(name = "d_level_changetime")
    public Date getLevelChangetime() {
        return levelChangetime;
    }

    public void setLevelChangetime(Date levelChangetime) {
        this.levelChangetime = levelChangetime;
    }
}
