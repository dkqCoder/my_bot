package com.tty.user.dao.pojo;/**
 * Created by shenwei on 2017/1/5.
 */
import javax.persistence.*;

/**
 * @author shenwei
 * @date 2017-01-05
 * @version v1.0
 * 说明：表【user_level_mapping】实体类，由hibernateToolv1.0工具自动生成
 */
@Entity
@Table(name = "user_level_mapping")
public class UserLevelMapping implements java.io.Serializable {

    private Integer id;
    private Integer level;
    private Long growupMin;
    private Long growupMax;
    private String remark;

    @Id
    @Column(name = "n_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId(){return id;}

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

    @Column(name = "n_growup_min")
    public Long getGrowupMin() {
        return growupMin;
    }

    public void setGrowupMin(Long growupMin) {
        this.growupMin = growupMin;
    }

    @Column(name = "n_growup_max")
    public Long getGrowupMax() {
        return growupMax;
    }

    public void setGrowupMax(Long growupMax) {
        this.growupMax = growupMax;
    }

    @Column(name = "s_remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
