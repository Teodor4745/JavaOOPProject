package Xpath;

import XMLStructure.Element;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class XPathOperator {
    private ArrayList<Element> elements;
    private String command;
    private LinkedHashMap<String,String> xpathMap;

    public XPathOperator(ArrayList<Element> elements, String command) {
        this.elements = elements;
        this.command = command;
    }

    public void extractXpath(){

        xpathMap = new LinkedHashMap<>();

        if(!checkIfLetter(command.charAt(0))){
            printInvalidCommand();
            return;
        }

        for(int i = 0;i<command.length();i++){
            String tag = "";
            StringBuilder instructions = new StringBuilder();

                int j = i;
                while(checkIfLetter(command.charAt(j))){
                    j++;
                    if(j == command.length()-1){
                        j++;
                        break;
                    }
                }
                tag = command.substring(i,j);
                i = j;
                if(i < command.length()){
                    while(command.charAt(i) != '/') {
                        if (command.charAt(i) == '[') {
                            j = i;
                            while (command.charAt(j) != ']')
                            {
                                j++;
                            }
                            instructions.append("&num");
                            instructions.append(command.substring(i + 1, j));
                        }
                        if (command.charAt(i) == '(') {
                            i++;
                            if (command.charAt(i) == '@') {
                                j = i+1;
                                while (checkIfLetter(command.charAt(j))) {
                                    j++;
                                }
                                if (command.charAt(j) == ')') {
                                    instructions.append("&atr*");
                                    instructions.append(command.substring(i + 1, j));
                                } else if (command.charAt(j) == '=') {
                                    String attribute = command.substring(i + 1, j);
                                    i = j;
                                    while (command.charAt(j) != ')') {
                                        j++;
                                    }
                                    instructions.append("&atre");
                                    instructions.append(attribute);
                                    instructions.append("=");
                                    instructions.append(command.substring(i + 1, j));

                                }

                            }
                            if (checkIfLetter(command.charAt(i))) {
                                j = i;
                                while (command.charAt(j) != '=') {
                                    j++;
                                }
                                String innerElement = command.substring(i, j);
                                j++;
                                i = j;
                                while (command.charAt(j) != ')') {
                                    j++;
                                }
                                instructions.append("&inval");
                                instructions.append(innerElement);
                                instructions.append("=");
                                instructions.append(command.substring(i, j));


                            }
                            if(j == command.length()-1){
                                j++;
                                break;
                            }
                        }
                        i++;
                        if(i == command.length()-1){
                            break;
                        }
                    }
                }
            if(i == command.length()-1){
                break;
            }
            xpathMap.put(tag,instructions.toString());

        }

    }

    public void runXpathInstructions(){
        ArrayList<Element> newElements = new ArrayList<>();
        boolean firstOperation = true;
        for (Map.Entry<String, String> set : xpathMap.entrySet()) {
            String tag = set.getKey();
            String instructions = set.getValue();

            if(tag.equals("")){
                System.out.println("Invalid xpath command");
                return;
            }
            if(firstOperation){
                for(Element element : elements){
                    if(element.getTagName().equals(tag)){
                        newElements.add(element);
                    }
                }
                firstOperation = false;
            }
            else{
                ArrayList<Element> childElements = new ArrayList<>();
                for(Element element : newElements){
                    for(Element childElement : element.getInnerElements()){
                        childElements.add(childElement);
                    }
                }
                newElements = childElements;
            }



            for(int i = 0;i<instructions.length();i++){

            }
        }
    }

    public boolean checkIfLetter(char character){
        if((character >= 65 && character <= 90) || (character >= 97 && character <= 122)){
            return true;
        }
        return false;
    }

    public boolean isParent(Element parent, Element child){
        Element compareElement = child;
        while(compareElement.getParent() != null){
            if(compareElement.getParent() == parent){
                return true;
            }
            compareElement = compareElement.getParent();
        }
        return false;
    }

    public void printInvalidCommand(){
        System.out.println("Invalid Xpath Command");
    }


}
