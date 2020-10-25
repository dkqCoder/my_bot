package com.tty.user.dao.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * @author shenwei
 * @date 2016-12-23
 * @version v1.0
 * 说明：表【user_integral_records】实体类，由hibernateToolv1.0工具自动生成
 */
@Entity
@Table(name = "user_integral_records")
public class UserIntegralRecords implements java.io.Serializable {

    private Long id;
    private Long userid;
    private Integer type;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private Integer count;
    private Long sourceid;
    private String description;

    @Id
    @Column(name = "n_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "n_userid")
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    @Column(name = "n_type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "d_create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "n_count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Column(name = "n_sourceid")
    public Long getSourceid() {
        return sourceid;
    }

    public void setSourceid(Long sourceid) {
        this.sourceid = sourceid;
    }

    @Column(name = "s_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
