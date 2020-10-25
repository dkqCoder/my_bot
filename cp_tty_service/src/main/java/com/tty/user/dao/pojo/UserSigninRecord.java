package com.tty.user.dao.pojo;

import java.util.Date;
import javax.persistence.*;

/**
 * @author shenwei
 * @date 2016-12-14
 * @version v1.0
 * 说明：表【user_signin_record】实体类，由hibernateToolv1.0工具自动生成
 */
@Entity
@Table(name = "user_signin_record")
public class UserSigninRecord implements java.io.Serializable {

    private Long id;
    private Long userId;
    private Date userDate;
    private Long signDays;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "n_user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "d_user_date")
    public Date getUserDate() {
        return userDate;
    }

    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }

    @Column(name="n_sign_days")
    public Long getSignDays(){return signDays;}

    public void setSignDays(Long signDays){ this.signDays = signDays; }
}
