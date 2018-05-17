package com.example.lenovo.cmps297n.Posts;

/**
 * Created by Lenovo on 26/11/2017.
 *
 * This class is an object that represents the check in times of the employee
 */

public class Checks {

    private String CheckType;
    private String CheckTime;


    public Checks(String CheckType,String CheckTime){

        this.CheckTime=CheckTime;
        this.CheckType=CheckType;
    }
public Checks(){}
    public String getCheckType() {
        return CheckType;
    }

    public void setCheckType(String checkType) {
        CheckType = checkType;
    }

    public String getCheckTime() {
        return CheckTime;
    }

    public void setCheckTime(String checkTime) {
        CheckTime = checkTime;
    }
}
