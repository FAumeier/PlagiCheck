package mapPackage;

import framework.IToken;

/*
 *
 */
interface IMapTokenToString {
    void put(IToken tk, String s);
    String get(IToken tk);
}
