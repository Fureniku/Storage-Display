package com.fureniku.storagedisplay.blocks;

import com.fureniku.metropolis.datagen.TextureSet;
import com.fureniku.storagedisplay.blocks.entities.BookshelfBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public abstract class BookshelfEntityBlock extends StorageBaseEntityBlock {

    public BookshelfEntityBlock(Properties props, VoxelShape shape, String modelDir, String modelName, TextureSet... textures) {
        super(props, shape, modelDir, modelName, "bookshelf", true, 8, 2, textures);
    }

    @Override
    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BookshelfBlockEntity(pos, state);
    }
}
