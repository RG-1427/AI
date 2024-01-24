//Red-Black Tree Class
import java.util.List;


public class RedBlackTree {
    //Variables necessary for red black trees
    private int count;
    private Node root;
    static final boolean RED = true;
    static final boolean BLACK = false;

    //Creating the red black tree
    public RedBlackTree(int count){
        //When the tree is created, add everything in the text file onto the tree
        this.count = count;
        if(count != 0) {
            List<Employee> employeeList;
            employeeList = Payroll.txtFile.getEmp();
            for(int i = 0; i < employeeList.size(); i++)
            {
                insertNode(employeeList.get(i));
            }
        }

    }

    //Searching the tree
    public Node searchNode(int regNum) {
        //Going through the tree, from the bottom
        Node node = root;
        while (node != null) {
            //If it is the root node, return it, otherwise based on teh data move to the left or right of the tree, as the tree is sorted
            //like a binary tree, where the value on the left is smaller than the value on the right
            if (regNum == node.emp.getRegNum()) {
                return node;
            } else if (regNum < node.emp.getRegNum()) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    //Adding node
    public void insertNode(Employee employee){
        //Node is equal root, and has no parent
        Node node = root;
        Node parent = null;

        //While that node is not null, setting the parent to the node, and checking based on teh registration number where it should go on the tree
        //If it is lower than the current node, it will go left, otherwise go right
        //This algorithm is looking for a parent that does not have a child where the node will go
        while (node != null) {
            parent = node;
            if (employee.getRegNum() < node.emp.getRegNum()) {
                node = node.left;
            }
            else if (employee.getRegNum() > node.emp.getRegNum()) {
                node = node.right;
            }
        }

        //Create a new node storing the employee
        Node newNode = new Node(employee);
        newNode.setColor(RED);
        //If it has no parent, it is the root
        if (parent == null) {
            root = newNode;
        }
        //If the registration number is smaller than the parent number, put it on the left, otherwise put it to the right
        else if (employee.getRegNum() < parent.emp.getRegNum()) {
            parent.left = newNode;
        }
        else {
            parent.right = newNode;
        }
        //Set the new nodes parent to the node found
        newNode.parent = parent;

        //Fix the properties of the tree after the node addition
        fixPropertiesAfterInsert(newNode);
        count += 1;
    }

    private void fixPropertiesAfterInsert(Node node) {
        //Find node's parent
        Node parent = node.parent;

        //If it has no parent, it is the root
        if (parent == null) {
            node.color = BLACK;
            return;
        }

        //If the parent is black it follows the rules
        if (parent.color == BLACK) {
            return;
        }

        //Getting nodes grandparent
        Node grandparent = parent.parent;

        //If the grandparent is null, that means the parent is the root, therefore make sure that it's color is black
        if (grandparent == null) {
            parent.color = BLACK;
            return;
        }

        //Get the parents uncle
        Node uncle = getUncle(parent);

        //If the uncle is red, recolor the parent, grandparent and uncle
        if (uncle != null && uncle.color == RED) {
            parent.color = BLACK;
            grandparent.color = RED;
            uncle.color = BLACK;
            //Call this function recursively to make sure that all the node follow the rules
            fixPropertiesAfterInsert(grandparent);
        }

        //If the parent is the left child of the grandparent
        else if (parent == grandparent.left) {
            //If the node is the right child of the parent, rotate left to fix the tree
            if (node == parent.right) {
                rotateLeft(parent);
                parent = node;
            }
            //Rotate the tree right from the grandparent and recolour the parent and grandparent to match the rules
            rotateRight(grandparent);
            parent.color = BLACK;
            grandparent.color = RED;
        }
        //The parent is the right child of the parent
        else {
            //If the node is the parents left, rotate the tree right
            if (node == parent.left) {
                rotateRight(parent);
                parent = node;
            }
            //Rotate the tree left from the grandparent and recolour the parent and grandparent to match the rules
            rotateLeft(grandparent);
            parent.color = BLACK;
            grandparent.color = RED;
        }
    }

    //Get uncle
    private Node getUncle(Node parent) {
        //Grandparent is the parent's parent
        Node grandparent = parent.parent;
        //If the parent is on the left side of the grandparent, return the right side, else return the left side
        if (grandparent.left == parent) {
            return grandparent.right;
        } else if (grandparent.right == parent) {
            return grandparent.left;
        } else {
            throw new IllegalStateException("The parent is not a child of its grandparent");
        }
    }

    //Rotating the tree right
    private void rotateRight(Node node) {
        //Finding the parent and left node
        Node parent = node.parent;
        Node leftChild = node.left;
        //The node to the left becomes the node to the left node's right
        node.left = leftChild.right;
        //If the node to the right of the node to the left is not null, that nodes parent becomes the node input in
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        //The right of the current left node becomes the node
        leftChild.right = node;
        //The nodes parent becomes the nodes left
        node.parent = leftChild;

        //The child is replaced
        replaceChild(parent, node, leftChild);
    }

    //Rotate left
    private void rotateLeft(Node node) {
        //Finding the parent and right node
        Node parent = node.parent;
        Node rightChild = node.right;

        //Works the same as the rotate left node, but with the right instead (opposite rotation)
        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.left = node;
        node.parent = rightChild;
        replaceChild(parent, node, rightChild);
    }

    //Replacing a child node
    private void replaceChild(Node parent, Node oldChild, Node newChild) {
        //If the node has no parent, teh root is that node
        if (parent == null) {
            root = newChild;
        //Replace the child with the new child
        } else if (parent.left == oldChild) {
            parent.left = newChild;
        } else if (parent.right == oldChild) {
            parent.right = newChild;
        } else {
            throw new IllegalStateException("The node is not a child of its parent");
        }
        //Set the new child's parent to the parent node
        if (newChild != null) {
            newChild.parent = parent;
        }
    }

    //Node deletion
    public int deleteNode(int key) {
        Node node = root;

        //Find the node to be deleted in the tree
        while (node != null && node.emp.getRegNum() != key) {
            if (key < node.emp.getRegNum()) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        //If node is not found, state that it does not exist
        if (node == null) {
            return -1;
        }
        //Create a node that may be moved up and store the colour of the deleted node
        Node movedUpNode;
        boolean deletedNodeColor;

        //If the node has one or no children, use the special deletion case function
        if (node.left == null || node.right == null) {
            movedUpNode = deleteNodeSpecialCase(node);
            deletedNodeColor = node.color;
        }
        else {
            //The minimum right of the tree will be the in order successor, copy the successor's data to the current node, delete the bottom right most node, and store the color
            Node inOrderSuccessor = findMinimum(node.right);
            node.emp = inOrderSuccessor.emp;
            movedUpNode = deleteNodeSpecialCase(inOrderSuccessor);
            deletedNodeColor = inOrderSuccessor.color;
        }

        //If the deleted node colour is black, fix the properties after a deletion
        if (deletedNodeColor == BLACK) {
            fixPropertiesAfterDelete(movedUpNode);
            //If the moved up node is a nil node, replace the child with a nil node
            if (movedUpNode.getClass() == NilNode.class) {
                replaceChild(movedUpNode.parent, movedUpNode, null);
            }
        }

        //Decrease the count and return that the deletion has been completed
        count -= 1;
        return 1;
    }

    //Deletion of a node with one or zero children
    private Node deleteNodeSpecialCase(Node node) {
        //If the nodes left is not null, replace the parents left with it
        if (node.left != null) {
            replaceChild(node.parent, node, node.left);
            return node.left;
        }
        //If the nodes right is not null, replace the parents left with it
        else if (node.right != null) {
            replaceChild(node.parent, node, node.right);
            return node.right;
        }

        //If the node's colour is black, replace it temporarily with a nil node and replace the child
        else {
            Node newChild = node.color == BLACK ? new NilNode() : null;
            replaceChild(node.parent, node, newChild);
            return newChild;
        }
    }

    //Find the minimum valued node
    private Node findMinimum(Node node) {
        //If the node is not the lowest go left, return the most left node
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    //Fixing properties after deletion
    private void fixPropertiesAfterDelete(Node node) {

        //If the node given is the root, it is the bottom of the recursion
        if (node == root) {
            node.color = BLACK;
            return;
        }

        //Get nodes sibling
        Node sibling = getSibling(node);
        //If the sibling is red, handle the red sibling
        if (sibling.color == RED) {
            handleRedSibling(node, sibling);
            sibling = getSibling(node);
        }
        //Handle node's black sibling with two black children
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            //Colour the sibling red if it's children are colored black
            sibling.color = RED;
            //If the parent is red, change it to black, otherwise fix the properties (call this function again on the parent)
            if (node.parent.color == RED) {
                node.parent.color = BLACK;
            }
            else {
                fixPropertiesAfterDelete(node.parent);
            }
        }
        //Otherwise, handle a black sibling
        else {
            handleBlackSibling(node, sibling);
        }
    }

    //Dealing with a red sibling
    private void handleRedSibling(Node node, Node sibling) {
        //Recolor the nodes to follow the rules
        sibling.color = BLACK;
        node.parent.color = RED;

        //Rotate the nodes int order to fix the tree
        if (node == node.parent.left) {
            rotateLeft(node.parent);
        } else {
            rotateRight(node.parent);
        }
    }

    //Handling a black sibling
    private void handleBlackSibling(Node node, Node sibling) {
        boolean nodeIsLeftChild = node == node.parent.left;

        //Recolor sibling and its child, and rotate around sibling based on which side the sibling is at
        if (nodeIsLeftChild && isBlack(sibling.right)) {
            sibling.left.color = BLACK;
            sibling.color = RED;
            rotateRight(sibling);
            sibling = node.parent.right;
        } else if (!nodeIsLeftChild && isBlack(sibling.left)) {
            sibling.right.color = BLACK;
            sibling.color = RED;
            rotateLeft(sibling);
            sibling = node.parent.left;
        }


        //Recolor sibling + parent + sibling's child, and rotate around parent, based on which side the node is
        sibling.color = node.parent.color;
        node.parent.color = BLACK;
        if (nodeIsLeftChild) {
            sibling.right.color = BLACK;
            rotateLeft(node.parent);
        } else {
            sibling.left.color = BLACK;
            rotateRight(node.parent);
        }
    }

    //Get sibling
    private Node getSibling(Node node) {
        //Return a node sibling based on if they are on the left or right side of the parent
        Node parent = node.parent;
        if (node == parent.left) {
            return parent.right;
        } else if (node == parent.right) {
            return parent.left;
        } else {
            throw new IllegalStateException("The parent is not a child of its grandparent");
        }
    }

    //Return true if the node is null (as a nil node is black) or is coloured black
    private boolean isBlack(Node node) {
        return node == null || node.color == BLACK;
    }

}
