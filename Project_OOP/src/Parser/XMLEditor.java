package Parser;

import org.w3c.dom.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class XMLEditor {
    String fileContent;

    private String getNextTag(int i){

        String tag = "";
        i = 0;
        while (i < fileContent.length()) {
            if (fileContent.charAt(i) == '<') {
                int j = i;
                while (j < fileContent.length() && fileContent.charAt(j) != '>') {
                    j++;
                }
                tag = fileContent.substring(i + 1, j);

            }
            else{
                i++;
            }
        }
        return tag;
    }
    public void validate(String fileContent){
        setFileContent(fileContent);

        String tag = "";
        int i = 0;
        TreeNode xmlTree = new TreeNode("root");
        TreeNode currentParent = xmlTree;
        while (i < fileContent.length()) {

            if (fileContent.charAt(i) == '<') {
                int j = i;
                while (j < fileContent.length() && fileContent.charAt(j) != '>') {
                    j++;
                }
                tag = fileContent.substring(i + 1, j);
                if(tag.charAt(1) == '/' && currentParent.parent != null){
                    currentParent = currentParent.parent;
                }
                else if(tag.charAt(1) == '/' && currentParent.parent == null){
                    break;
                }
                else{
                    currentParent.addChild(tag);
                    currentParent = currentParent.getChild(tag);
                }



            }
            else{
                i++;
            }
        }



    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}
