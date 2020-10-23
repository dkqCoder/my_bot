package com.tty.ticket.dao.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 出票配置
 **/

@Entity
@Table(name = "ticket_config")
public class TicketConfigENT implements Serializable {

    /** 编码 */
    private String code;

    /** 值 */
    private String value;

    /** 类型 */
    private String type;

    /** 描述 */
    private String comment;




    /**
     * 获取 编码 的值
     * @return String 
     */
    @Id
    @Column(name = "s_code")
    public String  getCode() {
        return code;
    }

    /**
     * 设置编码 的值
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取 值 的值
     * @return String 
     */
    @Column(name = "s_value")
    public String  getValue() {
        return value;
    }

    /**
     * 设置值 的值
     * @param value 值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取 类型 的值
     * @return String 
     */
    @Column(name = "s_type")
    public String  getType() {
        return type;
    }

    /**
     * 设置类型 的值
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取 描述 的值
     * @return String 
     */
    @Column(name = "s_comment")
    public String  getComment() {
        return comment;
    }

    /**
     * 设置描述 的值
     * @param comment 描述
     */
    public void setComment(String comment) {
        this.comment = comment;
    }



}