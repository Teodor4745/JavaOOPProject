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
    public TreeNode validate(String fileContent){
        setFileContent(fileContent);

        String tag = "";
        String text = "";
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
                if(tag.charAt(0) == '/' && currentParent.parent != null){
                    currentParent = currentParent.parent;
                }
                else if(tag.charAt(0) == '/' && currentParent.parent == null){
                    break;
                }
                else{
                    currentParent.addChild("&tag " + tag);
                    currentParent = currentParent.getChild("&tag " + tag);
                }



            }

            if(fileContent.charAt(i) == '>'){
                if(i+1 > fileContent.length()){
                    break;
                }
                i++;
                while(fileContent.charAt(i) == '\n' || fileContent.charAt(i) == ' '){
                    i++;
                    if(i >= fileContent.length())
                        break;
                }
                if(i == fileContent.length()){
                    break;
                }
                if(fileContent.charAt(i) != '<' ){
                    int j = i;
                    while(fileContent.charAt(j) != '<'){
                        j++;

                    }
                    text = fileContent.substring(i,j);
                    currentParent.addChild("&val " + text);
                }

            }
            else{
                i++;
            }



        }




        return xmlTree;

    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}
