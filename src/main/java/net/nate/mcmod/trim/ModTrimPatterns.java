package net.nate.mcmod.trim;

import net.nate.mcmod.MCMod;
import net.nate.mcmod.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTrimPatterns {
    public static final ResourceKey<TrimPattern> NATHAN = ResourceKey.create(Registries.TRIM_PATTERN,
            ResourceLocation.fromNamespaceAndPath(MCMod.MOD_ID, "nathan"));

    public static void bootstrap(BootstrapContext<TrimPattern> context) {
        register(context, ModItems.NATHAN_SMITHING_TEMPLATE.get(), NATHAN);
    }

    private static void register(BootstrapContext<TrimPattern> context, Item item, ResourceKey<TrimPattern> key) {
        TrimPattern trimPattern = new TrimPattern(key.location(), ForgeRegistries.ITEMS.getHolder(item).get(),
                Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())), false);
        context.register(key, trimPattern);
    }
}
