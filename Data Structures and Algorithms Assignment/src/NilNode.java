//NilNode Class
public class NilNode extends Node {
    //Special case of node, it has no value, is black, and sits at the bottom of the red-black tree
    public NilNode() {
        super(null);
        this.setColor(RedBlackTree.BLACK);
    }
}
