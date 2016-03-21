package triePackage;

import actionsPackage.IActionAtInsert;
import mapPackage.IMapFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Matthias on 19.03.2016.
 */
public class TrieNode implements ITrieNode {
    private final ITrieNode parent;         // Parent of this node
    private final IMapFactory mapFactory;   // MapFactory given from parent
    private Comparable ingoingEdge;         // Edge which was the enty point to this node
    private final Map<Comparable, ITrieNode> outgoingEdgeMap;   // Outgoing Edges
    private Integer wordKey = null;

    public TrieNode(ITrieNode parent, IMapFactory mapFactory, Comparable ingoingEdge) {
        this.parent = parent;
        this.mapFactory = mapFactory;
        this.ingoingEdge = ingoingEdge;
        outgoingEdgeMap = mapFactory.create();
    }

    public ITrieReference recursiveInsert(Iterator key, IActionAtInsert value) {
        boolean found = false;
        ITrieNode correspondingNode = null;
        int correspondingValue;
        while (key.hasNext()) {
            Comparable thisKey = new PartialKeyType((char)key.next());
            if (outgoingEdgeMap.containsKey(thisKey)) {
                // Character found
                found = true;
                correspondingNode = outgoingEdgeMap.get(thisKey);
                correspondingNode.recursiveInsert(key, value);
            }
            else {
                // Character not found
                found = false;
                Comparable newKey = thisKey;
                correspondingNode = new TrieNode(this, mapFactory, newKey);
                outgoingEdgeMap.put(newKey, correspondingNode);
                correspondingNode.recursiveInsert(key, value);
            }
        }

        if (correspondingNode != null) {
            if (found) {
                correspondingValue = (int) value.actionAtKeyFound(correspondingNode.getWordKey());
            }
            else {
                wordKey = (int) value.actionAtKeyNotFound();
                correspondingValue = wordKey;
            }
            // Return TrieReference according to findings
            return new TrieReference(found, correspondingValue, correspondingNode);
        }
        return null;
    }

    @Override
    public int getWordKey() {
        return wordKey;
    }
}
