package com.tty.user.dao.pojo;/**
 * Created by shenwei on 2017/1/5.
 */

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author shenwei
 * @date 2017-01-05
 * @version v1.0
 * 说明：表【user_level_authority】实体类，由hibernateToolv1.0工具自动生成
 */
@Entity
@Table(name = "user_level_authority")
public class UserLevelAuthority implements java.io.Serializable {

    private Integer id;
    private Integer level;
    private String authorityName;
    private String authorityDescription;
    private BigDecimal authorityValue;
    private Integer category;

    @Id
    @Column(name = "n_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "n_level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(name = "s_authority_name")
    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    @Column(name = "s_authority_description")
    public String getAuthorityDescription() {
        return authorityDescription;
    }

    public void setAuthorityDescription(String authorityDescription) {
        this.authorityDescription = authorityDescription;
    }

    @Column(name = "n_authority_value")
    public BigDecimal getAuthorityValue() {
        return authorityValue;
    }

    public void setAuthorityValue(BigDecimal authorityValue) {
        this.authorityValue = authorityValue;
    }

    @Column(name = "n_category")
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
