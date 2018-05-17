package com.example.lenovo.cmps297n.Posts;

/**
 * Created by Lenovo on 20/11/2017.
 *
 * object that represents a warning
 */

public class Warning {



    private Severity severity;
    private String fromManager;
    private String content;
    private String toEmployee;

    public Warning() {
    }
    public Warning(String fromManager, String content,Severity severity,String toEmployee) {
        this.fromManager = fromManager;
        this.content = content;
        this.severity=severity;
        this.toEmployee=toEmployee;

    }

    public String getToEmployee() {
        return toEmployee;
    }

    public void setToEmployee(String toEmployee) {
        this.toEmployee = toEmployee;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public String getFromManager() {
        return fromManager;
    }

    public void setFromManager(String fromManager) {
        this.fromManager = fromManager;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
