package Parser;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class XMLEditor {
    public TreeNode extract(String fileContent){

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

    public TreeNode validateID(TreeNode treeNode){
        Set<String> usedIds = new HashSet<>();
        Stack<TreeNode> treeIterationStack = new Stack();
        treeIterationStack.push(treeNode);
        while (!treeIterationStack.isEmpty()) {




                for(TreeNode child : treeNode.children ) {
                    if (child.text.startsWith("&tag")) {
                        if (child.text.contains("id=")) {
                            for (int i = child.text.indexOf("id="); i < child.text.length(); i++) {

                                if (child.text.charAt(i) == '=') {
                                    int startIndexId = i;
                                    int endIndexId = i;
                                    for (int t = 0; t < child.text.length(); t++) {
                                        if (child.text.charAt(i) != '>' && child.text.charAt(i) != '"')
                                            t++;
                                        endIndexId = t;
                                    }
                                    String idValue = child.text.substring(startIndexId, endIndexId);
                                    if (usedIds.contains(idValue)) {
                                        String oldIdValue = idValue;
                                        int z = 0;
                                        while (usedIds.contains(idValue)) {
                                            z++;
                                            idValue = idValue + "_" + z;
                                        }
                                        child.text.replace(oldIdValue, idValue);
                                    } else {
                                        usedIds.add(idValue);
                                    }
                                }

                            }
                        } else {
                            for (int i = 0; i < child.text.length(); i++) {
                                if (child.text.charAt(i) == ' ' || i == child.text.length() - 1) {
                                    int newId = ((int) ((Math.random() * (1000 - 0))));
                                    String newIdString = Integer.toString(newId);
                                    String replaceString = String.valueOf(child.text.charAt(i));
                                    if (usedIds.contains("id=" + newIdString))
                                        child.text.replace(replaceString, replaceString + "id=" + newIdString);
                                    else {
                                        i--;
                                    }

                                }
                            }
                        }
                    }
                }



        }



        return treeNode;
    }


}
