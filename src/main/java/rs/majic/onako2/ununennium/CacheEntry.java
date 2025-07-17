package rs.majic.onako2.ununennium;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;
import java.util.function.Predicate;

import static rs.majic.onako2.ununennium.PredicateStructureComparator.structurallySame;

public class CacheEntry {
    public CommandSourceStack source;
    public ServerLevel level;
    public Vec3 vec3;
    public Predicate<Entity> predicate;

    public CacheEntry(CommandSourceStack source, ServerLevel level, Vec3 vec3, Predicate<Entity> predicate) {
        this.source = source;
        this.level = level;
        this.vec3 = vec3;
        this.predicate = predicate;
    }

    // for debugging
    public String toString() {
        return "CacheEntry: " + level + ", " + vec3 + ", predicate=" + (predicate == null ? "null" : predicate.getClass().getName());
    }

    // need custom equals because of the predicates
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheEntry that = (CacheEntry) o;
        return Objects.equals(level, that.level) &&
                Objects.equals(vec3, that.vec3) &&
                Objects.equals(source, that.source)
                && structurallySame(predicate, that.predicate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, vec3, source);
    }
}
