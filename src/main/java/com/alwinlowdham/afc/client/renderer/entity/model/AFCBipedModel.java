package com.alwinlowdham.afc.client.renderer.entity.model;

import com.alwinlowdham.afc.util.combat.hitlocs.BipedHitLoc;
import com.alwinlowdham.afc.util.combat.hitlocs.interfaces.IHitLoc;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.Map;

// If needed, write a Python script (or a few scripts) to generate the parts code for me.
// That way when I make a new model for an entity all I need to do manually is map parts to hit locations.

// Make a new class called AFCBiped and have AFCHumanoid extend AFCBiped

// DONE
// CLEANED
public class AFCBipedModel<T extends LivingEntity> extends AFCModel<T> {

    private static final float PROPORTION = 0.6096F;
    private final ModelRenderer pelvis;
    private final ModelRenderer groin;
    private final ModelRenderer rightHip;
    private final ModelRenderer leftHip;
    private final ModelRenderer rightButtock;
    private final ModelRenderer leftButtock;
    private final ModelRenderer lowerAbdomen;
    private final ModelRenderer belly;
    private final ModelRenderer lowerBack;
    private final ModelRenderer upperAbdomen;
    private final ModelRenderer belly2;
    private final ModelRenderer lowerBack2;
    private final ModelRenderer lowerThorax;
    private final ModelRenderer chest;
    private final ModelRenderer upperBack;
    private final ModelRenderer upperThorax;
    private final ModelRenderer chest2;
    private final ModelRenderer rightShoulder;
    private final ModelRenderer leftShoulder;
    private final ModelRenderer upperBack2;
    private final ModelRenderer neck;
    private final ModelRenderer throat;
    private final ModelRenderer nape;
    private final ModelRenderer head;
    private final ModelRenderer lowerHead;
    private final ModelRenderer nape2;
    private final ModelRenderer face;
    private final ModelRenderer upperHead;
    private final ModelRenderer upperHead2;
    private final ModelRenderer rightUpperArm;
    private final ModelRenderer rightShoulder2;
    private final ModelRenderer rightArm;
    private final ModelRenderer rightLowerArm;
    private final ModelRenderer rightElbow;
    private final ModelRenderer rightForearm;
    private final ModelRenderer rightHand;
    private final ModelRenderer leftUpperArm;
    private final ModelRenderer leftShoulder2;
    private final ModelRenderer leftArm;
    private final ModelRenderer leftLowerArm;
    private final ModelRenderer leftElbow;
    private final ModelRenderer leftForearm;
    private final ModelRenderer leftHand;
    private final ModelRenderer rightUpperLeg;
    private final ModelRenderer rightHip2;
    private final ModelRenderer rightThigh;
    private final ModelRenderer rightHamstring;
    private final ModelRenderer rightLowerLeg;
    private final ModelRenderer rightKnee;
    private final ModelRenderer rightShin;
    private final ModelRenderer rightCalf;
    private final ModelRenderer rightFoot;
    private final ModelRenderer leftUpperLeg;
    private final ModelRenderer leftHip2;
    private final ModelRenderer leftThigh;
    private final ModelRenderer leftHamstring;
    private final ModelRenderer leftLowerLeg;
    private final ModelRenderer leftKnee;
    private final ModelRenderer leftShin;
    private final ModelRenderer leftCalf;
    private final ModelRenderer leftFoot;
    private final Map<ModelRenderer, ModelRenderer> immediateParentMap = Maps.newHashMap();
    private final Map<ModelRenderer, ObjectList<ModelRenderer>> allParentsMap = Maps.newHashMap();
    private final Map<ModelRenderer, IHitLoc> hitLocMap = Maps.newHashMap();

    public AFCBipedModel() {
        texWidth = 128;
        texHeight = 128;

        pelvis = new ModelRenderer(this);
        pelvis.setPos(0.0F, 0.0F, 0.0F);


        groin = new ModelRenderer(this);
        groin.setPos(0.0F, 0.0F, 0.0F);
        pelvis.addChild(groin);
        groin.texOffs(0, 62).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 3.0F, 3.0F, 0.0F, false);

        rightHip = new ModelRenderer(this);
        rightHip.setPos(0.0F, 0.0F, 0.0F);
        pelvis.addChild(rightHip);
        rightHip.texOffs(60, 0).addBox(-4.0F, -2.0F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);

        leftHip = new ModelRenderer(this);
        leftHip.setPos(0.0F, 0.0F, 0.0F);
        pelvis.addChild(leftHip);
        leftHip.texOffs(12, 41).addBox(1.0F, -2.0F, -3.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);

        rightButtock = new ModelRenderer(this);
        rightButtock.setPos(0.0F, 0.0F, 0.0F);
        pelvis.addChild(rightButtock);
        rightButtock.texOffs(45, 47).addBox(-4.0F, -2.0F, 0.0F, 4.0F, 5.0F, 3.0F, 0.0F, false);

        leftButtock = new ModelRenderer(this);
        leftButtock.setPos(0.0F, 0.0F, 0.0F);
        pelvis.addChild(leftButtock);
        leftButtock.texOffs(12, 47).addBox(0.0F, -2.0F, 0.0F, 4.0F, 5.0F, 3.0F, 0.0F, false);

        lowerAbdomen = new ModelRenderer(this);
        lowerAbdomen.setPos(0.0F, -2.0F, -3.0F);
        pelvis.addChild(lowerAbdomen);


        belly = new ModelRenderer(this);
        belly.setPos(0.0F, 0.0F, 0.0F);
        lowerAbdomen.addChild(belly);
        belly.texOffs(0, 18).addBox(-4.0F, -3.0F, -0.5F, 8.0F, 3.0F, 5.0F, 0.0F, false);

        lowerBack = new ModelRenderer(this);
        lowerBack.setPos(0.0F, 0.0F, 0.0F);
        lowerAbdomen.addChild(lowerBack);
        lowerBack.texOffs(48, 38).addBox(-4.0F, -3.0F, 4.5F, 8.0F, 3.0F, 1.0F, 0.0F, false);

        upperAbdomen = new ModelRenderer(this);
        upperAbdomen.setPos(0.0F, -3.0F, 5.5F);
        lowerAbdomen.addChild(upperAbdomen);


        belly2 = new ModelRenderer(this);
        belly2.setPos(0.0F, 0.0F, 0.0F);
        upperAbdomen.addChild(belly2);
        belly2.texOffs(0, 10).addBox(-4.0F, -3.0F, -6.0F, 8.0F, 3.0F, 5.0F, 0.0F, false);

        lowerBack2 = new ModelRenderer(this);
        lowerBack2.setPos(0.0F, 0.0F, 0.0F);
        upperAbdomen.addChild(lowerBack2);
        lowerBack2.texOffs(46, 13).addBox(-4.0F, -3.0F, -1.0F, 8.0F, 3.0F, 1.0F, 0.0F, false);

        lowerThorax = new ModelRenderer(this);
        lowerThorax.setPos(0.0F, -3.0F, -6.0F);
        upperAbdomen.addChild(lowerThorax);


        chest = new ModelRenderer(this);
        chest.setPos(0.0F, 0.0F, 0.0F);
        lowerThorax.addChild(chest);
        chest.texOffs(0, 0).addBox(-4.0F, -4.0F, -0.5F, 8.0F, 4.0F, 6.0F, 0.0F, false);

        upperBack = new ModelRenderer(this);
        upperBack.setPos(0.0F, 0.0F, 0.0F);
        lowerThorax.addChild(upperBack);
        upperBack.texOffs(45, 31).addBox(-4.0F, -4.0F, 5.5F, 8.0F, 4.0F, 1.0F, 0.0F, false);

        upperThorax = new ModelRenderer(this);
        upperThorax.setPos(0.0F, -4.0F, 6.5F);
        lowerThorax.addChild(upperThorax);


        chest2 = new ModelRenderer(this);
        chest2.setPos(0.0F, 0.0F, 0.0F);
        upperThorax.addChild(chest2);
        chest2.texOffs(22, 0).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 4.0F, 2.0F, 0.0F, false);

        rightShoulder = new ModelRenderer(this);
        rightShoulder.setPos(0.0F, 0.0F, 0.0F);
        upperThorax.addChild(rightShoulder);
        rightShoulder.texOffs(46, 55).addBox(-4.0F, -4.0F, -4.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);

        leftShoulder = new ModelRenderer(this);
        leftShoulder.setPos(0.0F, 0.0F, 0.0F);
        upperThorax.addChild(leftShoulder);
        leftShoulder.texOffs(49, 17).addBox(0.0F, -4.0F, -4.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);

        upperBack2 = new ModelRenderer(this);
        upperBack2.setPos(0.0F, 0.0F, 0.0F);
        upperThorax.addChild(upperBack2);
        upperBack2.texOffs(45, 6).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 4.0F, 1.0F, 0.0F, false);

        neck = new ModelRenderer(this);
        neck.setPos(0.0F, -4.0F, -4.0F);
        upperThorax.addChild(neck);


        throat = new ModelRenderer(this);
        throat.setPos(0.0F, 0.0F, 0.0F);
        neck.addChild(throat);
        throat.texOffs(62, 28).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);

        nape = new ModelRenderer(this);
        nape.setPos(0.0F, 0.0F, 0.0F);
        neck.addChild(nape);
        nape.texOffs(33, 13).addBox(-1.5F, -2.0F, 2.0F, 3.0F, 2.0F, 1.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setPos(0.0F, -2.0F, 0.0F);
        neck.addChild(head);


        lowerHead = new ModelRenderer(this);
        lowerHead.setPos(0.0F, 0.0F, 0.0F);
        head.addChild(lowerHead);
        lowerHead.texOffs(25, 7).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 2.0F, 3.0F, 0.0F, false);

        nape2 = new ModelRenderer(this);
        nape2.setPos(0.0F, 0.0F, 0.0F);
        head.addChild(nape2);
        nape2.texOffs(48, 42).addBox(-1.5F, -2.0F, 1.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);

        face = new ModelRenderer(this);
        face.setPos(0.0F, 0.0F, 0.0F);
        head.addChild(face);
        face.texOffs(20, 61).addBox(-2.0F, -5.0F, -2.0F, 4.0F, 3.0F, 2.0F, 0.0F, false);

        upperHead = new ModelRenderer(this);
        upperHead.setPos(0.0F, 0.0F, 0.0F);
        head.addChild(upperHead);
        upperHead.texOffs(45, 24).addBox(-2.0F, -5.0F, 0.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);

        upperHead2 = new ModelRenderer(this);
        upperHead2.setPos(0.0F, 0.0F, 0.0F);
        head.addChild(upperHead2);
        upperHead2.texOffs(42, 0).addBox(-2.0F, -6.0F, -1.0F, 4.0F, 1.0F, 5.0F, 0.0F, false);

        rightUpperArm = new ModelRenderer(this);
        rightUpperArm.setPos(-5.5F, -2.0F, -2.5F);
        upperThorax.addChild(rightUpperArm);


        rightShoulder2 = new ModelRenderer(this);
        rightShoulder2.setPos(0.0F, 0.0F, 0.0F);
        rightUpperArm.addChild(rightShoulder2);
        rightShoulder2.texOffs(61, 14).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);

        rightArm = new ModelRenderer(this);
        rightArm.setPos(0.0F, 0.0F, 0.0F);
        rightUpperArm.addChild(rightArm);
        rightArm.texOffs(0, 39).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 9.0F, 3.0F, 0.0F, false);

        rightLowerArm = new ModelRenderer(this);
        rightLowerArm.setPos(0.0F, 8.0F, 1.5F);
        rightUpperArm.addChild(rightLowerArm);


        rightElbow = new ModelRenderer(this);
        rightElbow.setPos(0.0F, 0.0F, 0.0F);
        rightLowerArm.addChild(rightElbow);
        rightElbow.texOffs(60, 33).addBox(-1.5F, 0.0F, -3.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);

        rightForearm = new ModelRenderer(this);
        rightForearm.setPos(0.0F, 0.0F, 0.0F);
        rightLowerArm.addChild(rightForearm);
        rightForearm.texOffs(36, 38).addBox(-1.5F, 1.0F, -3.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);

        rightHand = new ModelRenderer(this);
        rightHand.setPos(0.0F, 0.0F, 0.0F);
        rightLowerArm.addChild(rightHand);
        rightHand.texOffs(43, 62).addBox(-0.5F, 10.0F, -3.0F, 1.0F, 4.0F, 3.0F, 0.0F, false);

        leftUpperArm = new ModelRenderer(this);
        leftUpperArm.setPos(5.5F, -2.0F, -2.5F);
        upperThorax.addChild(leftUpperArm);


        leftShoulder2 = new ModelRenderer(this);
        leftShoulder2.setPos(0.0F, 0.0F, 0.0F);
        leftUpperArm.addChild(leftShoulder2);
        leftShoulder2.texOffs(60, 8).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);

        leftArm = new ModelRenderer(this);
        leftArm.setPos(0.0F, 0.0F, 0.0F);
        leftUpperArm.addChild(leftArm);
        leftArm.texOffs(24, 38).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 9.0F, 3.0F, 0.0F, false);

        leftLowerArm = new ModelRenderer(this);
        leftLowerArm.setPos(0.0F, 8.0F, 1.5F);
        leftUpperArm.addChild(leftLowerArm);


        leftElbow = new ModelRenderer(this);
        leftElbow.setPos(0.0F, 0.0F, 0.0F);
        leftLowerArm.addChild(leftElbow);
        leftElbow.texOffs(18, 12).addBox(-1.5F, 1.0F, -3.0F, 3.0F, -1.0F, 3.0F, 0.0F, false);

        leftForearm = new ModelRenderer(this);
        leftForearm.setPos(0.0F, 0.0F, 0.0F);
        leftLowerArm.addChild(leftForearm);
        leftForearm.texOffs(37, 16).addBox(-1.5F, 1.0F, -3.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);

        leftHand = new ModelRenderer(this);
        leftHand.setPos(0.0F, 0.0F, 0.0F);
        leftLowerArm.addChild(leftHand);
        leftHand.texOffs(14, 26).addBox(-0.5F, 10.0F, -3.0F, 1.0F, 4.0F, 3.0F, 0.0F, false);

        rightUpperLeg = new ModelRenderer(this);
        rightUpperLeg.setPos(-2.0F, 2.0F, -1.0F);
        pelvis.addChild(rightUpperLeg);


        rightHip2 = new ModelRenderer(this);
        rightHip2.setPos(0.0F, 0.0F, 0.0F);
        rightUpperLeg.addChild(rightHip2);
        rightHip2.texOffs(9, 34).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 5.0F, 0.0F, false);

        rightThigh = new ModelRenderer(this);
        rightThigh.setPos(0.0F, 0.0F, 0.0F);
        rightUpperLeg.addChild(rightThigh);
        rightThigh.texOffs(0, 26).addBox(-2.0F, 1.0F, -1.0F, 4.0F, 10.0F, 3.0F, 0.0F, false);

        rightHamstring = new ModelRenderer(this);
        rightHamstring.setPos(0.0F, 0.0F, 0.0F);
        rightUpperLeg.addChild(rightHamstring);
        rightHamstring.texOffs(10, 55).addBox(-2.0F, 1.0F, 2.0F, 4.0F, 10.0F, 1.0F, 0.0F, false);

        rightLowerLeg = new ModelRenderer(this);
        rightLowerLeg.setPos(0.5F, 11.0F, 0.0F);
        rightUpperLeg.addChild(rightLowerLeg);


        rightKnee = new ModelRenderer(this);
        rightKnee.setPos(0.0F, 0.0F, 0.0F);
        rightLowerLeg.addChild(rightKnee);
        rightKnee.texOffs(57, 24).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);

        rightShin = new ModelRenderer(this);
        rightShin.setPos(0.0F, 0.0F, 0.0F);
        rightLowerLeg.addChild(rightShin);
        rightShin.texOffs(36, 53).addBox(-1.5F, 1.0F, 0.0F, 3.0F, 9.0F, 2.0F, 0.0F, false);

        rightCalf = new ModelRenderer(this);
        rightCalf.setPos(0.0F, 0.0F, 0.0F);
        rightLowerLeg.addChild(rightCalf);
        rightCalf.texOffs(60, 52).addBox(-1.5F, 1.0F, 2.0F, 3.0F, 9.0F, 1.0F, 0.0F, false);

        rightFoot = new ModelRenderer(this);
        rightFoot.setPos(0.0F, 0.0F, 0.0F);
        rightLowerLeg.addChild(rightFoot);
        rightFoot.texOffs(33, 6).addBox(-1.5F, 10.0F, -3.0F, 3.0F, 1.0F, 6.0F, 0.0F, false);

        leftUpperLeg = new ModelRenderer(this);
        leftUpperLeg.setPos(2.0F, 2.0F, -1.0F);
        pelvis.addChild(leftUpperLeg);


        leftHip2 = new ModelRenderer(this);
        leftHip2.setPos(0.0F, 0.0F, 0.0F);
        leftUpperLeg.addChild(leftHip2);
        leftHip2.texOffs(32, 31).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 5.0F, 0.0F, false);

        leftThigh = new ModelRenderer(this);
        leftThigh.setPos(0.0F, 0.0F, 0.0F);
        leftUpperLeg.addChild(leftThigh);
        leftThigh.texOffs(23, 23).addBox(-2.0F, 1.0F, -1.0F, 4.0F, 10.0F, 3.0F, 0.0F, false);

        leftHamstring = new ModelRenderer(this);
        leftHamstring.setPos(0.0F, 0.0F, 0.0F);
        leftUpperLeg.addChild(leftHamstring);
        leftHamstring.texOffs(0, 51).addBox(-2.0F, 1.0F, 2.0F, 4.0F, 10.0F, 1.0F, 0.0F, false);

        leftLowerLeg = new ModelRenderer(this);
        leftLowerLeg.setPos(-0.5F, 11.0F, 0.0F);
        leftUpperLeg.addChild(leftLowerLeg);


        leftKnee = new ModelRenderer(this);
        leftKnee.setPos(0.0F, 0.0F, 0.0F);
        leftLowerLeg.addChild(leftKnee);
        leftKnee.texOffs(21, 19).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);

        leftShin = new ModelRenderer(this);
        leftShin.setPos(0.0F, 0.0F, 0.0F);
        leftLowerLeg.addChild(leftShin);
        leftShin.texOffs(26, 50).addBox(-1.5F, 1.0F, 0.0F, 3.0F, 9.0F, 2.0F, 0.0F, false);

        leftCalf = new ModelRenderer(this);
        leftCalf.setPos(0.0F, 0.0F, 0.0F);
        leftLowerLeg.addChild(leftCalf);
        leftCalf.texOffs(59, 42).addBox(-1.5F, 1.0F, 2.0F, 3.0F, 9.0F, 1.0F, 0.0F, false);

        leftFoot = new ModelRenderer(this);
        leftFoot.setPos(0.0F, 0.0F, 0.0F);
        leftLowerLeg.addChild(leftFoot);
        leftFoot.texOffs(21, 12).addBox(-1.5F, 10.0F, -3.0F, 3.0F, 1.0F, 6.0F, 0.0F, false);

        addChildrenRecursively(pelvis);
        setUpAllParentsMap();

        hitLocMap.put(upperHead, BipedHitLoc.UPPER_HEAD);
        hitLocMap.put(upperHead2, BipedHitLoc.UPPER_HEAD);
        hitLocMap.put(face, BipedHitLoc.FACE);
        hitLocMap.put(lowerHead, BipedHitLoc.LOWER_HEAD);

        hitLocMap.put(throat, BipedHitLoc.THROAT);
        hitLocMap.put(nape, BipedHitLoc.UPPER_BACK);
        hitLocMap.put(nape2, BipedHitLoc.UPPER_BACK);

        hitLocMap.put(chest, BipedHitLoc.CHEST);
        hitLocMap.put(chest2, BipedHitLoc.CHEST);
        hitLocMap.put(upperBack, BipedHitLoc.UPPER_BACK);
        hitLocMap.put(upperBack2, BipedHitLoc.UPPER_BACK);

        hitLocMap.put(rightShoulder, BipedHitLoc.R_SHOULDER);
        hitLocMap.put(rightShoulder2, BipedHitLoc.R_SHOULDER);
        hitLocMap.put(rightArm, BipedHitLoc.R_ARM);
        hitLocMap.put(rightElbow, BipedHitLoc.R_ELBOW);
        hitLocMap.put(rightForearm, BipedHitLoc.R_FOREARM);
        hitLocMap.put(rightHand, BipedHitLoc.R_HAND);

        hitLocMap.put(leftShoulder, BipedHitLoc.L_SHOULDER);
        hitLocMap.put(leftShoulder2, BipedHitLoc.L_SHOULDER);
        hitLocMap.put(leftArm, BipedHitLoc.L_ARM);
        hitLocMap.put(leftElbow, BipedHitLoc.L_ELBOW);
        hitLocMap.put(leftForearm, BipedHitLoc.L_FOREARM);
        hitLocMap.put(leftHand, BipedHitLoc.L_HAND);

        hitLocMap.put(belly, BipedHitLoc.BELLY);
        hitLocMap.put(belly2, BipedHitLoc.BELLY);
        hitLocMap.put(lowerBack, BipedHitLoc.LOWER_BACK);
        hitLocMap.put(lowerBack2, BipedHitLoc.LOWER_BACK);

        hitLocMap.put(groin, BipedHitLoc.GROIN);
        hitLocMap.put(rightButtock, BipedHitLoc.LOWER_BACK);
        hitLocMap.put(leftButtock, BipedHitLoc.LOWER_BACK);

        hitLocMap.put(rightHip, BipedHitLoc.R_HIP);
        hitLocMap.put(rightHip2, BipedHitLoc.R_HIP);
        hitLocMap.put(rightThigh, BipedHitLoc.R_THIGH);
        hitLocMap.put(rightHamstring, BipedHitLoc.R_THIGH);
        hitLocMap.put(rightKnee, BipedHitLoc.R_KNEE);
        hitLocMap.put(rightShin, BipedHitLoc.R_SHIN);
        hitLocMap.put(rightCalf, BipedHitLoc.R_SHIN);
        hitLocMap.put(rightFoot, BipedHitLoc.R_FOOT);

        hitLocMap.put(leftHip, BipedHitLoc.L_HIP);
        hitLocMap.put(leftHip2, BipedHitLoc.L_HIP);
        hitLocMap.put(leftThigh, BipedHitLoc.L_THIGH);
        hitLocMap.put(leftHamstring, BipedHitLoc.L_THIGH);
        hitLocMap.put(leftKnee, BipedHitLoc.L_KNEE);
        hitLocMap.put(leftShin, BipedHitLoc.L_SHIN);
        hitLocMap.put(leftCalf, BipedHitLoc.L_SHIN);
        hitLocMap.put(leftFoot, BipedHitLoc.L_FOOT);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // previously the render function, render code was moved to a method below
    }

    @Override
    @ParametersAreNonnullByDefault
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        pelvis.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public float getProportion() {
        return PROPORTION;
    }

    @Override
    public Map<ModelRenderer, ModelRenderer> getImmediateParentMap() {
        return this.immediateParentMap;
    }

    @Override
    public Map<ModelRenderer, ObjectList<ModelRenderer>> getAllParentsMap() {
        return this.allParentsMap;
    }

    @Override
    public Map<ModelRenderer, IHitLoc> getHitLocMap() {
        return this.hitLocMap;
    }

    private void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    private void addChildrenRecursively(ModelRenderer parent) {
        ObjectList<ModelRenderer> children = ObfuscationReflectionHelper.getPrivateValue(ModelRenderer.class, parent, "field_78805_m");
        assert children != null;
        if (children.isEmpty()) {
            allParentsMap.put(parent, new ObjectArrayList<>());
        } else for (ModelRenderer child : children) {
            immediateParentMap.put(child, parent);
            addChildrenRecursively(child);
        }
    }

    private void setUpAllParentsMap() {
        for (ModelRenderer part : allParentsMap.keySet()) {
            addParentsRecursively(part, allParentsMap.get(part));
            Collections.reverse(allParentsMap.get(part));
        }
    }

    private void addParentsRecursively(ModelRenderer child, ObjectList<ModelRenderer> parentsList) {
        ModelRenderer parent = immediateParentMap.get(child);
        if (parent != null) {
            parentsList.add(parent);
            addParentsRecursively(parent, parentsList);
        }
    }
}