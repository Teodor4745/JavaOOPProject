package Parser;

import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Objects;

public class TreeNode {
    String text;
    ArrayList<TreeNode> children;
    TreeNode parent;

    public TreeNode(String text) {
        this.text = text;
        children = new ArrayList<>();
        parent = null;
    }

    public void addChild(String text){
        TreeNode newNode = new TreeNode(text);
        newNode.parent = this;
        this.children.add(newNode);
    }

    public TreeNode getChild(String text){
        for(int i = 0;i<children.size();i++){
            if(Objects.equals(children.get(i).text, text)){
                return children.get(i);
            }

        }
        return null;
    }


}
