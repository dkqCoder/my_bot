package com.tty.data.dto;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;

/**
 * Created by rxu on 2017/11/23.
 */
public class PredictedIssueDataDTO {

    private String issueName;

    private String currentDate;

    private String hitRate ="";

    private String returnRate ="";

    private List<String> dateList = new ArrayList<>();

    private List<PredictedMatchDTO> matches;

    public PredictedIssueDataDTO() {
    }

    public PredictedIssueDataDTO(String issueName, List<PredictedMatchDTO> matches) {
        this.issueName = issueName;
        this.matches = matches;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public List<String> getDateList() {
        if(CollectionUtils.isEmpty(dateList)) {
            return new ArrayList<>();
        }
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public List<PredictedMatchDTO> getMatches() {
        if(CollectionUtils.isEmpty(matches)) {
            return new ArrayList<>();
        }
        return matches;
    }

    public void setMatches(List<PredictedMatchDTO> matches) {
        this.matches = matches;
    }

    public String getHitRate() {
        return hitRate;
    }

    public void setHitRate(String hitRate) {
        this.hitRate = hitRate;
    }

    public String getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(String returnRate) {
        this.returnRate = returnRate;
    }

}
