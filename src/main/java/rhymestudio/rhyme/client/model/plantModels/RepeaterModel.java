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


public class RepeaterModel extends AbstractPlantModel<AbstractPlant> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Rhyme.space( "repeater"), "main");
	private final ModelPart Yezi;
	private final ModelPart Genh;
	private final ModelPart head;
	private final ModelPart bone3;
	private final ModelPart bone;
	private final ModelPart eyeclosed;
	private final ModelPart bone6;
	private final ModelPart bone2;
	private final ModelPart root;

	public RepeaterModel(ModelPart root) {
		this.root = root;
		this.Yezi = root.getChild("Yezi");
		this.Genh = root.getChild("Genh");
		this.head = root.getChild("head");
		this.bone3 = this.head.getChild("bone3");
		this.bone = this.head.getChild("bone");
		this.eyeclosed = this.head.getChild("eyeclosed");
		this.bone6 = root.getChild("bone6");
		this.bone2 = root.getChild("bone2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Yezi = partdefinition.addOrReplaceChild("Yezi", CubeListBuilder.create().texOffs(24, 15).addBox(0.0F, -1.0F, -4.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(24, 0).addBox(0.0F, -1.0F, 2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 20).addBox(-6.0F, -1.0F, 2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(12, 24).addBox(-6.0F, -1.0F, -4.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 24.0F, -1.0F));

		PartDefinition Genh = partdefinition.addOrReplaceChild("Genh", CubeListBuilder.create().texOffs(0, 25).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(35, 3).addBox(-4.25F, -3.25F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.25F, -3.25F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.75F, 11.25F, 0.0F));

		PartDefinition bone3 = head.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(40, 12).addBox(9.3F, -4.8F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.25F, 1.75F, 0.0F));

		PartDefinition bone = head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(14, 16).addBox(5.0F, -13.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(16, 18).addBox(4.0F, -12.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.75F, 12.75F, 0.0F));

		PartDefinition eyeclosed = head.addOrReplaceChild("eyeclosed", CubeListBuilder.create().texOffs(20, 21).addBox(-23.0F, -16.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(21.75F, 12.75F, 0.0F));

		PartDefinition bone6 = partdefinition.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(0, 25).addBox(20.8F, 13.7F, -1.3F, 2.5F, 2.6F, 2.6F, new CubeDeformation(0.0F)), PartPose.offset(-22.0F, 16.0F, 0.0F));

		PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 30).addBox(15.8F, 13.7F, -1.3F, 2.5F, 2.6F, 2.6F, new CubeDeformation(0.0F)), PartPose.offset(-17.0F, 16.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		root().render(poseStack, vertexConsumer, packedLight, packedOverlay, color);

	}

	@Override
	public ModelPart root() {
		return root;
	}
	@Override
	public ModelPart getHead() {
		return head;
	}
}