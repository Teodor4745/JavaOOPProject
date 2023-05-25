package XMLStructure;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class resembles an XML element.
 * Every element has fields: tag name, attributes, inner elements and text.
 * It also has fields: parent and depth. Having these fields greatly helps with operations and algorithms with any element.
 * The constructor only initializes the fields that are collections.
 * The methods are getters and setters. They let us access the element's fields.
 */
public class Element {
    private String tagName;
    private HashMap<String,String> attributes;
    private final ArrayList<Element> innerElements;

    private Element parent;

    private String text;

    private Integer depth;

    public Element() {
        this.innerElements = new ArrayList<>();
        this.attributes = new HashMap<>();
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<Element> getInnerElements() {
        return innerElements;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Element getParent() {
        return parent;
    }

    public void setParent(Element parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
