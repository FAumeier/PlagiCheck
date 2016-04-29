package triePackage;

import actionsPackage.IActionAtInsert;

import java.util.Iterator;

/**
 * Created by Matthias on 19.03.2016.
 */
public interface ITrie {
    ITrieReference insert(Iterator key, IActionAtInsert value);
    ITrieReference insert(String str) throws IllegalStateException;
    ITrieReference insert(String str, IActionAtInsert value);
}
