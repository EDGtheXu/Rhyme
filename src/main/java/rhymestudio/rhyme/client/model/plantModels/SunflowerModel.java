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
import rhymestudio.rhyme.core.entity.plants.SunFlower;

public class SunflowerModel extends AbstractPlantModel<SunFlower> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Rhyme.space( "sunflower"), "main");
	private final ModelPart all;
	private final ModelPart head;
	private final ModelPart face;
	private final ModelPart normal;
	private final ModelPart eyes_closed;
	private final ModelPart Yezi;
	private final ModelPart gj;
	private final ModelPart Genh;
	private final ModelPart Genh2;

	public SunflowerModel(ModelPart root) {
		this.all = root.getChild("all");
		this.head = this.all.getChild("head");
		this.face = this.head.getChild("face");
		this.normal = this.face.getChild("normal");
		this.eyes_closed = this.face.getChild("eyes_closed");
		this.Yezi = this.all.getChild("Yezi");
		this.gj = this.all.getChild("gj");
		this.Genh = this.gj.getChild("Genh");
		this.Genh2 = this.gj.getChild("Genh2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition head = all.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 9).addBox(-0.8333F, -2.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(8, 29).addBox(-1.8333F, -3.0F, -4.0F, 2.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 43).addBox(1.1667F, -6.0F, -7.0F, 0.0F, 12.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.6667F, -12.2F, 0.0F));

		PartDefinition face = head.addOrReplaceChild("face", CubeListBuilder.create(), PartPose.offset(1.1667F, 3.0F, 0.0F));

		PartDefinition normal = face.addOrReplaceChild("normal", CubeListBuilder.create().texOffs(28, 25).addBox(-1.0F, -7.0F, -5.0F, 2.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition eyes_closed = face.addOrReplaceChild("eyes_closed", CubeListBuilder.create().texOffs(40, 0).addBox(-1.0F, -7.0F, -5.0F, 2.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Yezi = all.addOrReplaceChild("Yezi", CubeListBuilder.create().texOffs(24, 15).addBox(0.0F, -1.0F, -4.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(24, 0).addBox(0.0F, -1.0F, 2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 20).addBox(-6.0F, -1.0F, 2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(12, 24).addBox(-6.0F, -1.0F, -4.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, -1.0F));

		PartDefinition gj = all.addOrReplaceChild("gj", CubeListBuilder.create(), PartPose.offset(-1.0F, -6.0F, 0.0F));

		PartDefinition Genh = gj.addOrReplaceChild("Genh", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition Genh2 = gj.addOrReplaceChild("Genh2", CubeListBuilder.create().texOffs(0, 25).addBox(-0.1F, 4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.4F, -10.6F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}



	@Override
	public ModelPart root() {
		return all;
	}
	@Override
	public ModelPart getHead() {
		return head;
	}


}