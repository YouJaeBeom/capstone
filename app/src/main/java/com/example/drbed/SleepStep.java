package com.example.drbed;

import java.util.ArrayList;
import java.util.List;

public class SleepStep {
    List<String> IDList = new ArrayList<String>();
    List<String> TimeList = new ArrayList<String>();
    List<String> Sleep_stepList = new ArrayList<String>();

    public String getIDList(int i) {
        return  IDList.get(i);
    }

    public void setIDList(String ID) {
        IDList.add(ID);
    }

    public String getTimeList(int i) {
        return TimeList.get(i);
    }

    public void setTimeList(String Time) {
        TimeList.add(Time);
    }

    public String getSleep_stepList(int i) {
        return Sleep_stepList.get(i);
    }

    public void setSleep_stepList(String Sleep_step) {
        Sleep_stepList.add(Sleep_step);
    }


    public int getTotalLength(){
        return TimeList.size();
    }

}
