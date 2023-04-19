package Parser;

import com.sun.source.tree.Tree;

import java.util.ArrayList;

public class TreeNode {
    String text;
    ArrayList<TreeNode> children;
    TreeNode parent;

    public TreeNode(String text) {
        this.text = text;
        children = null;
        parent = null;
    }

    public void addChild(String text){
        TreeNode newNode = new TreeNode(text);
        this.children.add(newNode);
        newNode.parent = this;
    }

    public TreeNode getChild(String text){
        for(int i = 0;i<children.size();i++){
            if(children.get(i).text == text){
                return children.get(i);
            }

        }
        return null;
    }


}
