package net.nate.mcmod.villager;

import com.google.common.collect.ImmutableSet;
import net.nate.mcmod.MCMod;
import net.nate.mcmod.block.ModBlocks;
import net.nate.mcmod.sound.ModSounds;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, MCMod.MOD_ID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, MCMod.MOD_ID);

    public static final RegistryObject<PoiType> NATHAN_POI = POI_TYPES.register("nathan_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.CHAIR.get().getStateDefinition().getPossibleStates()),
                    1, 1));

    public static final RegistryObject<VillagerProfession> NATHANGER = VILLAGER_PROFESSIONS.register("nathanger",
            () -> new VillagerProfession("nathanger", holder -> holder.value() == NATHAN_POI.get(),
                    holder -> holder.value() == NATHAN_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
                    ModSounds.MAGIC_BLOCK_HIT.get()));



    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
