package Parser;

import java.util.HashMap;

/**
 * Extracts the attributes from a tag opening
 */
public class AttributeExtractor {

    /**
     * @param fullTag the full opening tag
     * @return  a HashMap containing the different attributes
     * The method iterates through the opening tag and extracts all the attributes in it into a HashMap.
     * The tag name is added as an attribute and later removed and added into the Element tagName field.
     */
    public HashMap<String,String> extractAttributes(String fullTag){
        HashMap<String,String> attributes = new HashMap<>();
        boolean initTag = false;

        for(int i = 0;i<fullTag.length();i++){
            if(!initTag) {
                while (fullTag.charAt(i) != ' ' && i < fullTag.length() - 1) {
                    i++;
                }
                String tagName;
                if(i+1 == fullTag.length()){
                     tagName = fullTag.substring(0,i+1);
                }
                else{
                     tagName = fullTag.substring(0, i);
                }

                attributes.put("&tagName", tagName);
                initTag = true;
            }
            while(fullTag.charAt(i) == ' ' || fullTag.charAt(i) == '"'){
                i++;
            }
            while(fullTag.charAt(i) != ' ' && i<fullTag.length()-1 && fullTag.charAt(i) != '"'){
                int j = i;
                while(fullTag.charAt(j) != '='){
                    j++;
                }
                String key = fullTag.substring(i,j);
                i = j+2;
                j = i;
                while(fullTag.charAt(j) != '"' && j<fullTag.length()-1){
                    j++;
                }
                String value = fullTag.substring(i,j);
                i = j;
                attributes.put(key,value);
            }
        }

        return attributes;

    }
}
