package com.tty.data.dto;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;

/**
 * Created by Administrator on 2017/12/8.
 */
public class BuyQuantityErrorWrapDTO {

    private String currentDate;

    private List<String> dateList = new ArrayList<>();

    private List<BuyQuantityErrorDataDTO> matches;

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public List<String> getDateList() {
        return dateList;
    }

    public void setDateList(List<String> dateList) {
        this.dateList = dateList;
    }

    public List<BuyQuantityErrorDataDTO> getMatches() {
        if(CollectionUtils.isEmpty(matches)) {
            return new ArrayList<>();
        }
        return matches;
    }

    public void setMatches(List<BuyQuantityErrorDataDTO> matches) {
        this.matches = matches;
    }
}
