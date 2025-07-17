package rs.majic.onako2.ununennium;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Ununennium implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(Ununennium.class);
    public static final Map<CacheEntry, List<Entity>> cache = new ConcurrentHashMap<>();

    @Override
    public void onInitialize() {
        LOGGER.info("Ununennium is ready to optimise datapack stuff! By Onako2");
    }
}
