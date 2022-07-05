package com.lt.dom.oct;

public enum EnumEmployeeAccessLevel {

    No_access("No access", 0),
    Minimal_access("Minimal access", 5),// (Introduced in GitLab 13.5.)
    Guest("Guest", 10),
    Reporter("Reporter", 20),
    Developer("Developer", 30),
    Maintainer("Maintainer", 40),
    Owner("Owner", 50);//. Valid for projects in GitLab 14.9 and later.


    EnumEmployeeAccessLevel(String s, int i) {

    }
}
