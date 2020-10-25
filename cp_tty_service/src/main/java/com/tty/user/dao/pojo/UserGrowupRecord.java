package com.tty.user.dao.pojo;/**
 * Created by shenwei on 2017/1/4.
 */

import javax.persistence.*;
import java.util.Date;

/**
 * @author shenwei
 * @date 2017-01-04
 * @version v1.0
 * 说明：表【user_growup_record】实体类，由hibernateToolv1.0工具自动生成
 */
@Entity
@Table(name = "user_growup_record")
public class UserGrowupRecord implements java.io.Serializable {

    private Long id;
    private Long userId;
    private Long growups;
    private String description;
    private Date createDate;

    @Id
    @Column(name = "n_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "n_growups")
    public Long getGrowups() {
        return growups;
    }

    public void setGrowups(Long growups) {
        this.growups = growups;
    }

    @Column(name = "s_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "d_create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
