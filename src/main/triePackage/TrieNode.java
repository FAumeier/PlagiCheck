package triePackage;

import actionsPackage.IActionAtInsert;
import mapPackage.IMapFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Matthias on 19.03.2016.
 */
public class TrieNode implements ITrieNode {
    ITrieNode parent;
    IMapFactory mapFactory;
    PartialKeyType ingoingEdge;
    Map<Comparable, ITrieNode> outgoingEdgeMap;

    public TrieNode(ITrieNode parent, IMapFactory mapFactory) {
        this.parent = parent;
        this.mapFactory = mapFactory;

    }

    public ITrieReference recursiveInsert(Iterator key, IActionAtInsert value) {
        return null;
    }
}
