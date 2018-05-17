package com.example.lenovo.cmps297n.Posts;

/**
 * Created by Lenovo on 18/11/2017.
 * Represents a company worker object
 */

public class CompanyWorker {

  private String useremail;
  private boolean check_status;
  private String name;

    public CompanyWorker(String name,String useremail, boolean check_status) {
        this.useremail = useremail;
        this.check_status = check_status;
        this.name=name;

    }
    public CompanyWorker(){}

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public void setCheck_status(boolean check_status) {
        this.check_status = check_status;
    }

    public String getUseremail() {
        return useremail;
    }

    public boolean getCheck_status() {
        return check_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
