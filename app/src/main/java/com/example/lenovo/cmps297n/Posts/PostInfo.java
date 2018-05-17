package com.example.lenovo.cmps297n.Posts;

/**
 * Created by Lenovo on 16/11/2017.
 * represents what the employee posts on company page or timeline
 */

public class PostInfo {

    String complaint;
    int position;
    String user;
    String date;


    public PostInfo(){}
    public PostInfo(String complaint,int position,String user,String date){
        this.complaint=complaint;this.user=user;
        this.position=position;this.date=date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComplaint() {
        return complaint;
    }

    public int getPosition() {
        return position;
    }

    public String getUser() {
        return user;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
