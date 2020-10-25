package com.tty.user.controller.model.result;/**
 * Created by shenwei on 2016/12/27.
 */

/**
 * @author shenwei
 * @create 2016-12-27
 */
public class UserSignInModel {
    private Long Integrals;
    private Integer IntegralsToAdd;
    private Integer continiousDays;

    public Long getIntegrals() {
        return Integrals;
    }

    public void setIntegrals(Long integrals) {
        Integrals = integrals;
    }

    public Integer getIntegralsToAdd() {
        return IntegralsToAdd;
    }

    public void setIntegralsToAdd(Integer integralsToAdd) {
        IntegralsToAdd = integralsToAdd;
    }

    public Integer getContiniousDays() {
        return continiousDays;
    }

    public void setContiniousDays(Integer continiousDays) {
        this.continiousDays = continiousDays;
    }
}
