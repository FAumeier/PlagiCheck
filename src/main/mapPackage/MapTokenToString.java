package mapPackage;

import framework.IToken;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Flo on 05.04.2016.
 */
public class MapTokenToString implements IMapTokenToString {
    ConcurrentHashMap<IToken, String> mConcurrentHashMap = new ConcurrentHashMap<>();
    @Override
    public void put(IToken tk, String s) {
        mConcurrentHashMap.put(tk, s);
    }

    @Override
    public String get(IToken tk) {
        String tokenToString = mConcurrentHashMap.get(tk);
        return tokenToString;
    }

}
