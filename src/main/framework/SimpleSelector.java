package framework;

/**
 * Created by Matthias on 06.05.2016.
 */
public class SimpleSelector implements ISelector {
    private IRegion region;

    public SimpleSelector(ITokenSequence sequence1, ITokenSequence sequence2) {
        region = new Region(0, sequence1.length(), 0, sequence2.length());
    }

    @Override
    public IRegion getRegion() {
        return region;
    }
}
