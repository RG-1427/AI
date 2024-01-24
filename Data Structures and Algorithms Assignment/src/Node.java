//Node class
public class Node {
    //Variables storing node information
    protected Employee emp;
    protected Node left;
    protected Node right;
    protected Node parent;
    protected boolean color;

    //Adding an employee to a node - defining a node
    public Node(Employee emp) {
        this.emp = emp;
    }
    //Setting the node colour
    public void setColor(boolean color) {
        this.color = color;
    }
}
