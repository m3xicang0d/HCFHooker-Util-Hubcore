package dev.jesusmx.hooker.external_hooker.interfaces;

import java.util.UUID;

/**
 * @Author JesusMX , FxMxGRAGFX
 * @Project TacoHooker, SharkHooker
 **/

public interface HCFCore {

    /* true/false */
    boolean isDeathban(UUID uuid);
    /* ##:##:## */
    String getDeathban(UUID uuid);
    /* # */
    int getLives(UUID uuid);
}
