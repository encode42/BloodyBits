package com.cravencraft.bloodybits.client.renderer;

import com.cravencraft.bloodybits.BloodyBitsMod;
import com.cravencraft.bloodybits.config.CommonConfig;
import com.cravencraft.bloodybits.entity.custom.BloodChunkEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/**
 * This class will render the BloodChunkEntity on the client. It will use values defined within the BloodChunkEntity
 * (xMin, xMax, etc.) to render a rectangle dynamically that will act as a blood chunk.
 */
public class BloodChunkRenderer extends EntityRenderer<BloodChunkEntity> {
    public static final ResourceLocation BLOOD_CHUNK_0 = new ResourceLocation(BloodyBitsMod.MODID, "textures/entity/blood_chunk_0.png");

    public BloodChunkRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * TODO: Address TODOs for the if statements
     *
     * Renders a rectangle for blood chunks based on the position values defined in the BloodChunkEntity class.
     *
     * @param entity
     * @param pEntityYaw
     * @param pPartialTicks
     * @param pPoseStack
     * @param pBuffer
     * @param pPackedLight
     */
    @Override
    public void render(BloodChunkEntity entity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {

        // Stops the blood from rendering as black when in a dark location such as a ceiling.
        int correctedPackedLight = Math.max(pPackedLight, 10485776);
        pPoseStack.pushPose();
        // todo: I know this is dumb. I'll reverse it after testing.
        //TODO: For now this is the most accurate one. Maybe can play around with other methods later that
        //      Don't cause that small clipping through a block that only I will really notice.
        //TODO: Can probably put most of the below code in these blocks.
        if (entity.entityDirection == null) {
            pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, entity.xRotO, entity.getXRot())));
        }
        else if (entity.entityDirection.equals(Direction.NORTH) || entity.entityDirection.equals(Direction.SOUTH)) {
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        }
        else if (entity.entityDirection.equals(Direction.UP) || entity.entityDirection.equals(Direction.DOWN)) {
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }

        pPoseStack.scale(0.05625F, 0.05625F, 0.05625F);
        VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));
        PoseStack.Pose posestack$pose = pPoseStack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();

        // Right side
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMin, entity.yMin, entity.zMax, 0.0F, 0.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMax, entity.yMin, entity.zMax, 0.0F, 1.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMax, entity.yMax, entity.zMax, 1.0F, 1.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMin, entity.yMax, entity.zMax, 1.0F, 0.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);

        // Left side
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMin, entity.yMax, entity.zMin, 0.0F, 0.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMax, entity.yMax, entity.zMin, 0.0F, 1.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMax, entity.yMin, entity.zMin, 1.0F, 1.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMin, entity.yMin, entity.zMin, 1.0F, 0.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);

        // Top Side
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMin, entity.yMax, entity.zMin, 0.0F, 0.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMax, entity.yMax, entity.zMin, 0.0F, 1.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMax, entity.yMax, entity.zMax, 1.0F, 1.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMin, entity.yMax, entity.zMax, 1.0F, 0.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);

        // Bottom Side
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMin, entity.yMin, entity.zMin, 0.0F, 0.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMax, entity.yMin, entity.zMin, 0.0F, 1.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMax, entity.yMin, entity.zMax, 1.0F, 1.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMin, entity.yMin, entity.zMax, 1.0F, 0.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);

        // Front side
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMin, entity.yMax, entity.zMin, 0.0F, 0.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMin, entity.yMin, entity.zMin, 0.0F, 1.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMin, entity.yMin, entity.zMax, 1.0F, 1.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMin, entity.yMax, entity.zMax, 1.0F, 0.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);

        // Back side
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMax, entity.yMax, entity.zMin, 0.0F, 0.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMax, entity.yMax, entity.zMax, 0.0F, 1.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMax, entity.yMin, entity.zMax, 1.0F, 1.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);
        this.vertex(matrix4f, matrix3f, vertexConsumer, entity.xMax, entity.yMin, entity.zMin, 1.0F, 0.0F, 0, 0, 0, correctedPackedLight, entity.red, entity.green, entity.blue, 255);

        pPoseStack.popPose();
    }

    /**
     * Convenient helper method to simplify vertex drawing.
     */
    public void vertex(Matrix4f pMatrix, Matrix3f pNormal, VertexConsumer pConsumer, float pX, float pY, float pZ, float pU, float pV, int pNormalX, int pNormalZ, int pNormalY, int packedLight, int red, int green, int blue, int alpha) {
        pConsumer
                .vertex(pMatrix, pX, pY, pZ)
                .color(red, green, blue, alpha)
                .uv(pU, pV)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(pNormal, (float)pNormalX, (float)pNormalY, (float)pNormalZ)
                .endVertex();
    }

    /**
     * TODO: Create more blood chunk textures & randomize it similar to blood sprays.
     *
     * Gets a random blood chunk texture to render for the entity.
     *
     * @param bloodChunkEntity
     * @return
     */
    @Override
    public ResourceLocation getTextureLocation(BloodChunkEntity bloodChunkEntity) {
        return BLOOD_CHUNK_0;
    }

    /**
     * TODO: These just hold spatter textures for now. Need to implement more blood chunk textures.
     *
     * Stores the random blood chunk textures
     *
     * @param randomInt
     * @return
     */
    private ResourceLocation getRandomSpatterTexture(int randomInt) {
        return switch (randomInt) {
            case 1 -> new ResourceLocation(BloodyBitsMod.MODID, "textures/entity/spatter_1.png");
            case 2 -> new ResourceLocation(BloodyBitsMod.MODID, "textures/entity/spatter_2.png");
            case 3 -> new ResourceLocation(BloodyBitsMod.MODID, "textures/entity/spatter_3.png");
            default -> new ResourceLocation(BloodyBitsMod.MODID, "textures/entity/spatter_0.png");
        };
    }
}
