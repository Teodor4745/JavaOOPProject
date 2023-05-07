package Parser;

import XMLStructure.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Validates all elements so that they follow the implied rules
 */
public class DataValidator {
    /**
     * @param allElements an ArrayList of all elements
     * @return  returns the new modified ArrayList that is validated
     * Checks all elements for their IDs so that there are no duplicates and every element has an ID
     */
    public ArrayList<Element> validate(ArrayList<Element> allElements){
        Set<String> uniqueIds = new HashSet<>();

        for (Element element : allElements) {

            if (element.getAttributes().containsKey("id")) {
                for (Map.Entry<String, String> set : element.getAttributes().entrySet()) {
                    if (set.getKey().equals("id")) {
                        if (uniqueIds.contains(set.getValue())) {
                            int t = 1;
                            while (uniqueIds.contains(set.getValue())) {
                                set.setValue(set.getValue() + "_" + t);
                                t++;
                            }
                        }
                        uniqueIds.add(set.getValue());

                    }
                }
            }
        }
        for (Element element : allElements) {
            if (!element.getAttributes().containsKey("id")) {
                String newIdString = "";
                int newId;
                for (int t = 1; t < 10000; t++) {
                    newId = t;
                    newIdString = Integer.toString(newId);
                    if (!uniqueIds.contains(newIdString)) {
                        uniqueIds.add(newIdString);
                        break;
                    }
                }
                element.getAttributes().put("id", newIdString);
            }
        }





        return allElements;

    }
}
