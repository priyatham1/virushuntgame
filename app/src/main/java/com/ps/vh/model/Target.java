package com.ps.vh.model;

import androidx.annotation.Keep;

import com.ps.vh.util.GeoHashUtil;

@Keep
public class Target {
    private String geo;
    private double lt;
    private double ln;
    private boolean alive;
    private String geo_alive;
    private String killedby;
    private String country;
    private String state;
    private String city;

    public Target(){
    }

    public Target(double lt, double ln, boolean alive){
        this.lt = lt;
        this.ln = ln;
        this.alive = alive;
        this.geo = GeoHashUtil.encode(lt, ln);
        if(alive){
            this.geo_alive = geo;
        } else {
            this.geo_alive="ZZZZZZZZZZZZZZ";
        }
    }

    public String getGeo_alive() {
        return geo_alive;
    }

    public void setGeo_alive(String geo_alive) {
        this.geo_alive = geo_alive;
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

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getKilledby() {
        return killedby;
    }

    public void setKilledby(String killedby) {
        this.killedby = killedby;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
