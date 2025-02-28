package net.nate.mcmod;

import com.mojang.logging.LogUtils;
import net.nate.mcmod.block.ModBlocks;
import net.nate.mcmod.block.entity.ModBlockEntities;
import net.nate.mcmod.block.entity.renderer.PedestalBlockEntityRenderer;
import net.nate.mcmod.component.ModDataComponentTypes;
import net.nate.mcmod.effect.ModEffects;
import net.nate.mcmod.enchantment.ModEnchantmentEffects;
import net.nate.mcmod.entity.ModEntities;
import net.nate.mcmod.entity.client.ChairRenderer;
import net.nate.mcmod.entity.client.TomahawkProjectileRenderer;
import net.nate.mcmod.entity.client.TriceratopsRenderer;
import net.nate.mcmod.item.ModCreativeModeTabs;
import net.nate.mcmod.item.ModItems;
import net.nate.mcmod.loot.ModLootModifiers;
import net.nate.mcmod.particle.AlexandriteParticles;
import net.nate.mcmod.particle.ModParticles;
import net.nate.mcmod.potion.ModPotions;
import net.nate.mcmod.sound.ModSounds;
import net.nate.mcmod.util.ModItemProperties;
import net.nate.mcmod.villager.ModVillagers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MCMod.MOD_ID)
public class MCMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "mcmod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public MCMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModDataComponentTypes.register(modEventBus);
        ModSounds.register(modEventBus);

        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);

        ModEnchantmentEffects.register(modEventBus);
        ModEntities.register(modEventBus);

        ModVillagers.register(modEventBus);
        ModParticles.register(modEventBus);

        ModLootModifiers.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)  {
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModItems.KOHLRABI.get(), 0.4f);
            ComposterBlock.COMPOSTABLES.put(ModItems.KOHLRABI_SEEDS.get(), 0.15f);
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.ALEXANDRITE);
            event.accept(ModItems.RAW_ALEXANDRITE);
        }

        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.ALEXANDRITE_BLOCK);
            event.accept(ModBlocks.RAW_ALEXANDRITE_BLOCK);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModItemProperties.addCustomItemProperties();

            EntityRenderers.register(ModEntities.TRICERATOPS.get(), TriceratopsRenderer::new);
            EntityRenderers.register(ModEntities.TOMAHAWK.get(), TomahawkProjectileRenderer::new);

            EntityRenderers.register(ModEntities.CHAIR.get(), ChairRenderer::new);
        }

        @SubscribeEvent
        public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.ALEXANDRITE_PARTICLES.get(), AlexandriteParticles.Provider::new);
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.PEDESTAL_BE.get(), PedestalBlockEntityRenderer::new);
        }
    }
}
