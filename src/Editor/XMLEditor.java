package Editor;

import Parser.DataExtractor;
import Parser.DataValidator;
import XMLStructure.Element;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class XMLEditor {
    private String fileContent;
    private ArrayList<Element> elements;

    public void open(String fileContent){
        this.fileContent = fileContent;
        DataExtractor dataExtractor = new DataExtractor();
        DataValidator dataValidator = new DataValidator();
        this.elements = dataValidator.validate(dataExtractor.extract(fileContent));
        toText();
    }

    public void toText(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Element element : elements){
            int i = 0;
            Element counterElement = element;
            while (counterElement.getParent() != null){
                i++;
                counterElement = counterElement.getParent();
            }
            element.setDepth(i);
        }
        for(Element element: elements){
            stringBuilder.append(getIndent(element.getDepth()));
            stringBuilder.append("<").append(element.getTagName());
            //Extract attributes
            for(Map.Entry<String,String> set : element.getAttributes().entrySet()){
                stringBuilder.append(" ").append(set.getKey()).append("=").append('"').append(set.getValue()).append('"');
            }
            stringBuilder.append(">" + '\n');
            if(element.getText() != null){
                stringBuilder.append(getIndent(element.getDepth()));
                stringBuilder.append("   ").append(element.getText()).append('\n');
            }
            if(element.getInnerElements().size() == 0){
                stringBuilder.append(getIndent(element.getDepth()));
                stringBuilder.append("</").append(element.getTagName()).append(">").append('\n');
            }
            if(element.getParent() != null && element.getInnerElements().size() == 0){
                Element counterElement = element;
                while(counterElement.getParent() != null && counterElement.getParent().getInnerElements().size() > 0){
                    if(counterElement.getParent().getInnerElements().get(counterElement.getParent().getInnerElements().size()-1) == counterElement)
                    {
                        stringBuilder.append(getIndent(counterElement.getDepth()-1));
                        stringBuilder.append("</").append(counterElement.getParent().getTagName()).append(">").append('\n');
                        counterElement = counterElement.getParent();
                    }
                    else{
                        break;
                    }

                }


            }
        }
        this.fileContent = stringBuilder.toString();
    }

    public void print(){
        toText();
        System.out.println(fileContent);
    }

    public void select(String[] command){
        if(command.length != 3) {
            System.out.println("Wrong number of arguments. command is: select <id> <key>");
            return;
        }
        String id = command[1];
        String key = command[2];
        String value = "";
        boolean containsId = false;
        boolean containsKey = false;
        for(Element element : elements){
            for(Map.Entry<String,String> set : element.getAttributes().entrySet()){
                if(Objects.equals(set.getKey(), "id") && Objects.equals(set.getValue(), id)){
                    containsId = true;
                }
                if(Objects.equals(set.getKey(), key)){
                    if(containsId){
                        value = set.getValue();
                    }
                }
            }
            containsId = false;
        }
        if(Objects.equals(value, "")){
            System.out.println("There is no attribute with this id and key");
        }
        else{
            System.out.println(command[2] + " = " + value);
        }
    }

    public void set(String[] command){
        if(command.length != 4) {
            System.out.println("Wrong number of arguments. command is: set <id> <key> <value>");
            return;
        }
        String id = command[1];
        String key = command[2];
        String value = command[3];
        boolean containsId = false;
        boolean isSet = false;

        for(Element element : elements){
            for(Map.Entry<String,String> set : element.getAttributes().entrySet()){
                if(Objects.equals(set.getKey(), "id") && Objects.equals(set.getValue(), id)){
                    containsId = true;
                }
                if(Objects.equals(set.getKey(), key)){
                    if(containsId){
                        set.setValue(value);
                        System.out.println("Value of " + key + " set to " + value);
                         isSet = true;
                         toText();
                    }
                }
            }
            containsId = false;
        }
        if(!isSet){
            System.out.println("There is no attribute with this id and key");
        }
    }

    public void children(String[] command){
        if(command.length != 2) {
            System.out.println("Wrong number of arguments. command is: children <id>");
            return;
        }
        String id = command[1];

        for(Element element : elements){
            if(element.getInnerElements().size() > 0){
                for(Map.Entry<String,String> set : element.getAttributes().entrySet()){
                    if(Objects.equals(set.getKey(), "id") && Objects.equals(set.getValue(), id)){
                        for (Element childElement : element.getInnerElements()){
                            System.out.println("element: " + childElement.getTagName() + "has elements:");
                            for(Map.Entry<String,String> childSet : element.getAttributes().entrySet()){
                                System.out.println(childSet.getKey() + " = " + childSet.getValue());
                            }
                        }
                    }
                }
            }
            else {
                System.out.println("Element has no child elements");
            }
        }

    }

    public String getIndent(int depth){
        StringBuilder indent = new StringBuilder("   ");
        for(int t = 0;t<depth;t++){
            indent.append("   ");
        }
        return indent.toString();
    }

    public String getFileContent() {
        return fileContent;
    }


}



