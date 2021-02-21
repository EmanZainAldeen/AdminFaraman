package com.faraman_app.admin_faraman.model;

import java.io.Serializable;

public class CostAdmin implements Serializable {
    private String id;
    private String createAt;
    private int minutesFree;//بعد كم وقت يجي للمحامي يحدد السعر الاستشارة
    private int costFree;//  المصاري الموجودة في المحفظة بشكل مجاني للمرة الاولى
    private int numberOfTrying;//  عدد المحاولات المسموح بها في استخدام الدقائق المجانية
    private int minutesFreeATF;// بعد كم وقت يجي للمحامي يحدد السعر الاستشارة بعد المحاولات المسموح فيها

    public CostAdmin() {
    }

    public CostAdmin(String id, String createAt, int minutesFree, int costFree, int numberOfTrying, int minutesFreeATF)
    {
        this.id = id;
        this.createAt = createAt;
        this.minutesFree = minutesFree;
        this.costFree = costFree;
        this.numberOfTrying = numberOfTrying;
        this.minutesFreeATF = minutesFreeATF;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getMinutesFree() {
        return minutesFree;
    }

    public void setMinutesFree(int minutesFree) {
        this.minutesFree = minutesFree;
    }

    public int getCostFree() {
        return costFree;
    }

    public void setCostFree(int costFree) {
        this.costFree = costFree;
    }

    public int getNumberOfTrying() {
        return numberOfTrying;
    }

    public void setNumberOfTrying(int numberOfTrying) {
        this.numberOfTrying = numberOfTrying;
    }

    public int getMinutesFreeATF() {
        return minutesFreeATF;
    }

    public void setMinutesFreeATF(int minutesFreeATF) {
        this.minutesFreeATF = minutesFreeATF;
    }
}
