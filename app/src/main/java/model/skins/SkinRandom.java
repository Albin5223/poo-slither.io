package model.skins;

import java.util.ArrayList;

import externData.OurColors;
import interfaces.Orientation;

public class SkinRandom<Type extends Number & Comparable<Type>, O extends Orientation<O>> extends Skin<Type, O> {

    private SkinRandom(OurColors head_color, ArrayList<OurColors> tail_pattern) {
        super(head_color, tail_pattern);
    }

    public static <Type extends Number & Comparable<Type>, O extends Orientation<O>> SkinRandom<Type,O> build() {
        OurColors color = OurColors.getRandomColor();
        
        ArrayList<OurColors> tail_pattern = new ArrayList<OurColors>();
        tail_pattern.add(color);
        return new SkinRandom<Type,O>(color, tail_pattern);
    }
    
}
