package com.tochalumni.osat.osat;

/**
 * Created by joji on 28/11/17.
 */

class Person {
    private String fname;
    private String lname;
    private String pass;
    private String stud;
    private String insti;
    private String phone;
    private String email;

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPass() { return pass; }

    public String getStud() {
        return stud;
    }

    public String getInsti() {
        return insti;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setPass(String pass) { this.pass = pass; }

    public void setStud(String stud) {
        this.stud = stud;
    }

    public void setInsti(String insti) {
        this.insti = insti;
    }

    public void setPhone(String phone) { this.phone = phone; }

    public void setEmail(String email) { this.email = email; }

}

