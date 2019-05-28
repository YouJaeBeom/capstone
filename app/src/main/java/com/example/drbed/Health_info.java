package com.example.drbed;

public class Health_info {
        String Time;
        float HR;
    float RR;
    float SV;
    float HRV;
    float Signal_Strength;
    float Status ;
    public String getTime() {
        return Time;
    }
    public void setTime(String Time) {
        this.Time = Time;
    }

        public float getHR() {
            return HR;
        }

        public void setHR(String HR) {
            this.HR = Float.parseFloat(HR);
        }

        public float getRR() {
            return RR;
        }

        public void setRR(String RR) {
            this.RR = Float.parseFloat(RR);
        }

        public float getSV() {
            return SV;
        }

        public void setSV(String SV) {
            this.SV = Float.parseFloat(SV);
        }

    public float getHRV() {
        return HRV;
    }

    public void setHRV(String HRV) {
        this.HRV = Float.parseFloat(HRV);
    }

    public float getSignal_Strength() {
        return Signal_Strength;
    }

    public void setSignal_Strength(String Signal_Strength) {
        this.Signal_Strength = Float.parseFloat(Signal_Strength);
    }

    public float getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Float.parseFloat(Status);
    }



}
