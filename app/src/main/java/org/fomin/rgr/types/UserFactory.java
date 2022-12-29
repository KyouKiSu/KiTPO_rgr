package org.fomin.rgr.types;

import java.util.ArrayList;
import java.util.Arrays;

public class UserFactory {
    public final static ArrayList<UserType> types = new ArrayList<>(Arrays.asList(new UserInteger(),new UserFloat(),new UserVector()));

    static public UserType getBuilderByName(String name) {
        for(UserType element: types){
            if(element.getClassName().equalsIgnoreCase(name)){
                return element.create();
            }
        }
        return null;
    }
}