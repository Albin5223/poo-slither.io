package model.skins;

import java.util.ArrayList;

import externData.OurColors;
import interfaces.Orientation;

public class SkinOne<Type extends Number & Comparable<Type>, O extends Orientation<O>> extends Skin<Type,O>{

    public SkinOne(OurColors head_color, ArrayList<OurColors> tail_pattern, String subFolder) {
        super(head_color, tail_pattern, subFolder);
    }
    
}
