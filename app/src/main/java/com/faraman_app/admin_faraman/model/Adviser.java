package com.faraman_app.admin_faraman.model;

import java.io.Serializable;

public class Adviser implements Serializable {

    private String id;
    private String adviserName;
    private String imageURL;
    private String email;
    private String experience;
    private String phoneNumber;
    private String location;
    private String status;
    private String major;
    private String bio;
    private String search;
    private boolean isStar;

    public Adviser() {
    }

    public Adviser(String id, String adviserName, String imageURL,
                   String email, String experience, String phoneNumber, String location,
                   String status, String major, String bio, String search, boolean isStar) {
        this.id = id;
        this.adviserName = adviserName;
        this.imageURL = imageURL;
        this.email = email;
        this.experience = experience;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.status = status;
        this.major = major;
        this.bio = bio;
        this.search = search;
        this.isStar = isStar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdviserName() {
        return adviserName;
    }

    public void setAdviserName(String adviserName) {
        this.adviserName = adviserName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean star) {
        isStar = star;
    }
}
