package model.skins;

import java.util.ArrayList;

import externData.OurColors;
import interfaces.Orientation;

public abstract class Skin<Type extends Number & Comparable<Type>, O extends Orientation<O>> {

    private final OurColors head_color;
    private final ArrayList<OurColors> tail_pattern;

    public Skin(OurColors head_color, ArrayList<OurColors> tail_pattern) {
        this.head_color = head_color;
        this.tail_pattern = new ArrayList<OurColors>(tail_pattern);
    }

    public OurColors getHeadColor() {
        return head_color;
    }

    public ArrayList<OurColors> getTailPattern() {
        return new ArrayList<OurColors>(tail_pattern);
    }
}