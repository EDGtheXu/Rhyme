package rhymestudio.rhyme.client.model.plantModels;// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.model.AbstractPlantModel;
import rhymestudio.rhyme.core.entity.AbstractPlant;

public class SnowPeaModel extends AbstractPlantModel<AbstractPlant> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Rhyme.space( "snow_pea_model"), "main");
	private final ModelPart bone3;
	private final ModelPart Yezi;
	private final ModelPart Genh;
	private final ModelPart head;
	private final ModelPart bone2;
	private final ModelPart bone;
	private final ModelPart eyeclosed;
	private final ModelPart bone6;

	public SnowPeaModel(ModelPart root) {
		this.bone3 = root.getChild("bone3");
		this.Yezi = this.bone3.getChild("Yezi");
		this.Genh = this.bone3.getChild("Genh");
		this.head = this.bone3.getChild("head");
		this.bone2 = this.head.getChild("bone2");
		this.bone = this.head.getChild("bone");
		this.eyeclosed = this.head.getChild("eyeclosed");
		this.bone6 = this.bone3.getChild("bone6");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition Yezi = bone3.addOrReplaceChild("Yezi", CubeListBuilder.create().texOffs(24, 15).addBox(0.0F, -1.0F, -4.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(24, 0).addBox(0.0F, -1.0F, 2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(-6.0F, -1.0F, 2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(12, 24).addBox(-6.0F, -1.0F, -4.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, -1.0F));

		PartDefinition Genh = bone3.addOrReplaceChild("Genh", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition head = bone3.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.5F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.5F, 0.0F));

		PartDefinition bone2 = head.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(-2.2F, -0.7F, 2.4F));

		PartDefinition cube_r1 = bone2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 40).addBox(-6.0F, -2.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, 2.6F, -4.5F, 0.0F, -0.3054F, 0.2618F));

		PartDefinition cube_r2 = bone2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 40).addBox(-6.0F, -2.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, 0.0F, -4.5F, 0.0F, -0.3054F, 0.2618F));

		PartDefinition cube_r3 = bone2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 40).addBox(-6.0F, -2.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.0F, -2.2F, 0.0F, 0.0F, 0.2618F));

		PartDefinition cube_r4 = bone2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 40).addBox(-6.0F, -2.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, 2.8F, -1.8F, 0.0F, 0.1396F, 0.2618F));

		PartDefinition cube_r5 = bone2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 40).addBox(-6.0F, -2.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 3.1F, -3.1F, 0.0F, -0.1396F, 0.2618F));

		PartDefinition cube_r6 = bone2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 40).addBox(-6.0F, -2.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, 4.0F, 0.0F, -0.0804F, 0.2494F, -0.0537F));

		PartDefinition cube_r7 = bone2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 40).addBox(-6.0F, -2.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, 4.1F, -4.8F, -0.082F, -0.316F, -0.0083F));

		PartDefinition cube_r8 = bone2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 40).addBox(-6.0F, -2.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, 4.0F, -2.4F, 0.0093F, 0.0319F, -0.0363F));

		PartDefinition cube_r9 = bone2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 40).addBox(-6.0F, -2.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.6F, -0.5F, 0.0F, 0.4189F, 0.2618F));

		PartDefinition cube_r10 = bone2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 40).addBox(-6.0F, -2.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.2618F));

		PartDefinition bone = head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(14, 16).addBox(5.0F, -13.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(18, 16).addBox(4.0F, -12.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.5F, 0.0F));

		PartDefinition eyeclosed = head.addOrReplaceChild("eyeclosed", CubeListBuilder.create().texOffs(28, 20).addBox(-23.0F, -16.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(19.0F, 12.5F, 0.0F));

		PartDefinition bone6 = bone3.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(0, 28).addBox(15.8F, 13.7F, -1.3F, 2.5F, 2.6F, 2.6F, new CubeDeformation(0.0F)), PartPose.offset(-17.0F, -8.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public ModelPart root() {
		return bone3;
	}

	@Override
	public ModelPart getHead() {
		return head;
	}
}