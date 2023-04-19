package Parser;

import java.util.Set;
import java.util.UUID;

public class IdGenerator {
    public static String generateUniqueId(Set<String> usedIds) {
        String id = UUID.randomUUID().toString();
        while (usedIds.contains(id)) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }
}
