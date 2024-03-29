package Editor;

import Parser.DataExtractor;
import Parser.DataValidator;
import XMLStructure.Element;
import Xpath.XPathOperator;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * This class provides the XML logic used in the XML commands.
 * the String fileContent is passed from the TextEditor and then altered.
 * We use an arraylist of elements to store all the XML elements.
 * The order of the elements in the arraylist must be as it is in the file,
 * since all the algorithms iterate this arraylist.
 */
public class XMLEditor {
    private String fileContent;
    private ArrayList<Element> elements;

    /**
     * @param fileContent the content of the file(passed by the TextEditor).
     * This method extracts the text content into an XML structure. it also validates it
     * to follow our rules.
     */
    public void open(String fileContent) {
        this.fileContent = fileContent;
        DataExtractor dataExtractor = new DataExtractor();
        DataValidator dataValidator = new DataValidator();
        this.elements = dataValidator.validate(dataExtractor.extract(fileContent));
        toText();
    }

    /**
     * This method transforms our new XML structure into a String which is
     * formatted as an XML file. the Algorithm iterates the arraylist
     * and transforms the elements into text.
     */
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

        ArrayList<Element> newElements = new ArrayList<>(elements);
        for (Element element : elements) {
            if (!newElements.contains(element)) {
                continue;
            }

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

    /**
     * Prints the String which contains our formatted XML structure.
     */
    public String print() {
        toText();
        return fileContent;
    }

    /**
     * @param command used to extract the ID and Key from the user input.
     * The method iterates through the arraylist until an element with the ID and attribute Key is found.
     * If found, it prints it's value.
     */
    public String select(String[] command) {
        if (command.length != 3) {
            return "Wrong number of arguments. command is: select <id> <key>";
        }
        String id = command[1];
        String key = command[2];

        Element element = findElementById(id);
        if (element == null) {
            return "No element with such id";
        } else {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> set : element.getAttributes().entrySet()) {
                if (set.getKey().equals(key)) {
                    sb.append(set.getKey()).append("=").append(set.getValue()).append('\n');

                }
            }
            if(sb.toString().equals("")){
                return "No such attribute key";
            }
            else {
                return sb.toString();
            }
        }
    }

    /**
     * @param command used to extract the ID, Key and Value from the user input.
     * The method iterates through the arraylist of elements until it finds an element
     * with the given key and value. Then it changes it's attribute's value.
     */
    public String set(String[] command) {
        if (command.length != 4) {
            return "Wrong number of arguments. command is: set <id> <key> <value>";
        }
        String id = command[1];
        String key = command[2];
        String value = command[3];

        if (key.equals("id")) {
            return "Cannot change id attribute";
        }

        Element element = findElementById(id);
        if (element == null) {
           return "No element with such id";
        } else {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> set : element.getAttributes().entrySet()) {
                if (set.getKey().equals(key)) {
                    set.setValue(value);
                    sb.append("Value of ").append(key).append(" set to ").append(value).append('\n');
                    toText();

                }
            }
            if(!sb.toString().equals(""))
                return sb.toString();
            return "Element with this id has no such key";
        }
    }

    /**
     * @param command used to extract the ID from the user input.
     * If an object in the arraylist of elements with such id is found,
     * it's innerelements(field in the object which is arraylist) is iterated.
     * it's children are printed.
     */
    public String children(String[] command) {
        if (command.length != 2) {
            return "Wrong number of arguments. command is: children <id>";
        }
        String id = command[1];

        Element element = findElementById(id);
        if (element == null) {
            return "No element with such id";
        } else {
            if (element.getInnerElements().size() > 0) {
                StringBuilder sb = new StringBuilder();
                for (Element childElement : element.getInnerElements()) {
                    if (childElement.getAttributes().size() > 0) {

                        sb.append("Element: ").append(childElement.getTagName()).append(" has attributes: ").append("\n");
                        for (Map.Entry<String, String> childSet : childElement.getAttributes().entrySet()) {
                            sb.append(childSet.getKey()).append(" = ").append(childSet.getValue()).append("\n");
                        }
                    }
                    else {
                        sb.append("Element ").append(childElement.getTagName()).append(" has no attributes");
                    }
                    if (childElement.getText() != null) {
                        sb.append(childElement.getTagName()).append(" has text:").append(childElement.getText()).append("\n");
                    }
                }
                return sb.toString();
            } else {
                return "Element has no child elements";
            }
        }


    }

    /**
     * @param command used to extract the ID from the user input.
     * used to display the n-th child of an element. If an element with such ID is found
     * in the arraylist of elements. It's n-th child(in the Element's arraylist innerelements) is diplayed.
     */
    public String child(String[] command) {
        if (command.length != 3) {
            return "Wrong number of arguments. command is: child <id> <n>";
        }
        String id = command[1];
        int n = Integer.parseInt(command[2]);
        if (n <= 0) {
            return "n must be a positive number";
        }

        Element element = findElementById(id);
        if (element == null) {
            return "No element with such id";
        } else {
            if (element.getInnerElements().size() == 0) {
               return ("No child elements");
            } else if (element.getInnerElements().size() < n) {
                return ("Number you have given: " + n + " is larger than number of child elements " + element.getInnerElements().size());
            } else {
                return "child element number " + n + " is : " + element.getInnerElements().get(n - 1).getTagName();
            }
        }
    }

    /**
     * @param command used to extract the ID from the user input.
     * if an element with such ID in the arraylist of elements is found,
     * and it has Text, it is displayed into the console.
     */
    public String elementText(String[] command) {
        if (command.length != 2) {
           return "Wrong number of arguments. command is: text <id>";
        }
        String id = command[1];
        Element element = findElementById(id);
        if (element == null) {
           return "No element with such id";
        } else {
            if (element.getText() == null) {
                return "Element " + element.getTagName() + " has no text";
            } else {
                return "Element " + element.getTagName() + " has text: " + element.getText();
            }
        }
    }

    /**
     * @param command used to extract the ID and Name from the user input.
     * If an element with such ID is found in the arraylist of elements,
     * The new child is created and added either
     * as its first child(if the element doesn't have children)
     * as its last child(if the element has children)
     * the new child is also added in the arraylist of all elements in the correct position.
     */
    public String newChild(String[] command) {
        if (command.length != 3) {
            return "Wrong number of arguments. command is: newchild <id> <name>";
        }
        String id = command[1];
        String name = command[2];
        Element element = findElementById(id);
        if (element == null) {
            return "No element with such id";
        } else {
            Element newChild = new Element();
            newChild.setTagName(name);
            newChild.setParent(element);
            int index = 0;
            for (int i = 0; i < elements.size(); i++) {
                if (element.equals(elements.get(i))) {
                    if (element.getInnerElements().size() == 0) {
                        index = i + 1;
                    } else {
                        Element counterElement = element;
                        while (counterElement.getInnerElements().size() > 0) {
                            counterElement = counterElement.getInnerElements().get(counterElement.getInnerElements().size() - 1);
                        }
                        for (int t = i; t < elements.size(); t++) {
                            if (counterElement.equals(elements.get(t))) {
                                index = t + 1;
                            }
                        }
                    }
                }
            }
            elements.add(index, newChild);
            element.getInnerElements().add(newChild);
            DataValidator dataValidator = new DataValidator();
            elements = dataValidator.validate(elements);
            toText();
            return "New child added";
        }
    }

    /**
     * @param command used to extract the ID and Key from the user input.
     * If an element with such ID is found and it has an attribute with the given key,
     * the attribute is deleted from the Element's hashmap of attributes.
     */
    public String deleteAttribute(String[] command) {
        if (command.length != 3) {
            return "Wrong number of arguments. command is: delete <id> <key>";
        }
        String id = command[1];
        String key = command[2];
        if (key.equals("id")) {
            return "Cannot delete element id";
        }
        Element element = findElementById(id);
        if (element == null) {
            return "No element with such id";
        }
        for (Map.Entry<String, String> set : element.getAttributes().entrySet()) {
            if (Objects.equals(set.getKey(), key)) {
                element.getAttributes().remove(key);
                toText();
                return "Successfully deleted key " + key + " from element " + element.getTagName();
            }
        }
        return "No such key found in element " + element.getTagName() + " with id" + id;

    }

    /**
     * @param depth the depth of the XML element.
     * @return a String of empty spaces(indent) based on the depth of the element.
     */
    public String getIndent(int depth) {
        return "   ".repeat(Math.max(0, depth - 1));
    }

    public String getFileContent() {
        return fileContent;
    }

    /**
     * @param id element ID
     * @return an element with the following ID if it exists.
     */
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

    /**
     * @param command used for extracting xpath from user input.
     * Creates an instance of the XPathOperator class which is used for executing XPath on our XML structure.
     */
    public String executeXpath(String[] command) {
        if (command.length != 2) {
            return "Wrong number of arguments. command is: xpath <xpath>";
        }
        XPathOperator xPathOperator = new XPathOperator(elements, command[1]);
        String result = xPathOperator.extractXpath();
        if(!result.equals("")) {
            return result;
        }
        return xPathOperator.runXpathInstructions();

    }


}



