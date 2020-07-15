package com.innopolis.innometrics.activitiescollector.DTO;

public class CategoriesReport implements ICategoriesReport {
    private String catname;
    private String catdescription;
    private String timeused;

    public CategoriesReport() {
    }

    public CategoriesReport(String catname, String catdescription, String timeused) {
        this.catname = catname;
        this.catdescription = catdescription;
        this.timeused = timeused;
    }

    @Override
    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    @Override
    public String getCatdescription() {
        return catdescription;
    }

    public void setCatdescription(String catdescription) {
        this.catdescription = catdescription;
    }

    @Override
    public String getTimeused() {
        return timeused;
    }

    public void setTimeused(String timeused) {
        this.timeused = timeused;
    }
}
