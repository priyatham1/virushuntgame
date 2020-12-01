package com.ps.vh.model;

import androidx.annotation.Keep;

import com.ps.vh.util.GeoHashUtil;

@Keep
public class TargetArea {
    private String geo;
    private double lt;
    private double ln;
    private String name;
    private int count;
    private String geo_alive;
    private String parentname;
    private String type;
    private TargetArea(){
    }
    public TargetArea(String name, double lt, double ln ){
        count = 1;
        this.name = name;
        this.lt = lt;
        this.ln = ln;
        this.geo = GeoHashUtil.encode(lt, ln);
        this.geo_alive = geo;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public double getLt() {
        return lt;
    }

    public void setLt(double lt) {
        this.lt = lt;
    }

    public double getLn() {
        return ln;
    }

    public void setLn(double ln) {
        this.ln = ln;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getGeo_alive() {
        return geo_alive;
    }

    public void setGeo_alive(String geo_alive) {
        this.geo_alive = geo_alive;
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
