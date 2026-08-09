package com.library.model;

public class Member {
    private String memberId;
    private String name;
    private String contactNumber;

    public Member(String memberId, String name, String contactNumber){
        this.memberId = memberId;
        this.name = name;
        this.contactNumber = contactNumber;
    }

    public String getMemberId(){
        return memberId;
    }

    public String getName(){
        return name;
    }

    public String getContactNumber(){
        return contactNumber;
    }

    @Override
    public String toString(){
        return "Member ID: " + memberId + " | Name: " + name + " | Contact: " + contactNumber;
    }
}
