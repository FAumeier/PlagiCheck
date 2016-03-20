package triePackage;

import actionsPackage.IActionAtInsert;
import mapPackage.IMapFactory;

import java.util.Iterator;

/**
 * Created by Matthias on 19.03.2016.
 */
public class Trie implements ITrie {
    ITrieNode root;
    IMapFactory mapFactory;

    public Trie(IMapFactory mapFactory) {
        this.mapFactory = mapFactory;

    }

    @Override
    public ITrieReference insert(Iterator key, IActionAtInsert value) {
        return null;
    }

    @Override
    public ITrieReference insert(String str, IActionAtInsert value) {
        return null;
    }
}
