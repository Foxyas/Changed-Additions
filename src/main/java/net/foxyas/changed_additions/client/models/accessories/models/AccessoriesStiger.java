//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.foxyas.changed_additions.client.models.accessories.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import java.util.List;

import net.foxyas.changed_additions.ChangedAdditionsMod;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.client.renderer.animate.AnimatorPresets;
import net.ltxprogrammer.changed.client.renderer.animate.HumanoidAnimator;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModel;
import net.ltxprogrammer.changed.client.renderer.model.AdvancedHumanoidModelInterface;
import net.ltxprogrammer.changed.client.renderer.model.CorrectorType;
import net.ltxprogrammer.changed.client.renderer.model.TripleArmedModel;
import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.beast.LatexStiger;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.HumanoidArm;

public class AccessoriesStiger<T extends ChangedEntity> extends AdvancedHumanoidModel<T> implements AdvancedHumanoidModelInterface<T, AccessoriesStiger<T>>, TripleArmedModel {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ChangedAdditionsMod.modResource("accessory_stiger"), "main");
    public final ModelPart Head;
    public final ModelPart Torso;
    public final ModelPart RightArm;
    public final ModelPart RightArm2;
    public final ModelPart RightArm3;
    public final ModelPart LeftArm;
    public final ModelPart LeftArm2;
    public final ModelPart LeftArm3;
    public final ModelPart LeftLeg;
    public final ModelPart RightLeg;
    private final ModelPart Tail;
    public final HumanoidAnimator<T, AccessoriesStiger<T>> animator;

    public AccessoriesStiger(ModelPart root) {
        super(root);
        this.RightLeg = root.getChild("RightLeg");
        this.LeftLeg = root.getChild("LeftLeg");
        this.Head = root.getChild("Head");
        this.Torso = root.getChild("Torso");
        this.RightArm = root.getChild("RightArm");
        this.RightArm2 = root.getChild("RightArm2");
        this.RightArm3 = root.getChild("RightArm3");
        this.LeftArm = root.getChild("LeftArm");
        this.LeftArm2 = root.getChild("LeftArm2");
        this.LeftArm3 = root.getChild("LeftArm3");
        this.Tail = this.Torso.getChild("Tail");
        ModelPart tailPrimary = this.Tail.getChild("TailPrimary");
        ModelPart tailSecondary = tailPrimary.getChild("TailSecondary");
        ModelPart leftLowerLeg = this.LeftLeg.getChild("LeftLowerLeg");
        ModelPart leftFoot = leftLowerLeg.getChild("LeftFoot");
        ModelPart rightLowerLeg = this.RightLeg.getChild("RightLowerLeg");
        ModelPart rightFoot = rightLowerLeg.getChild("RightFoot");
        this.animator = HumanoidAnimator.of(this).hipOffset(-1.5F).addPreset(AnimatorPresets.stigerLike(this.Head, this.Head.getChild("LeftEar"), this.Head.getChild("RightEar"), this.Torso, this.LeftArm3, this.RightArm3, this.LeftArm2, this.RightArm2, this.LeftArm, this.RightArm, this.Tail, List.of(tailPrimary, tailSecondary), this.LeftLeg, leftLowerLeg, leftFoot, leftFoot.getChild("LeftPad"), this.RightLeg, rightLowerLeg, rightFoot, rightFoot.getChild("RightPad")));
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create(), PartPose.offset(-2.5F, 10.5F, 0.0F));

        PartDefinition RightThigh_r1 = RightLeg.addOrReplaceChild("RightThigh_r1", CubeListBuilder.create().texOffs(12, 60).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition RightLowerLeg = RightLeg.addOrReplaceChild("RightLowerLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 6.375F, -3.45F));

        PartDefinition RightCalf_r1 = RightLowerLeg.addOrReplaceChild("RightCalf_r1", CubeListBuilder.create().texOffs(44, 61).addBox(-1.99F, -0.125F, -2.9F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, -2.125F, 1.95F, 0.8727F, 0.0F, 0.0F));

        PartDefinition RightFoot = RightLowerLeg.addOrReplaceChild("RightFoot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.8F, 7.175F));

        PartDefinition RightArch_r1 = RightFoot.addOrReplaceChild("RightArch_r1", CubeListBuilder.create().texOffs(0, 68).addBox(-2.0F, -8.45F, -0.725F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.03F)), PartPose.offsetAndRotation(0.0F, 7.075F, -4.975F, -0.3491F, 0.0F, 0.0F));

        PartDefinition RightPad = RightFoot.addOrReplaceChild("RightPad", CubeListBuilder.create().texOffs(59, 11).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.025F)), PartPose.offset(0.0F, 4.325F, -4.425F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create(), PartPose.offset(2.5F, 10.5F, 0.0F));

        PartDefinition LeftThigh_r1 = LeftLeg.addOrReplaceChild("LeftThigh_r1", CubeListBuilder.create().texOffs(52, 50).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition LeftLowerLeg = LeftLeg.addOrReplaceChild("LeftLowerLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 6.375F, -3.45F));

        PartDefinition LeftCalf_r1 = LeftLowerLeg.addOrReplaceChild("LeftCalf_r1", CubeListBuilder.create().texOffs(28, 60).addBox(-2.01F, -0.125F, -2.9F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, -2.125F, 1.95F, 0.8727F, 0.0F, 0.0F));

        PartDefinition LeftFoot = LeftLowerLeg.addOrReplaceChild("LeftFoot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.8F, 7.175F));

        PartDefinition LeftArch_r1 = LeftFoot.addOrReplaceChild("LeftArch_r1", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -8.45F, -0.725F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.03F)), PartPose.offsetAndRotation(0.0F, 7.075F, -4.975F, -0.3491F, 0.0F, 0.0F));

        PartDefinition LeftPad = LeftFoot.addOrReplaceChild("LeftPad", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.025F)), PartPose.offset(0.0F, 4.325F, -4.425F));

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.025F))
                .texOffs(32, 22).addBox(-2.0F, -3.0F, -6.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.025F))
                .texOffs(0, 5).addBox(-1.5F, -1.0F, -5.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.025F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition Nose_r1 = Head.addOrReplaceChild("Nose_r1", CubeListBuilder.create().texOffs(0, 21).addBox(-1.0F, -29.625F, -0.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.5F, 26.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition RightEar = Head.addOrReplaceChild("RightEar", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, -5.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition rightear_r1 = RightEar.addOrReplaceChild("rightear_r1", CubeListBuilder.create().texOffs(65, 31).addBox(5.75F, -34.65F, -1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(2.5F, 30.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition LeftEar = Head.addOrReplaceChild("LeftEar", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -5.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition leftear_r1 = LeftEar.addOrReplaceChild("leftear_r1", CubeListBuilder.create().texOffs(65, 47).addBox(-7.75F, -34.65F, -1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(-2.5F, 30.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition Hair = Head.addOrReplaceChild("Hair", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.225F))
                .texOffs(24, 8).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.325F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition RightAntenna = Head.addOrReplaceChild("RightAntenna", CubeListBuilder.create(), PartPose.offset(-2.1F, -6.8F, -0.8F));

        PartDefinition RightAntennaPivot = RightAntenna.addOrReplaceChild("RightAntennaPivot", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.75F, 1.0F, -0.1745F, 0.0873F, -0.1309F));

        PartDefinition rightupperantenna_r1 = RightAntennaPivot.addOrReplaceChild("rightupperantenna_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -29.25F, -25.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(2.5F, 34.25F, 0.0F, -0.6981F, -0.0873F, 0.0F));

        PartDefinition rightlowerantenna_r1 = RightAntennaPivot.addOrReplaceChild("rightlowerantenna_r1", CubeListBuilder.create().texOffs(0, 16).addBox(-3.5F, -34.0F, -13.6F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(2.5F, 34.25F, 0.0F, -0.3054F, -0.0873F, 0.0F));

        PartDefinition LeftAntenna = Head.addOrReplaceChild("LeftAntenna", CubeListBuilder.create(), PartPose.offset(2.1F, -6.8F, -0.8F));

        PartDefinition LeftAntennaPivot = LeftAntenna.addOrReplaceChild("LeftAntennaPivot", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.75F, 1.0F, -0.1745F, -0.0873F, 0.1309F));

        PartDefinition leftupperantenna_r1 = LeftAntennaPivot.addOrReplaceChild("leftupperantenna_r1", CubeListBuilder.create().texOffs(24, 0).addBox(2.0F, -29.25F, -25.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(-2.5F, 34.25F, 0.0F, -0.6981F, 0.0873F, 0.0F));

        PartDefinition leftlowerantenna_r1 = LeftAntennaPivot.addOrReplaceChild("leftlowerantenna_r1", CubeListBuilder.create().texOffs(0, 0).addBox(1.5F, -34.0F, -13.6F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(-2.5F, 34.25F, 0.0F, -0.3054F, 0.0873F, 0.0F));

        PartDefinition Torso = partdefinition.addOrReplaceChild("Torso", CubeListBuilder.create().texOffs(28, 28).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.025F))
                .texOffs(14, 71).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.375F))
                .texOffs(38, 71).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.225F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition Tail = Torso.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(0.0F, 10.5F, 0.0F));

        PartDefinition TailPrimary = Tail.addOrReplaceChild("TailPrimary", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition Base_r1 = TailPrimary.addOrReplaceChild("Base_r1", CubeListBuilder.create().texOffs(60, 61).addBox(-2.0F, 0.75F, -1.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.175F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2654F, 0.0F, 0.0F));

        PartDefinition TailSecondary = TailPrimary.addOrReplaceChild("TailSecondary", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 3.5F));

        PartDefinition Base_r2 = TailSecondary.addOrReplaceChild("Base_r2", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -1.85F, -2.2F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(0.0F, 0.75F, 1.0F, 1.3963F, 0.0F, 0.0F));

        PartDefinition LeftArm3 = partdefinition.addOrReplaceChild("LeftArm3", CubeListBuilder.create().texOffs(52, 34).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.025F))
                .texOffs(68, 38).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.225F)), PartPose.offset(5.0F, 1.5F, 0.0F));

        PartDefinition LeftArm2 = partdefinition.addOrReplaceChild("LeftArm2", CubeListBuilder.create().texOffs(48, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offset(5.0F, 4.5F, 0.0F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(36, 44).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offset(5.0F, 7.5F, 0.0F));

        PartDefinition RightArm3 = partdefinition.addOrReplaceChild("RightArm3", CubeListBuilder.create().texOffs(52, 18).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.025F))
                .texOffs(68, 18).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.225F)), PartPose.offset(-5.0F, 1.5F, 0.0F));

        PartDefinition RightArm2 = partdefinition.addOrReplaceChild("RightArm2", CubeListBuilder.create().texOffs(0, 47).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offset(-5.0F, 4.5F, 0.0F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(20, 44).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.025F)), PartPose.offset(-5.0F, 7.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 96, 96);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.Torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.RightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.RightArm2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.RightArm3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.LeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.LeftArm2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.LeftArm3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void prepareMobModel(T p_102861_, float p_102862_, float p_102863_, float p_102864_) {
        this.prepareMobModel(this.animator, p_102861_, p_102862_, p_102863_, p_102864_);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.animator.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public PoseStack getPlacementCorrectors(CorrectorType type) {
        PoseStack corrector = AdvancedHumanoidModelInterface.super.getPlacementCorrectors(type);
        return corrector;
    }

    public ModelPart getArm(HumanoidArm humanoidArm) {
        ModelPart var10000;
        switch (humanoidArm) {
            case LEFT -> var10000 = this.LeftArm3;
            case RIGHT -> var10000 = this.RightArm3;
            default -> throw new IncompatibleClassChangeError();
        }

        return var10000;
    }

    public ModelPart getMiddleArm(HumanoidArm humanoidArm) {
        ModelPart var10000;
        switch (humanoidArm) {
            case LEFT -> var10000 = this.LeftArm2;
            case RIGHT -> var10000 = this.RightArm2;
            default -> throw new IncompatibleClassChangeError();
        }

        return var10000;
    }

    public ModelPart getOtherArm(HumanoidArm humanoidArm) {
        ModelPart var10000;
        switch (humanoidArm) {
            case LEFT -> var10000 = this.LeftArm;
            case RIGHT -> var10000 = this.RightArm;
            default -> throw new IncompatibleClassChangeError();
        }

        return var10000;
    }

    public void translateToUpperHand(HumanoidArm arm, PoseStack poseStack) {
        this.getArm(arm).translateAndRotate(poseStack);
        poseStack.translate((double) 0.0F, (double) (this.animator.armLength - 12.0F) / (double) 20.0F, (double) 0.0F);
    }

    public void translateToMiddleHand(HumanoidArm arm, PoseStack poseStack) {
        this.getMiddleArm(arm).translateAndRotate(poseStack);
        poseStack.translate((double) 0.0F, (double) (this.animator.armLength - 12.0F) / (double) 20.0F, (double) 0.0F);
    }

    public void translateToLowerHand(HumanoidArm arm, PoseStack poseStack) {
        this.getOtherArm(arm).translateAndRotate(poseStack);
        poseStack.translate((double) 0.0F, (double) (this.animator.armLength - 12.0F) / (double) 20.0F, (double) 0.0F);
    }

    public ModelPart getLeg(HumanoidArm p_102852_) {
        return p_102852_ == HumanoidArm.LEFT ? this.LeftLeg : this.RightLeg;
    }

    public void setupHand() {
        this.animator.setupHand();
    }

    public HumanoidAnimator<T, AccessoriesStiger<T>> getAnimator() {
        return this.animator;
    }

    public ModelPart getHead() {
        return this.Head;
    }

    public ModelPart getTorso() {
        return this.Torso;
    }
}
