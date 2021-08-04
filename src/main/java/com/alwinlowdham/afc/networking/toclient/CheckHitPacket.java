package com.alwinlowdham.afc.networking.toclient;

import com.alwinlowdham.afc.client.renderer.entity.model.AFCModel;
import com.alwinlowdham.afc.util.combat.CombatHelper;
import com.alwinlowdham.afc.util.combat.hitlocs.interfaces.IHitLoc;
import com.alwinlowdham.afc.util.math.AFCVector3d;
import com.alwinlowdham.afc.util.math.OrientedBB;
import com.alwinlowdham.afc.util.renderer.ModelHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

// CLEANED
public class CheckHitPacket {

    private final int attackerID;
    private final int targetID;

    public CheckHitPacket(int attackerID, int targetID) {
        this.attackerID = attackerID;
        this.targetID = targetID;
    }

    public static void encode(CheckHitPacket pkt, PacketBuffer buf) {
        buf.writeInt(pkt.attackerID);
        buf.writeInt(pkt.targetID);
    }

    public static CheckHitPacket decode(PacketBuffer buf) {
        return new CheckHitPacket(buf.readInt(), buf.readInt());
    }

    private static void handlePacket(CheckHitPacket msg, Supplier<NetworkEvent.Context> ctx) {
        final Minecraft mc = Minecraft.getInstance();
        assert mc.level != null;
        final Entity attacker = mc.level.getEntity(msg.attackerID);
        assert attacker != null;

        final Vector3d start = getStart(attacker);
        assert start != null;
        final Vector3d end = getEnd(attacker, start);
        assert end != null;

        final Entity target = mc.level.getEntity(msg.targetID);
        assert target != null;
        final IHitLoc bestHitLoc = getBestHitLoc(start, end, target);
    }

    private static Vector3d getStart(Entity attacker) {
        final Vector3d start;
        if (attacker instanceof ProjectileEntity) {
            start = attacker.position();
        } else if (attacker instanceof LivingEntity) {
            start = attacker.getEyePosition(1.0F);
        } else start = null;
        System.out.println("Start: " + start);
        return start;
    }

    private static Vector3d getEnd(Entity attacker, Vector3d start) {
        final Vector3d end;
        if (attacker instanceof ProjectileEntity) {
            end = start.add(attacker.getDeltaMovement());
        } else if (attacker instanceof LivingEntity) {
            final Vector3d viewVec = attacker.getViewVector(1.0F);
            final double d0 = CombatHelper.getTotalReach((LivingEntity) attacker);
            end = start.add(viewVec.x * d0, viewVec.y * d0, viewVec.z * d0);
        } else end = null;
        System.out.println("End: " + end);
        return end;
    }

    private static <T extends LivingEntity> IHitLoc getBestHitLoc(Vector3d start, Vector3d end, Entity target) {
        final Map<OrientedBB, ModelRenderer> partMap;
        if (target instanceof LivingEntity) {
            partMap = ModelHelper.getPartMap((LivingEntity) target);
        } else {
            System.out.println("Non-living target.");
            return null;
            // Do later? Would this even ever fire???
        }

        OrientedBB bestBb = null;
        double shortestDistSqr = Double.MAX_VALUE;
        for (OrientedBB bb : partMap.keySet()) {
            final AFCVector3d intersect = bb.getIntersect(new AFCVector3d(start), new AFCVector3d(end));
            if (intersect == null) continue;
            System.out.println("Intersect: " + intersect);
            final double distSqr = intersect.distanceToSqr(start);
            if (distSqr < shortestDistSqr) {
                bestBb = bb;
                shortestDistSqr = distSqr;
            }
        }

        final ModelRenderer bestPart = partMap.get(bestBb);
        final AFCModel<? super T> targetModel = ModelHelper.getTargetModel((LivingEntity) target);
        final IHitLoc bestHitLoc = targetModel.getHitLocMap().get(bestPart);

        if (bestHitLoc != null) {
            System.out.println("Best hit loc: " + bestHitLoc);
        } else System.out.println("No best hit loc found.");
        return bestHitLoc;
    }

    public static class Handler {
        // Email the First Aid Mod developer and see if we can collaborate!
        // Maybe this is a good way for me to get a not-shitty algorithm without me actually doing the work of un-shitifying it!
        // His email: tobias.hotz@hotmail.de

        public static void handle(CheckHitPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() ->
                    // Make sure it's only executed on the physical client
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handlePacket(msg, ctx))
            );
            ctx.get().setPacketHandled(true);
        }
    }
}
