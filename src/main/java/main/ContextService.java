package main;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Виталий on 03.04.2015.
 */
public class ContextService {
    private Map<Class<?>, Object> context = new HashMap<>();

    public void add (Class<?> clazz, Object object){
        if (context.containsKey(clazz)){
            //Exception
        }
        context.put(clazz, object);
    }

    public Object get(Class<?> clazz){
        return context.get(clazz);
    }
}
