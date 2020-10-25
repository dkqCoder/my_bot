package com.tty.user.common;
import java.util.List;

/**
 * Created by linian on 2018/1/16.
 */
public class EmailModel {
    //发给谁
    private List<String> toEmail;
    //谁发的
    private String fromEmail;
    //主题
    private String subject;
    //内容
    private String text;
    public EmailModel(){}

    public EmailModel(List<String> toEmail, String fromEmail, String subject, String text) {
        this.toEmail = toEmail;
        this.fromEmail = fromEmail;
        this.subject = subject;
        this.text = text;
    }

    public List<String> getToEmail() {
        return toEmail;
    }

    public void setToEmail(List<String> toEmail) {
        this.toEmail = toEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
