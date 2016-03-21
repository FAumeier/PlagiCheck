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

    private Integer keyNodeValue = null;
    private boolean isKeyNode = false;

    @Override
    public boolean isKeyNode() {
        return isKeyNode;
    }
    @Override
    public void setKeyNode() {
        isKeyNode = true;
    }

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
                correspondingValue = (int) value.actionAtKeyFound(correspondingNode.getKeyNodeValue());
            }
            else {
                keyNodeValue = (int) value.actionAtKeyNotFound();
                correspondingValue = keyNodeValue;
            }
            // Return TrieReference according to findings
            return new TrieReference(found, correspondingValue, correspondingNode);
        }
        return null;
    }

    public int getKeyNodeValue() {
        return keyNodeValue;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int offset) {
        String msg = "";
        for (Map.Entry<Comparable, ITrieNode> entry : outgoingEdgeMap.entrySet()) {
            // Add offset as points
            for (int i = 0; i < offset; i++) {
                msg += ".";
            }
            //if (isKeyNode) {
                msg += entry.getKey() + " |-> " + keyNodeValue + "\n";
            //}
            //else {
            //    msg += entry.getKey() + "\n";
            //}
            TrieNode next = (TrieNode) entry.getValue();
            msg += next.toString(offset + 1);
        }
        return msg;
    }
}
