package com.fureniku.storagedisplay;

import com.fureniku.metropolis.utils.Debug;
import com.fureniku.storagedisplay.blocks.BlockType;
import com.fureniku.storagedisplay.blocks.BookshelfEntityBlock;
import com.fureniku.storagedisplay.blocks.entities.BookshelfBlockEntity;
import com.fureniku.storagedisplay.client.ber.BookshelfEntityRenderer;
import com.fureniku.metropolis.RegistrationBase;
import com.fureniku.metropolis.blockentity.MetroBlockEntity;
import com.fureniku.metropolis.blocks.decorative.MetroBlockDecorativeBase;
import com.fureniku.metropolis.blocks.decorative.builders.MetroBlockDecorativeBuilder;
import com.fureniku.metropolis.blocks.decorative.helpers.HelperBase;
import com.fureniku.metropolis.blocks.decorative.helpers.RotationHelper;
import com.fureniku.metropolis.datagen.MetroBlockStateProvider;
import com.fureniku.metropolis.utils.CreativeTabSet;
import com.fureniku.metropolis.utils.ShapeUtils;
import com.mojang.datafixers.types.Type;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Supplier;

public class RegistrationDecorations extends RegistrationBase {

    private final String BOOKSHELF = "bookshelf_";

    private final String OAK = "oak";
    private final String SPRUCE = "spruce";
    private final String BIRCH = "birch";
    private final String JUNGLE = "jungle";
    private final String ACACIA = "acacia";
    private final String DARK_OAK = "dark_oak";
    private final String MANGROVE = "mangrove";
    private final String CHERRY = "cherry";
    private final String BAMBOO = "bamboo";
    private final String CRIMSON = "crimson";
    private final String WARPED = "warped";

    private final String[] TYPES = {OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, DARK_OAK, MANGROVE, CHERRY, BAMBOO, CRIMSON, WARPED};

    private CreativeTabSet _decorativeTab;

    private BlockBehaviour.Properties _props = BlockBehaviour.Properties.of().strength(1.0f).sound(SoundType.WOOD);

    public RegistrationDecorations(String modid, IEventBus modEventBus) {
        super(modid, modEventBus);
    }

    @Override
    public void init(IEventBus iEventBus) {
        iEventBus.addListener(this::registerRenderers);
        iEventBus.addListener(this::registerModels);
        HelperBase[] helpers = { new RotationHelper(Block.box(0, 0, 0, 16, 16, 8)) };

        HashMap<String, Supplier<Block>> bookshelves = new HashMap<>(); //this is disgusting and I *love* it.

        for (int i = 0; i < TYPES.length; i++) {
            bookshelves.put(BOOKSHELF + TYPES[i], registerBlock("bookshelf_half", getVanilla(TYPES[i] + "_planks"), helpers, BlockType.BOOKSHELF));
            bookshelves.put(BOOKSHELF + "open_" + TYPES[i], registerBlock("bookshelf_half_open", getVanilla(TYPES[i] + "_planks"), helpers, BlockType.BOOKSHELF));
        }


        BookshelfEntityBlock.ENTITY = registerBlockEntitySet("bookshelf_entity", BookshelfBlockEntity::new, bookshelves);

        _decorativeTab = new CreativeTabSet(getCreativeTabDeferredRegister(),"tab_general", getItem(BOOKSHELF + OAK));
    }

    @SubscribeEvent
    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer((BlockEntityType) BookshelfEntityBlock.ENTITY.get(), BookshelfEntityRenderer::new);
    }

    @SubscribeEvent
    public void registerModels(ModelEvent.RegisterAdditional event) {
        Debug.Log("Registering additional models");
        event.register(new ResourceLocation(StorageDisplay.MODID, "itemmodel/book_1"));
    }

    @Override
    public void generateCreativeTabs() {
        for (int i = 0; i < TYPES.length; i++) {
            _decorativeTab.addItem(getItem(BOOKSHELF + TYPES[i]).get().getDefaultInstance());
            _decorativeTab.addItem(getItem(BOOKSHELF + "open_" + TYPES[i]).get().getDefaultInstance());
        }
    }

    @Override
    protected ArrayList<CreativeTabSet> getCreativeTabs() {
        ArrayList<CreativeTabSet> tabList = new ArrayList<CreativeTabSet>();
        tabList.add(_decorativeTab);
        return tabList;
    }

    @Override
    protected void commonSetup(FMLCommonSetupEvent fmlCommonSetupEvent) {}

    @Override
    protected void clientSetup(FMLClientSetupEvent fmlClientSetupEvent) {}

    @Override
    protected void dataGen(GatherDataEvent gatherDataEvent, DataGenerator dataGenerator, PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        dataGenerator.addProvider(gatherDataEvent.includeClient(), new MetroBlockStateProvider(packOutput, StorageDisplay.MODID, existingFileHelper, StorageDisplay.INSTANCE.registration));
    }

    private Supplier<Block> registerBlock(String modelName, ResourceLocation texture, HelperBase[] helpers, BlockType blockType) {
        return () -> new MetroBlockDecorativeBuilder(_props)
                .setModelDirectory("blocks/")
                .setModelName(modelName)
                .setTextures(texture)
                .setHelpers(new RotationHelper(ShapeUtils.makeShape(new Vec3(16, 16, 8))))
                .buildAs(getBlockFactory(blockType, helpers));
    }

    private <T extends MetroBlockEntity> RegistryObject<BlockEntityType<T>> registerBlockEntitySet(String entityName, BlockEntityType.BlockEntitySupplier<T> blockEntity, HashMap<String, Supplier<Block>> blockClasses) {
        ArrayList<RegistryObject<Block>> validBlocks = new ArrayList<>();

        blockClasses.forEach((name, cls) -> {
            RegistryObject<Block> block = this.blockRegistry.register(name, cls);
            RegistryObject<Item> blockItem = this.itemRegistry.register(name, () -> new BlockItem((Block)block.get(), new Item.Properties()));

            this.addBlock(name, block);
            this.addItem(name, blockItem);

            validBlocks.add(block);
        });

        return this.blockEntityRegistry.register(entityName, () -> {
            Block[] blocks = new Block[validBlocks.size()];
            for (int i = 0; i < validBlocks.size(); i++) {
                blocks[i] = validBlocks.get(i).get();
            }
            return BlockEntityType.Builder.of(blockEntity, blocks).build((Type)null);
        });
    }

    private static MetroBlockDecorativeBase.MetroBlockStateFactory getBlockFactory(BlockType type, HelperBase... helpersIn) {
        switch (type) {
            case BOOKSHELF:
                return (props, shape, modelDir, modelName, tag, dynamicShape, textures) -> new BookshelfEntityBlock(props, shape, modelDir, modelName, textures) {
                    @Override
                    public ArrayList<HelperBase> getHelpers() {
                        return new ArrayList<>(Arrays.asList(helpersIn));
                    }
                };
        }
        return null;
    }

    private ResourceLocation getVanilla(String textureName) {
        return new ResourceLocation("minecraft", "block/" + textureName);
    }
}
