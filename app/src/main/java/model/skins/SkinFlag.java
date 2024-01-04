package model.skins;

import java.util.ArrayList;

import externData.OurColors;

public class SkinFlag extends Skin {

    private static final int COLOR_LENGTH = 3;

    public SkinFlag(OurColors head_color, ArrayList<OurColors> tail_pattern) {
        super(head_color, tail_pattern);
    }

    private static SkinFlag build(OurColors c1 , OurColors c2 , OurColors c3) {
        OurColors color = c1;
        
        ArrayList<OurColors> tail_pattern = new ArrayList<OurColors>();

        for (int i = 0; i < COLOR_LENGTH; i++) {
            tail_pattern.add(c1);
        }
        for (int i = 0; i < COLOR_LENGTH; i++) {
            tail_pattern.add(c2);
        }
        for (int i = 0; i < COLOR_LENGTH; i++) {
            tail_pattern.add(c3);
        }
        return new SkinFlag(color, tail_pattern);
    }

    public static SkinFlag buildFrance() {
        return build(OurColors.BLUE, OurColors.WHITE, OurColors.RED);
    }

    public static SkinFlag buildGermany() {
        return build(OurColors.GRAY, OurColors.RED, OurColors.YELLOW);
    }

    public static SkinFlag buildItaly() {
        return build(OurColors.GREEN, OurColors.WHITE, OurColors.RED);
    }
    
}
