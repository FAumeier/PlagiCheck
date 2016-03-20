package mapPackage;

import java.util.Map;

/**
 * Created by Matthias on 19.03.2016.
 */
public class TreeMapFactory implements IMapFactory {
    @Override
    public Map create() {
        return (Map)new TreeMapFactory();
    }
}
