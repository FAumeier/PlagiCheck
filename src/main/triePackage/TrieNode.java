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
    Map<PartialKeyType, ITrieNode> outgoingEdgeMap;
    
    @Override
    public ITrieReference recursiveInsert(Iterator key, IActionAtInsert value) {
        return null;
    }
}
