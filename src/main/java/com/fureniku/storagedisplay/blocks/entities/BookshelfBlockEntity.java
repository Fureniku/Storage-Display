package com.fureniku.storagedisplay.blocks.entities;

import com.fureniku.storagedisplay.blocks.BookshelfEntityBlock;
import com.fureniku.metropolis.blockentity.MetroBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BookshelfBlockEntity extends MetroBlockEntity {

    public BookshelfBlockEntity(BlockPos pos, BlockState state) {
        super(BookshelfEntityBlock.ENTITY.get(), pos, state);
    }

    /*@Override
    public Component getDisplayName() {
        return Component.literal("Bookshelf (localize me!");
    }

    @Nullable
    @Override
    public AbstractcontainerMenu createMenu(int id, Inventory inv, Player player) {
        return new BookshelfMenu(id, inv);
    }*/
}
