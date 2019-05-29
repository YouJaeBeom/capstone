package com.example.drbed;

public class Data {
    String HR;
    String RR;
    String SV;
    String HRV;
    public String getHR() {
        return HR;
    }

    public void setHR(String HR) {
        this.HR = HR;
    }

    public String getRR() {
        return RR;
    }

    public void setRR(String RR) {
        this.RR = RR;
    }

    public String getSV() {
        return SV;
    }

    public void setSV(String SV) {
        this.SV = SV;
    }

    public String getHRV() {
        return HRV;
    }

    public void setHRV(String HRV) {
        this.HRV = HRV;
    }




    public Data(String HR, String RR, String SV,String HRV) {
        this.HR = HR;
        this.RR = RR;
        this.SV = SV;
        this.HRV = HRV;
    }

}
