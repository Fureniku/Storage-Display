package com.fureniku.storagedisplay.client.ber;

import com.fureniku.storagedisplay.blocks.entities.BookshelfBlockEntity;
import com.fureniku.metropolis.blocks.decorative.helpers.RotationHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;

public class BookshelfEntityRenderer implements BlockEntityRenderer<BookshelfBlockEntity> {

    ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

    public BookshelfEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(BookshelfBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int light, int p_112312_) {
        BlockState state = entity.getBlockState();
        Level level = entity.getLevel();

        poseStack.pushPose();
        ItemStack item = Items.BOOK.getDefaultInstance();
        rotateItem(poseStack, state, 0, 0, 0);
        renderItem(item, level, poseStack, bufferSource, 0.75f, 0.75f, 0.75f, 0.5f, light);
        poseStack.popPose();
    }

    private void rotateItem(PoseStack poseStack, BlockState state, float x, float y, float z) {
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose((new Quaternionf()).rotationZ(x * ((float)Math.PI / 180F)));
        poseStack.mulPose((new Quaternionf()).rotationY((y + getRotation(state.getValue(RotationHelper.DIRECTION))) * ((float)Math.PI / 180F)));
        poseStack.mulPose((new Quaternionf()).rotationZ(z * ((float)Math.PI / 180F)));
        poseStack.translate(-0.5, -0.5, -0.5);
    }

    private void renderItem(ItemStack item, Level level, PoseStack poseStack, MultiBufferSource bufferSource, float x, float y, float z, float scale, int light) {
        poseStack.translate(x, y, z);
        poseStack.scale(scale, scale, scale);
        BakedModel model = itemRenderer.getModel(item, level, null, 0);
        itemRenderer.render(item, ItemDisplayContext.FIXED, false, poseStack, bufferSource, light, OverlayTexture.NO_OVERLAY, model);
    }

    private int getRotation(Direction dir) {
        switch (dir) {
            case NORTH -> { return 180; }
            case EAST  -> { return 90; }
            case SOUTH -> { return 0; }
            case WEST  -> { return 270; }
        }
        return 0;
    }
}
