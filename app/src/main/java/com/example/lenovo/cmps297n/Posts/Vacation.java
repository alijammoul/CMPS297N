package com.example.lenovo.cmps297n.Posts;

/**
 * Created by Lenovo on 20/11/2017.
 * represents a vacation request object
 */

public class Vacation {
    private VacType type;
    private String reason;
    private String startdate;
    private String enddate;
    private String employee;
    private VacStatus status;
    public Vacation() {
    }

    public Vacation(VacType type,VacStatus status, String reason, String startdate,String enddate,String employee) {
        this.type = type;
        this.reason = reason;
        this.startdate = startdate;
        this.enddate=enddate;
        this.employee=employee;
        this.status=status;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public void setType(VacType type) {
        this.type = type;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public VacType getType() {
        return type;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getEmployee() {
        return employee;
    }

    public String getReason() {
        return reason;
    }



    public VacStatus getStatus() {
        return status;
    }

    public void setStatus(VacStatus status) {
        this.status = status;
    }
}
