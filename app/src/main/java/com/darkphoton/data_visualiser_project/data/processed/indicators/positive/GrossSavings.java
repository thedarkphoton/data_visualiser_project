package com.darkphoton.data_visualiser_project.data.processed.indicators.positive;

import com.darkphoton.data_visualiser_project.data.processed.indicators.PositiveIndicator;
import com.darkphoton.data_visualiser_project.data.raw.RIndicator;

public class GrossSavings extends PositiveIndicator {
    public final static String id = "NY.GNS.ICTR.CD";
    public final static String name = "Gross Savings";
    public final static String title = "Gross Savings (in US$)";

    public GrossSavings(RIndicator indicator){
        super(indicator);
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getTitle(){
        return title;
    }
}