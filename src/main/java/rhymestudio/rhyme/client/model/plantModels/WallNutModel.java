package rhymestudio.rhyme.client.model.plantModels;// Made with Blockbench 4.11.2
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

public class WallNutModel extends AbstractPlantModel<AbstractPlant> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Rhyme.space("wall_nut_model"), "main");
	private final ModelPart head;
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart bone3;

	public WallNutModel(ModelPart root) {
		this.head = root.getChild("head");
		this.bone = this.head.getChild("bone");
		this.bone2 = this.head.getChild("bone2");
		this.bone3 = this.head.getChild("bone3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone = head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -16.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(-6.0F, -18.0F, -6.0F, 12.0F, 18.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone2 = head.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(48, 28).addBox(-7.0F, -16.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(48, 56).addBox(-6.0F, -18.0F, -6.0F, 12.0F, 18.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition bone3 = head.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(56, 0).addBox(-7.0F, -16.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 58).addBox(-6.0F, -18.0F, -6.0F, 12.0F, 18.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public ModelPart root() {
		return head;
	}
	@Override
	public ModelPart getHead() {
		return null;
	}
}