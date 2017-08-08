package common.libraries;

import java.util.Map;

/**
 * Created by zipdoc on 2017. 4. 21..
 */

public class Singleton {
    private static Map<Class<? extends Singleton>,Singleton> map = new java.util.HashMap<Class<? extends Singleton>, Singleton>();
    public static <T extends Singleton> T getInstance(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        if(map.containsKey(clazz)) {
            return (T) map.get(clazz);
        }
        else {
            T instance = clazz.newInstance();
            map.put(clazz, instance);
            return instance;
        }
    }
}
