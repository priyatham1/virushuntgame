package com.ps.vh.http;

import androidx.annotation.Keep;

@Keep
public class GeocodeResp {

    Result[] results;

    public Result[] getResults() {
        return results;
    }

    public void setResults(Result[] results) {
        this.results = results;
    }
}
