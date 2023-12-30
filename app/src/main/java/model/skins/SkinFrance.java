package model.skins;

import java.util.ArrayList;

import externData.OurColors;

public class SkinFrance extends Skin {

    private static final int COLOR_LENGTH = 3;

    private SkinFrance(OurColors head_color, ArrayList<OurColors> tail_pattern) {
        super(head_color, tail_pattern);
    }
    
    public static SkinFrance build() {
        OurColors color = OurColors.BLUE;
        
        ArrayList<OurColors> tail_pattern = new ArrayList<OurColors>();

        for (int i = 0; i < COLOR_LENGTH; i++) {
            tail_pattern.add(OurColors.BLUE);
        }
        for (int i = 0; i < COLOR_LENGTH; i++) {
            tail_pattern.add(OurColors.WHITE);
        }
        for (int i = 0; i < COLOR_LENGTH; i++) {
            tail_pattern.add(OurColors.RED);
        }
        return new SkinFrance(color, tail_pattern);
    }
}
