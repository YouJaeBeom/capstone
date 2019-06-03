package com.example.drbed;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SleepStep_3 {

    List<String> IDList = new ArrayList<String>();
    List<String> TimeList = new ArrayList<String>();
    List<String> Sleep_stepList = new ArrayList<String>();
    List<String> Sleep_time_diffList= new ArrayList<String>();

    public String getSleep_time_diffList(int i) {
        return  Sleep_time_diffList.get(i);
    }

    public void setSleep_time_diffList(String Sleep_time_diff) {
        Log.e(this.getClass().getName(), "Sleep_time_diff"+Sleep_time_diff);
        Sleep_time_diffList.add(Sleep_time_diff);
    }
    public int getSleep_time_diffListTotalLength(){
        return Sleep_time_diffList.size();
    }

    public String getIDList(int i) {
        return  IDList.get(i);
    }

    public void setIDList(String ID) {
        Log.e(this.getClass().getName(), "ID"+ID);
        IDList.add(ID);
    }

    public String getTimeList(int i) {
        return TimeList.get(i);
    }

    public void setTimeList(String Time) {
        Log.e(this.getClass().getName(), "Time"+Time);
        TimeList.add(Time);
    }

    public String getSleep_stepList(int i) {
        return Sleep_stepList.get(i);
    }

    public void setSleep_stepList(String Sleep_step) {
        Log.e(this.getClass().getName(), "Sleep_step"+Sleep_step);
        Sleep_stepList.add(Sleep_step);
    }


    public int getTotalLength(){
        return TimeList.size();
    }

}
