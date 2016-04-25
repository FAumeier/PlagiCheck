package triePackage;

import actionsPackage.IActionAtInsert;

import java.util.Iterator;

/**
 * Created by Matthias on 19.03.2016.
 */
public interface ITrie {
    public ITrieReference insert(Iterator key, IActionAtInsert value);
    public ITrieReference insert(String str) throws IllegalStateException;
    public ITrieReference insert(String str, IActionAtInsert value);
}
