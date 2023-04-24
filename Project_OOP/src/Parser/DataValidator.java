package Parser;

import XML.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataValidator {
    public ArrayList<Element> validate(ArrayList<Element> allElements){
        Set<String> uniqueIds = new HashSet<>();

        for(int i = 0;i<allElements.size();i++){

            if(allElements.get(i).getAttributes().containsKey("id")){
                for(Map.Entry<String,String> set : allElements.get(i).getAttributes().entrySet()){
                    if(set.getKey() == "id" && uniqueIds.contains(set.getValue())){
                        int t = 0;
                        while(uniqueIds.contains(set.getValue())){
                            set.setValue(set.getValue() + "_" + t);
                        }
                    }
                }
            }
        }
        for(int i = 0;i<allElements.size();i++){
            if(!allElements.get(i).getAttributes().containsKey("id")){
                int newId = ((int) ((Math.random() * (1000 - 0))));
                String newIdString = Integer.toString(newId);
                while(uniqueIds.contains(newIdString)){
                    newId = ((int) ((Math.random() * (1000 - 0))));
                    newIdString = Integer.toString(newId);
                }
                allElements.get(i).getAttributes().put("id",newIdString);
            }
        }





        return allElements;

    }
}
