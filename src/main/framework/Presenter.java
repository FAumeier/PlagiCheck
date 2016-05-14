package framework;

/**
 * Created by Matthias on 14.05.2016.
 */
public class Presenter implements IPresenter {
    private final IAlignmentMatrix matrix;

    public Presenter(IAlignmentMatrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public String backward() {
        return null;
    }

    @Override
    public String threeColumnOutput() {
        return null;
    }
}
