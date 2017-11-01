package com.qslib.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Dang on 5/26/2016.
 */
public class CollectionUtils {
    /**
     * convert Map<String, List<MapEntity<T>> to List<Map<T>>
     *
     * @param maps
     * @param <T>
     * @return
     */
    public static <T> List<MapEntity<T>> convertMapToList(Map<String, List<T>> maps) {
        List<MapEntity<T>> mapEntities = new ArrayList<>();

        try {
            if (maps != null && maps.size() > 0) {
                for (Map.Entry<String, List<T>> entry : maps.entrySet()) {
                    mapEntities.add(new MapEntity<>(entry.getKey(), entry.getValue()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapEntities;
    }
}
