package XMLStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Element {
    private String tagName;
    private HashMap<String,String> attributes;
    private ArrayList<Element> innerElements;

    private Element parent;

    private String text;

    private Integer depth;

    public Element() {
        this.innerElements = new ArrayList<Element>();
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

    public void setInnerElements(ArrayList<Element> innerElements) {
        this.innerElements = innerElements;
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

    public Element getChildByTagName(String tagName){
        for(int i = 0;i<innerElements.size();i++){
            if(Objects.equals(innerElements.get(i).getTagName(), tagName)){
                return innerElements.get(i);
            }

        }
        return null;
    }
}
