package externData;

public enum OurSpecials {
    POISON, SHIELD, SKULL;

    @Override
    public String toString(){
        return this.name().toLowerCase();
    }
}
