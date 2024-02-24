package com.fureniku.storagedisplay.blocks;

import com.fureniku.storagedisplay.blocks.entities.BookshelfBlockEntity;
import com.fureniku.metropolis.blockentity.MetroBlockEntity;
import com.fureniku.metropolis.blocks.decorative.MetroEntityBlockDecorative;
import com.fureniku.metropolis.blocks.decorative.helpers.RotationHelper;
import com.fureniku.metropolis.datagen.TextureSet;
import com.fureniku.metropolis.utils.SimpleUtils;
import com.fureniku.metropolis.utils.Debug;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;


public abstract class BookshelfEntityBlock extends MetroEntityBlockDecorative {

    public static RegistryObject<BlockEntityType<MetroBlockEntity>> ENTITY;

    int sizeX = 8;
    int sizeY = 2;

    float xMin = 1.0f/32.0f;
    float xMax = 1.0f - 1.0f/32.0f;
    float yMin = 1.0f/32.0f;
    float yMax = 1.0f - 1.0f/32.0f;

    public BookshelfEntityBlock(Properties props, VoxelShape shape, String modelDir, String modelName, String tag, boolean dynamicShape, TextureSet... textures) {
        super(props, shape, modelDir, modelName, tag, dynamicShape, textures);
    }

    public void onHitSection(double x, double y) {
        Debug.Log("Hitting selection point " + x + ", " + y);
        if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
            float divisionsX = (xMax - xMin) / sizeX;
            float divisionsY = (yMax - yMin) / sizeY;
            int posX = SimpleUtils.clamp((int)(x / divisionsX) + 1, 1, sizeX);
            int posY = SimpleUtils.clamp((int)(y / divisionsY) + 1, 1, sizeY);
            Debug.Log("Slot index is " + posX + ", " + posY);
        }
    }

    @Override
    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BookshelfBlockEntity(pos, state);
    }

    @Override
    public InteractionResult onUse(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        boolean hitFront = state.getValue(RotationHelper.DIRECTION).getOpposite() == result.getDirection();
        if (hand == InteractionHand.MAIN_HAND && !level.isClientSide && hitFront) {
            Vec3 localHit = localHIt(result.getLocation(), pos);
            switch (result.getDirection()) {
                case NORTH:
                    onHitSection(1-localHit.x, localHit.y);
                    break;
                case EAST:
                    onHitSection(1-localHit.z, localHit.y);
                    break;
                case SOUTH:
                    onHitSection(localHit.x, localHit.y);
                    break;
                case WEST:
                    onHitSection(localHit.z, localHit.y);
                    break;
            }
        }


        return InteractionResult.PASS;
    }

    private Vec3 localHIt(Vec3 hitLocation, BlockPos pos) {
        double x = hitLocation.x - pos.getX();
        double y = hitLocation.y - pos.getY();
        double z = hitLocation.z - pos.getZ();
        return new Vec3(x,y,z);
    }
}
