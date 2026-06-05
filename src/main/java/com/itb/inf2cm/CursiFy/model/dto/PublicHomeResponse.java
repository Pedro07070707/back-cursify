package com.itb.inf2cm.CursiFy.model.dto;

import java.util.List;

public class PublicHomeResponse {

    private long courseCount;
    private long userCount;
    private long professorCount;
    private List<FeaturedCourseResponse> featuredCourses;

    public PublicHomeResponse() {
    }

    public PublicHomeResponse(long courseCount, long userCount, long professorCount, List<FeaturedCourseResponse> featuredCourses) {
        this.courseCount = courseCount;
        this.userCount = userCount;
        this.professorCount = professorCount;
        this.featuredCourses = featuredCourses;
    }

    public long getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(long courseCount) {
        this.courseCount = courseCount;
    }

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }

    public long getProfessorCount() {
        return professorCount;
    }

    public void setProfessorCount(long professorCount) {
        this.professorCount = professorCount;
    }

    public List<FeaturedCourseResponse> getFeaturedCourses() {
        return featuredCourses;
    }

    public void setFeaturedCourses(List<FeaturedCourseResponse> featuredCourses) {
        this.featuredCourses = featuredCourses;
    }
}
