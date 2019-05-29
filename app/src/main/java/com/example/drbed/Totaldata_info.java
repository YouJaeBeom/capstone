package com.example.drbed;

import java.util.ArrayList;
import java.util.List;

public class Totaldata_info {
    List<String> HRList = new ArrayList<String>();
    List<String> RRList = new ArrayList<String>();
    List<String> SVList = new ArrayList<String>();
    List<String> HRVList = new ArrayList<String>();
    public int getHRList(int i) {
        return  Integer.parseInt(HRList.get(i));
    }

    public void setHRList(String HR) {
        HRList.add(HR);
    }
    public int getRRList(int i) {
        return Integer.parseInt(RRList.get(i));
    }

    public void setRRList(String RR) {
        RRList.add(RR);
    }
    public int getSVList(int i) {
        return Integer.parseInt(SVList.get(i));
    }

    public void setSVList(String SV) {
        SVList.add(SV);
    }
    public int getHRVList(int i) {
        return Integer.parseInt(HRVList.get(i));
    }

    public void setHRVList(String HRV) {
        HRVList.add(HRV);
    }

    public int getTotalLength(){
        return HRList.size();
    }






}
