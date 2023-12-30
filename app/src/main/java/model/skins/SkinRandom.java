package model.skins;

import java.util.ArrayList;

import externData.OurColors;

public class SkinRandom extends Skin {

    private SkinRandom(OurColors head_color, ArrayList<OurColors> tail_pattern) {
        super(head_color, tail_pattern);
    }

    public static SkinRandom build() {
        OurColors color = OurColors.getRandomColor();
        
        ArrayList<OurColors> tail_pattern = new ArrayList<OurColors>();
        tail_pattern.add(color);
        return new SkinRandom(color, tail_pattern);
    }
    
}
