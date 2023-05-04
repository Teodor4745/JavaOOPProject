package Xpath;

import XMLStructure.Element;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class XPathOperator {
    private final ArrayList<Element> elements;
    private final String command;
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
            String tag;
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
                            instructions.append("&a");
                            instructions.append(command, i + 1, j);
                            i = j;
                            if(j == command.length()-1){
                                break;
                            }
                        }
                        if (command.charAt(i) == '(') {
                            i++;
                            if (command.charAt(i) == '@') {
                                j = i+1;
                                while (checkIfLetter(command.charAt(j))) {
                                    j++;
                                }
                                if (command.charAt(j) == ')') {
                                    instructions.append("&b");
                                    instructions.append(command, i + 1, j);
                                    i = j;
                                } else if (command.charAt(j) == '=') {
                                    String attribute = command.substring(i + 1, j);
                                    i = j;
                                    while (command.charAt(j) != ')') {
                                        j++;
                                    }
                                    instructions.append("&c");
                                    instructions.append(attribute);
                                    instructions.append("=");
                                    instructions.append(command, i + 1, j);
                                    i = j;

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
                                instructions.append("&d");
                                instructions.append(innerElement);
                                instructions.append("=");
                                instructions.append(command, i, j);
                                i = j;


                            }
                            if(j == command.length()-1){
                                break;
                            }
                        }
                        i++;
                        if(i == command.length()-1){
                            break;
                        }
                    }
                }
            xpathMap.put(tag,instructions.toString());
            if(i == command.length()-1){
                break;
            }


        }

    }

    public void runXpathInstructions(){
        ArrayList<Element> newElements = new ArrayList<>();
        boolean firstOperation = true;
        boolean displayAttributes = false;
        String attributeToDisplay = "";
        boolean attributeCheck;

        for (Map.Entry<String, String> set : xpathMap.entrySet()) {
            String tag = set.getKey();
            String instructions = set.getValue();

            if(tag.equals("")){
                System.out.println("Invalid xpath command");
                return;
            }
            if(firstOperation){
                for(Element element : elements){
                    if(element.getTagName().equals(tag) && element.getDepth() <= 2){
                        newElements.add(element);
                    }
                }
                firstOperation = false;
            }
            else{
                ArrayList<Element> childElements = new ArrayList<>();
                for(Element element : newElements){
                    for(Element child : element.getInnerElements()){
                        if(child.getTagName().equals(tag)){
                            childElements.add(child);
                        }
                    }
                }
                newElements = childElements;
            }



            for(int i = 0;i<instructions.length();i++){
                if(instructions.charAt(i) == '&'){
                    i++;
                    if(instructions.charAt(i) == 'a'){
                        i++;
                        int t = i;
                        while(instructions.charAt(t) != '&' && t < instructions.length()-1){
                            t++;
                        }
                        if(t == instructions.length() - 1){
                            t++;
                        }
                        int index = Integer.parseInt(instructions.substring(i,t));
                        ArrayList<Element> newNewElements = new ArrayList<>();
                        for(int z = 0; z < newElements.size(); z++){
                            if(z == index){
                                newNewElements.add(newElements.get(z));
                            }
                        }
                        newElements = newNewElements;
                        i = t;
                        if(i == instructions.length()){
                            break;
                        }
                    }
                    if(instructions.charAt(i) == 'b'){
                        displayAttributes = true;
                        i++;
                        int t = i;
                        while(instructions.charAt(t) != '&' && t < instructions.length()-1){
                            t++;
                        }
                        if(t == instructions.length() - 1){
                            t++;
                        }
                        attributeToDisplay = instructions.substring(i,t);
                        i = t;
                        if(i == instructions.length()){
                            break;
                        }
                    }
                    if(instructions.charAt(i) == 'c' || instructions.charAt(i) == 'd'){

                        attributeCheck = instructions.charAt(i) == 'c';
                        i++;
                        int t = i;
                        while(instructions.charAt(t) != '='){
                            t++;
                        }
                        String attributeToCheck = instructions.substring(i,t);
                        t++;
                        i = t;
                        while(instructions.charAt(t) != '&' && t < instructions.length()-1){
                            t++;
                        }
                        if(t == instructions.length() - 1){
                            t++;
                        }
                        String valueToCheck = instructions.substring(i,t);
                        ArrayList<Element> newNewElements = new ArrayList<>(newElements);
                        if(attributeCheck){
                            for(Element element : newElements){
                                if(!element.getAttributes().containsKey(attributeToCheck)){
                                    newNewElements.remove(element);
                                }
                                else {
                                    if(!element.getAttributes().get(attributeToCheck).equals(valueToCheck)){
                                        newNewElements.remove(element);
                                    }
                                }
                            }
                        }
                        else{
                            boolean noInnerElement = true;
                            for(Element element : newElements){
                                for(Element innerElement : element.getInnerElements()){
                                    if(innerElement.getTagName().equals(attributeToCheck)){
                                        noInnerElement = false;
                                        if(innerElement.getText().equals(valueToCheck)){
                                            break;
                                        }
                                        else{
                                            newNewElements.remove(element);
                                        }
                                    }
                                }
                                if(noInnerElement){
                                    newNewElements.remove(element);
                                }
                            }
                        }
                        newElements = newNewElements;
                        i = t;
                        if(t == instructions.length()){
                            break;
                        }
                    }
                }
            }
        }

        boolean printedInformation = false;
        if(displayAttributes){
            for(Element element : newElements){
                if(element.getAttributes().containsKey(attributeToDisplay)){
                    printedInformation = true;
                    System.out.println("Element: " + element.getTagName() +
                            " with text: " + element.getText() + " has " + attributeToDisplay + "=" + element.getAttributes().get(attributeToDisplay));
                }

            }
        }
        else{
            for (Element element : newElements){
                printedInformation = true;
                System.out.print("Element: " + element.getTagName() + "(id = " + element.getAttributes().get("id") + ") ");
                if(element.getText() == null){
                    System.out.println("(no text value)");
                }
                else{
                    System.out.println("=" + element.getText());
                }
            }
        }
        if(!printedInformation){
            System.out.println("No results found");
        }
    }

    public boolean checkIfLetter(char character){
        return (character >= 65 && character <= 90) || (character >= 97 && character <= 122);
    }

    public void printInvalidCommand(){
        System.out.println("Invalid Xpath Command");
    }


}
