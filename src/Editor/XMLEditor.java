package Editor;

import Parser.DataExtractor;
import Parser.DataValidator;
import XMLStructure.Element;
import Xpath.XPathOperator;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class XMLEditor {
    private String fileContent;
    private ArrayList<Element> elements;

    public void open(String fileContent) {
        this.fileContent = fileContent;
        DataExtractor dataExtractor = new DataExtractor();
        DataValidator dataValidator = new DataValidator();
        this.elements = dataValidator.validate(dataExtractor.extract(fileContent));
        toText();
    }

    public void toText() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Element element : elements) {
            int i = 0;
            Element counterElement = element;
            while (counterElement.getParent() != null) {
                i++;
                counterElement = counterElement.getParent();
            }
            element.setDepth(i);
        }
        for (Element element : elements) {
            stringBuilder.append(getIndent(element.getDepth()));
            stringBuilder.append("<").append(element.getTagName());
            //Extract attributes
            for (Map.Entry<String, String> set : element.getAttributes().entrySet()) {
                stringBuilder.append(" ").append(set.getKey()).append("=").append('"').append(set.getValue()).append('"');
            }
            stringBuilder.append(">");
            if (element.getText() != null) {
                stringBuilder.append(element.getText());
            } else {
                stringBuilder.append('\n');
            }
            if (element.getInnerElements().size() == 0) {
                if (element.getText() == null) {
                    stringBuilder.append(getIndent(element.getDepth()));
                }
                stringBuilder.append("</").append(element.getTagName()).append(">").append('\n');
            }
            if (element.getParent() != null && element.getInnerElements().size() == 0) {
                Element counterElement = element;
                while (counterElement.getParent() != null && counterElement.getParent().getInnerElements().size() > 0) {
                    if (counterElement.getParent().getInnerElements().get(counterElement.getParent().getInnerElements().size() - 1) == counterElement) {
                        if (counterElement.getParent().getText() == null) {
                            stringBuilder.append(getIndent(counterElement.getDepth() - 1));
                        }
                        stringBuilder.append("</").append(counterElement.getParent().getTagName()).append(">").append('\n');
                        counterElement = counterElement.getParent();
                    } else {
                        break;
                    }

                }


            }
        }
        this.fileContent = stringBuilder.toString();
    }

    public void print() {
        toText();
        System.out.println(fileContent);
    }

    public void select(String[] command) {
        if (command.length != 3) {
            System.out.println("Wrong number of arguments. command is: select <id> <key>");
            return;
        }
        String id = command[1];
        String key = command[2];

        Element element = findElementById(id);
        if (element == null) {
            System.out.println("No element with such id");
        }
        else{
            for (Map.Entry<String, String> set : element.getAttributes().entrySet()) {
                if(set.getKey().equals(key)){
                    System.out.println(set.getKey() + "=" + set.getValue());
                }
            }
        }
    }

    public void set(String[] command) {
        if (command.length != 4) {
            System.out.println("Wrong number of arguments. command is: set <id> <key> <value>");
            return;
        }
        String id = command[1];
        String key = command[2];
        String value = command[3];

        if(key.equals("id")){
            System.out.println("Cannot change id attribute");
            return;
        }

        Element element = findElementById(id);
        if (element == null) {
            System.out.println("No element with such id");
        }
        else{
            for (Map.Entry<String, String> set : element.getAttributes().entrySet()) {
                if (set.getKey().equals(key)) {
                    set.setValue(value);
                    System.out.println("Value of " + key + " set to " + value);
                    toText();
                    return;
                }
            }
            System.out.println("Element with this id has no such key");
        }
    }

    public void children(String[] command) {
        if (command.length != 2) {
            System.out.println("Wrong number of arguments. command is: children <id>");
            return;
        }
        String id = command[1];

        Element element = findElementById(id);
        if (element == null) {
            System.out.println("No element with such id");
        } else {
            if (element.getInnerElements().size() > 0) {
                for (Element childElement : element.getInnerElements()) {
                    if (childElement.getAttributes().size() > 0) {
                        System.out.println("element: " + childElement.getTagName() + " has attributes: ");
                        for (Map.Entry<String, String> childSet : childElement.getAttributes().entrySet()) {
                            System.out.println(childSet.getKey() + " = " + childSet.getValue());
                        }
                    } else {
                        System.out.println("Element " + childElement.getTagName() + " has no attributes");
                    }
                }
            } else {
                System.out.println("Element has no child elements");
            }
        }


    }

    public void child(String[] command) {
        if (command.length != 3) {
            System.out.println("Wrong number of arguments. command is: child <id> <n>");
            return;
        }
        String id = command[1];
        int n = Integer.parseInt(command[2]);
        if (n <= 0) {
            System.out.println("n must be a positive number");
            return;
        }

        Element element = findElementById(id);
        if (element == null) {
            System.out.println("No element with such id");
        } else {
            if (element.getInnerElements().size() == 0) {
                System.out.println("No child elements");
            } else if (element.getInnerElements().size() < n) {
                System.out.println("Number you have given: " + n + " is larger than number of child elements " + element.getInnerElements().size());
            } else {
                System.out.println("child element number " + n + " is : " + element.getInnerElements().get(n - 1).getTagName());
            }
        }
    }

    public void elementText(String[] command) {
        if (command.length != 2) {
            System.out.println("Wrong number of arguments. command is: text <id>");
            return;
        }
        String id = command[1];
        Element element = findElementById(id);
        if (element == null) {
            System.out.println("No element with such id");
        } else {
            if (element.getText() == null) {
                System.out.println("Element " + element.getTagName() + " has no text");
            } else {
                System.out.println("Element " + element.getTagName() + " has text: " + element.getText());
            }
        }
    }

    public void newChild(String[] command) {
        if (command.length != 2) {
            System.out.println("Wrong number of arguments. command is: newchild <id>");
            return;
        }
        String id = command[1];
        Element element = findElementById(id);
        if (element == null) {
            System.out.println("No element with such id");
        } else {
            Element newChild = new Element();
            newChild.setTagName("newchild");
            newChild.setParent(element);
            elements.add(newChild);
            element.getInnerElements().add(newChild);
            DataValidator dataValidator = new DataValidator();
            elements = dataValidator.validate(elements);
            toText();
        }
    }

    public void deleteAttribute(String[] command) {
        if (command.length != 3) {
            System.out.println("Wrong number of arguments. command is: delete <id> <key>");
            return;
        }
        String id = command[1];
        String key = command[2];
        if (key.equals("id")) {
            System.out.println("Cannot delete element id");
            return;
        }
        Element element = findElementById(id);
        if (element == null) {
            System.out.println("No element with such id");
            return;
        }
        for (Map.Entry<String, String> set : element.getAttributes().entrySet()) {
            if (Objects.equals(set.getKey(), key)) {
                element.getAttributes().remove(key);
                System.out.println("Successfully deleted key " + key + " from element " + element.getTagName());
                toText();
                return;
            }
        }
        System.out.println("No such key found in element " + element.getTagName() + " with id" + id);

    }

    public String getIndent(int depth) {
        return "   " + "   ".repeat(Math.max(0, depth));
    }

    public String getFileContent() {
        return fileContent;
    }

    public Element findElementById(String id) {
        for (Element element : elements) {
            for (Map.Entry<String, String> set : element.getAttributes().entrySet()) {
                if (Objects.equals(set.getKey(), "id") && Objects.equals(set.getValue(), id)) {
                    return element;
                }
            }
        }
        return null;
    }

    public void executeXpath(String[] command){
        if (command.length != 2) {
            System.out.println("Wrong number of arguments. command is: xpath <xpath>");
            return;
        }
        XPathOperator xPathOperator = new XPathOperator(elements,command[1]);
        xPathOperator.extractXpath();
        xPathOperator.runXpathInstructions();

    }


}



