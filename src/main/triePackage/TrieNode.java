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
    private Comparable ingoingEdge;         // Edge which was the entry point to this node
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
    @Override
    public int getKeyNodeValue() {
        return keyNodeValue;
    }
    @Override
    public void setKeyNodeValue(int value) {
        keyNodeValue = value;
    }

    public TrieNode(ITrieNode parent, IMapFactory mapFactory, Comparable ingoingEdge) {
        this.parent = parent;
        this.mapFactory = mapFactory;
        this.ingoingEdge = ingoingEdge;
        outgoingEdgeMap = mapFactory.create();
    }

    /**
     * Root node constructor
     * @param mapFactory
     */
    public TrieNode(IMapFactory mapFactory) {
        this.mapFactory = mapFactory;
        outgoingEdgeMap = mapFactory.create();
        parent = null;
    }

    public ITrieReference recursiveInsert(Iterator key, IActionAtInsert value) {
        ITrieNode correspondingNode = null;
        int correspondingValue;

        ITrieReference keyNodeRef = null;

        while (key.hasNext()) {
            Comparable thisKey = new PartialKeyType((char)key.next());
            if (outgoingEdgeMap.containsKey(thisKey)) {
                // Character found
                correspondingNode = outgoingEdgeMap.get(thisKey);
                if (!key.hasNext()) {
                    correspondingValue = (int) value.actionAtKeyFound(correspondingNode.getKeyNodeValue());
                    correspondingNode.setKeyNodeValue(correspondingValue);
                    setKeyNode();
                    keyNodeRef = new TrieReference(true, correspondingValue, correspondingNode);
                }
                else if (keyNodeRef == null) {
                    keyNodeRef = correspondingNode.recursiveInsert(key, value);
                }
            }
            else {
                // Character not found
                Comparable newKey = thisKey;
                correspondingNode = new TrieNode(this, mapFactory, newKey);
                outgoingEdgeMap.put(newKey, correspondingNode);
                if (!key.hasNext()) {
                    keyNodeValue = (int) value.actionAtKeyNotFound();
                    correspondingValue = keyNodeValue;
                    correspondingNode.setKeyNodeValue(correspondingValue);
                    setKeyNode();
                    keyNodeRef = new TrieReference(false, correspondingValue, correspondingNode);
                }
                else if (keyNodeRef == null) {
                    keyNodeRef = correspondingNode.recursiveInsert(key, value);
                }
            }
        }
        if (keyNodeRef != null) {
            return keyNodeRef;
        }
        return null;
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
            if (isKeyNode) {
                msg += entry.getKey() + " |-> " + keyNodeValue + "\n";
            }
            else {
                msg += entry.getKey() + "\n";
            }
            TrieNode next = (TrieNode) entry.getValue();
            msg += next.toString(offset + 1);
        }
        return msg;
    }
}
