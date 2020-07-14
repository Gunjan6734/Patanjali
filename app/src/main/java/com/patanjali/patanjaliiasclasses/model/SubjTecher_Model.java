package com.patanjali.patanjaliiasclasses.model;

public class SubjTecher_Model {

    private String cdid,subject,teacher,noofclassess,classduration;

    public SubjTecher_Model(String cdid, String subject, String teacher, String noofclassess, String classduration) {
        this.cdid = cdid;
        this.subject = subject;
        this.teacher = teacher;
        this.noofclassess = noofclassess;
        this.classduration = classduration;
    }

    public String getCdid() {
        return cdid;
    }

    public void setCdid(String cdid) {
        this.cdid = cdid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getNoofclassess() {
        return noofclassess;
    }

    public void setNoofclassess(String noofclassess) {
        this.noofclassess = noofclassess;
    }

    public String getClassduration() {
        return classduration;
    }

    public void setClassduration(String classduration) {
        this.classduration = classduration;
    }
}
