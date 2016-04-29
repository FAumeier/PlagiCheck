package mapPackage;

import framework.ClassCodes;
import framework.IToken;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Flo on 05.04.2016.
 */
public class MapTokenToString implements IMapTokenToString {
    private final ConcurrentHashMap<IToken, String> tokens = new ConcurrentHashMap<>();

    @Override
    public void put(IToken tk, String s) {
        tokens.put(tk, s);
    }

    @Override
    public String get(IToken tk) {
        ClassCodes classCode = tk.getClassCode();
        int relativeCode = tk.getRelativeCode();
        return tokens.get(tk);
    }

}
