package framework;

/**
 * Created by Matthias on 06.05.2016.
 */
public class SimpleSelector implements ISelector {
    private IRegion region;

    public SimpleSelector(IRegion region) {
        this.region = region;
    }

    @Override
    public IRegion getRegion() {
        return region;
    }
}
