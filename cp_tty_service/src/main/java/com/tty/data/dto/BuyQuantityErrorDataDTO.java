package com.tty.data.dto;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;

/**
 * Created by rxu on 2017/12/1.
 */
public class BuyQuantityErrorDataDTO {
    /**
     * 序号
     */
    private String matchNo;
    /**
     * 主队
     */
    private String hostTeamName;
    /**
     * 客队
     */
    private String visitingTeamName;
    /**
     * 比赛日期
     */
    private String jzWeek;

    /**
     * 买量误差详情
     */
    private List<BuyQuantityErrorDetailDTO> bqes;

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public String getHostTeamName() {
        return hostTeamName;
    }

    public void setHostTeamName(String hostTeamName) {
        this.hostTeamName = hostTeamName;
    }

    public String getVisitingTeamName() {
        return visitingTeamName;
    }

    public void setVisitingTeamName(String visitingTeamName) {
        this.visitingTeamName = visitingTeamName;
    }

    public String getJzWeek() {
        return jzWeek;
    }

    public void setJzWeek(String jzWeek) {
        this.jzWeek = jzWeek;
    }

    public List<BuyQuantityErrorDetailDTO> getBqes() {
        if(CollectionUtils.isEmpty(bqes)){
            return new ArrayList<>();
        }
        return bqes;
    }

    public void setBqes(List<BuyQuantityErrorDetailDTO> bqes) {
        this.bqes = bqes;
    }
}
