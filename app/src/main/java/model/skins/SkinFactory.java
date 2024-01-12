package model.skins;

public class SkinFactory {

    public enum SkinType {
        RANDOM, FRANCE, GERMANY, ITALY
    }
    
    public static Skin build(SkinType type) {
        switch (type) {
            case FRANCE:
                return SkinFlag.buildFrance();
            case GERMANY:
                return SkinFlag.buildGermany();
            case ITALY:
                return SkinFlag.buildItaly();
            default:
                return SkinRandom.build();
        }
    }

}
