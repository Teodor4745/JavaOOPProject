package Parser;

import XMLStructure.Element;

import java.util.ArrayList;
import java.util.HashMap;

public class DataExtractor {

    public ArrayList<Element> extract(String fileContent){
        ArrayList<Element> allElements = new ArrayList<>();

        int i = 0;
        boolean firstElement = true;
        Element currentElement = new Element();
        AttributeExtractor attributeExtractor = new AttributeExtractor();
        while (i < fileContent.length()) {

            if (fileContent.charAt(i) == '<') {
                int j = i;
                while (j < fileContent.length() && fileContent.charAt(j) != '>') {
                    j++;
                }
                String tag = fileContent.substring(i + 1, j);
                if(tag.charAt(0) == '/' && currentElement.getParent() != null){
                    currentElement = currentElement.getParent();
                }
                else if(tag.charAt(0) == '/' && currentElement.getParent() == null){
                    break;
                }
                else{

                    HashMap<String,String> attributes = attributeExtractor.extractAttributes(tag);
                    Element childElement = new Element();

                    childElement.setTagName(attributes.get("&tagName"));
                    attributes.remove("&tagName");

                    childElement.setAttributes(attributes);
                    childElement.setParent(currentElement);
                    if(firstElement){
                       firstElement = false;
                       currentElement = childElement;
                    }
                    else{
                        currentElement.getInnerElements().add(childElement);
                        currentElement = childElement;
                    }
                    allElements.add(currentElement);


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
                    String text = fileContent.substring(i,j);
                    currentElement.setText(text);
                }

            }
            else{
                i++;
            }



        }



        return allElements;
    }
}
