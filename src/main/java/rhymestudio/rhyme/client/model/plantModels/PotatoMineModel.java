package rhymestudio.rhyme.client.model.plantModels;// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.model.AbstractPlantModel;
import rhymestudio.rhyme.core.entity.plants.PotatoMine;

public class PotatoMineModel extends AbstractPlantModel<PotatoMine> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Rhyme.space( "potato_mine_model"), "main");
	private final ModelPart all;
	private final ModelPart body;
	private final ModelPart red;
	private final ModelPart grey;
	private final ModelPart doro_on;
	private final ModelPart doro_under;
	private final ModelPart root;

	public PotatoMineModel(ModelPart root) {
		this.root = root;
		this.all = root.getChild("all");
		this.body = this.all.getChild("body");
		this.red = this.body.getChild("red");
		this.grey = this.body.getChild("grey");
		this.doro_on = this.all.getChild("doro_on");
		this.doro_under = this.all.getChild("doro_under");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, 0.0F));

		PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(10, 15).addBox(-0.7F, -1.0F, -0.7F, 1.4F, 6.0F, 1.4F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, 2.5F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition red = body.addOrReplaceChild("red", CubeListBuilder.create().texOffs(8, 24).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition grey = body.addOrReplaceChild("grey", CubeListBuilder.create().texOffs(32, 11).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition doro_on = all.addOrReplaceChild("doro_on", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.7F, 2.0F, -1.3F));

		PartDefinition cube_r1 = doro_on.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 27).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.6F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r2 = doro_on.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 27).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3F, 0.0F, 2.6F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r3 = doro_on.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(30, 17).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7F, 0.0F, 2.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition doro_under = all.addOrReplaceChild("doro_under", CubeListBuilder.create().texOffs(0, 30).addBox(-1.0F, 4.2F, 4.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 23).addBox(2.6F, 3.3F, -4.9F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(30, 23).addBox(-5.0F, 3.5F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 23).addBox(-5.0F, 3.1F, -4.9F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(14, 20).addBox(-4.7F, 4.2F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(3.6F, 4.1F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(-4.7F, 4.2F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 19).addBox(-3.0F, 3.8F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(22, 15).addBox(3.0F, 3.2F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.7F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r4 = doro_under.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(30, 21).addBox(-3.0F, -1.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 5.2F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r5 = doro_under.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(30, 15).addBox(-3.0F, -1.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 5.2F, 2.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r6 = doro_under.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 15).addBox(-3.0F, -1.0F, 0.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1F, 4.8F, -4.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r7 = doro_under.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(16, 24).addBox(-3.0F, -1.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 4.4F, -4.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r8 = doro_under.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 4.4F, -4.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r9 = doro_under.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.1F, 4.4F, 5.7F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r10 = doro_under.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(28, 31).addBox(-3.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4F, 3.6F, 4.8F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r11 = doro_under.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(16, 31).addBox(-3.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 3.4F, -3.6F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r12 = doro_under.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(22, 31).addBox(-3.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.9F, 4.4F, 1.7F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r13 = doro_under.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(10, 30).addBox(-3.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.1F, 4.4F, 5.4F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r14 = doro_under.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(8, 22).addBox(-3.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.2F, 3.5F, -1.3F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r15 = doro_under.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(8, 28).addBox(-3.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.2F, 4.4F, 4.7F, 0.0F, -1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	float ageInTicksO = 0;
	@Override
	public void setupAnim(@NotNull PotatoMine entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		String pose = entity.getCafeAnimState().curName;
		if(pose.equals("up" )&& entity.tickCount - entity.getCafeAnimState().tick== 30 && ageInTicksO + 1f > ageInTicks){
			return;
		}
		ageInTicksO = ageInTicks;
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	@Override
	public ModelPart root() {
		return root;
	}
	@Override
	public ModelPart getHead() {
		return null;
	}
}