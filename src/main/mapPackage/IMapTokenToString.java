package mapPackage;

import framework.IToken;

/*
 *
 */
public interface IMapTokenToString {
    public void put(IToken tk, String s);
    public String get(IToken tk);
}
