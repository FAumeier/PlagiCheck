package framework;

/**
 * Created by q253514 on 06.05.2016.
 */
public class Region implements IRegion {
    private int lower1;
    private int upper1;
    private int lower2;
    private int upper2;

    public Region(int lower1, int upper1, int lower2, int upper2) {
        this.lower1 = lower1;
        this.upper1 = upper1;
        this.lower2 = lower2;
        this.upper2 = upper2;
    }

    @Override
    public int getLowerBound1() {
        return lower1;
    }

    @Override
    public int getUpperBound1() {
        return upper1;
    }

    @Override
    public int getLowerBound2() {
        return lower2;
    }

    @Override
    public int getUpperBound2() {
        return upper2;
    }

    @Override
    public String toString() {
        return "Region{" +
                "lower1=" + lower1 +
                ", upper1=" + upper1 +
                ", lower2=" + lower2 +
                ", upper2=" + upper2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Region region = (Region) o;

        if (lower1 != region.lower1) return false;
        if (upper1 != region.upper1) return false;
        if (lower2 != region.lower2) return false;
        return upper2 == region.upper2;

    }

    @Override
    public int hashCode() {
        int result = lower1;
        result = 31 * result + upper1;
        result = 31 * result + lower2;
        result = 31 * result + upper2;
        return result;
    }
}
