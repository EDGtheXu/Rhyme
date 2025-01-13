package rhymestudio.rhyme.core.entity.plants.prefabs;

import rhymestudio.rhyme.core.entity.AbstractPlant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CardLevelModifier<T extends AbstractPlant> {
    private final Map<Integer, List<Consumer<T>>> levelModifiers;
    public CardLevelModifier(Map<Integer, List<Consumer<T>>> levelModifiers) {
        this.levelModifiers = levelModifiers;
    }
    public void applyModifiers(T plant, int level) {
        if (levelModifiers.containsKey(level)) {
            levelModifiers.get(level).forEach(modifier -> modifier.accept(plant));
            return;
        }
        // 如果没有找到对应的level，则尝试查找比当前level低的level
        for (int i = level - 1; i >= 0; i--) {
            if (levelModifiers.containsKey(i)) {
                levelModifiers.get(i).forEach(modifier -> modifier.accept(plant));
                return;
            }
        }
    }

    public static <T extends AbstractPlant> builder<T> builder() {
        return new builder<>();
    }

    public static class builder<T extends AbstractPlant>{
        private final Map<Integer, List<Consumer<T>>> levelModifiers;
        public builder() {
            levelModifiers = new java.util.HashMap<>();
        }
        public builder<T> addModifier(int level, Consumer<T> modifier) {
            if (levelModifiers.containsKey(level)) {
                levelModifiers.get(level).add(modifier);
            } else {
                levelModifiers.put(level, new ArrayList<>(Collections.singletonList(modifier)));
            }
            return this;
        }
        public CardLevelModifier<T> buildLevelModifier() {
            return new CardLevelModifier<>(levelModifiers);
        }
    }

}
