package utils;

import java.util.Collection;

/**
 *
 * @author SRamos
 */
public class ListUtil {

    public synchronized static <T> T first(Collection<T> list) {
        if (!isNullOrEmpty(list)) {
            return list.iterator().next();
        }

        return null;
    }

    public synchronized static boolean isNullOrEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

}