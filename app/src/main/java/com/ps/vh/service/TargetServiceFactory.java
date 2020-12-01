package com.ps.vh.service;

import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

public class TargetServiceFactory {
    private static TargetService singleInstance;
    public static TargetService getInstance(){
        if(singleInstance == null){
            singleInstance = new TargetService();
            singleInstance.markerMap = new HashMap<String, Marker>();
        }
        return singleInstance;
    }
}
