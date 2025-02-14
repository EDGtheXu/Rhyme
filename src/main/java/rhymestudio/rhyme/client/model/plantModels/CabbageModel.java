package rhymestudio.rhyme.client.model.plantModels;// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.model.AbstractPlantModel;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.entity.plants.Pea;

public class CabbageModel extends AbstractPlantModel<Pea> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Rhyme.space( "cabbage_model"), "main");
	private final ModelPart head;
	private final ModelPart bone2;
	private final ModelPart closed;
	private final ModelPart bone;
	private final ModelPart bone5;
	private final ModelPart bone3;
	private final ModelPart bone4;

	public CabbageModel(ModelPart root) {
		this.head = root.getChild("head");
		this.bone2 = this.head.getChild("bone2");
		this.closed = this.bone2.getChild("closed");
		this.bone = this.head.getChild("bone");
		this.bone5 = this.head.getChild("bone5");
		this.bone3 = this.bone5.getChild("bone3");
		this.bone4 = this.bone5.getChild("bone4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bone2 = head.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 48).addBox(-4.0F, -3.8333F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(-5.0F, -4.8333F, -5.0F, 10.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.1667F, 0.0F));

		PartDefinition closed = bone2.addOrReplaceChild("closed", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.1667F, 0.0F));

		PartDefinition bone = head.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -0.5F, -6.0F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.49F, 0.0F));

		PartDefinition bone5 = head.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone3 = bone5.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, -11.5408F, 8.6207F));

		PartDefinition cube_r1 = bone3.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 32).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.4592F, 0.5793F, -0.0873F, 0.0F, 0.0F));

		PartDefinition cube_r2 = bone3.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(40, 13).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.5408F, -4.4207F, 0.9599F, 0.0F, 0.0F));

		PartDefinition bone4 = bone5.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.0F, -17.2905F, 11.4179F));

		PartDefinition cube_r3 = bone4.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 42).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.4905F, -0.2179F, -0.0873F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	@Override
	public ModelPart getHead() {
		return null;
	}

	@Override
	public ModelPart root() {
		return head;
	}
}