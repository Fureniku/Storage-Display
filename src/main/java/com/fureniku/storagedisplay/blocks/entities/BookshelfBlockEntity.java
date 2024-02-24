package com.fureniku.storagedisplay.blocks.entities;

import com.fureniku.storagedisplay.blocks.StorageBaseEntityBlock;
import com.fureniku.metropolis.blockentity.MetroBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BookshelfBlockEntity extends MetroBlockEntity {

    private final ItemStackHandler inventory = new ItemStackHandler(16) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
    };

    public BookshelfBlockEntity(BlockPos pos, BlockState state) {
        super(StorageBaseEntityBlock.ENTITY.get(), pos, state);
    }

    @Nonnull
    @Override
    public void saveAdditional(CompoundTag tag) {
        CompoundTag compound = inventory.serializeNBT();
        tag.put("inv", compound);
        //TODO save custom data
    }

    @Override
    public void load(CompoundTag tag) {
        CompoundTag invTag = tag.getCompound("inv");
        inventory.deserializeNBT(invTag);
        //TODO load custom data
        super.load(tag);
    }
}
