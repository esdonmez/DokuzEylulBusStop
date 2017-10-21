package com.esdonmez.esd.dokuzeylulbusstop.Models;

import java.io.Serializable;

public class SurveyModel implements Serializable {
    private String advice;
    private String email;
    private String likeDegree;
    private String busStop;

    public SurveyModel(String advice, String email, String likeDegree, String busStop) {
        this.advice = advice;
        this.email = email;
        this.likeDegree = likeDegree;
        this.busStop = busStop;
    }

    public String getAdvice() {
        return advice;
    }

    public String getEmail() {
        return email;
    }

    public String getLikeDegree() {
        return likeDegree;
    }

    public String getBusStop() {
        return busStop;
    }
}
