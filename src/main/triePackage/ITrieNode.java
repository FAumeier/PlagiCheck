package triePackage;

import actionsPackage.IActionAtInsert;

import java.util.Iterator;

/**
 * Created by Matthias on 19.03.2016.
 */
public interface ITrieNode {
    ITrieReference recursiveInsert(Iterator key, IActionAtInsert value);
}
