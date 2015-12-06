package com.darkphoton.data_visualiser_project.data.processed.indicators.positive;

import com.darkphoton.data_visualiser_project.data.processed.indicators.PositiveIndicator;
import com.darkphoton.data_visualiser_project.data.raw.RIndicator;

public class WaterSourcesUrban extends PositiveIndicator {
    public final static String id = "SH.H2O.SAFE.UR.ZS";
    public final static String name = "Improved Water Sources - Urban";
    public final static String title = "Improved Water Sources - Urban (% of population with access)";

    public WaterSourcesUrban(RIndicator indicator){
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
