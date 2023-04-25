package Parser;

import XMLStructure.Element;

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
                    if(set.getKey().equals("id")){
                        if(uniqueIds.contains(set.getValue())){
                            int t = 1;
                            while(uniqueIds.contains(set.getValue())){
                                set.setValue(set.getValue() + "_" + t);
                                t++;
                            }
                        }
                        uniqueIds.add(set.getValue());

                    }
                }
            }
        }
        for(int i = 0;i<allElements.size();i++){
            if(!allElements.get(i).getAttributes().containsKey("id")){
                String newIdString = "";
                int newId = 0;
                for(int t = 1; t < 10000; t++){
                    newId = t;
                    newIdString = Integer.toString(newId);
                    if(!uniqueIds.contains(newIdString)){
                        uniqueIds.add(newIdString);
                        break;
                    }
                }
                allElements.get(i).getAttributes().put("id",newIdString);
            }
        }





        return allElements;

    }
}
