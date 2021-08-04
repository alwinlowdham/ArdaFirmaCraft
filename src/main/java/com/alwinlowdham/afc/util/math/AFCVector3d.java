package com.alwinlowdham.afc.util.math;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.vecmath.Matrix4d;

// DONE
// CLEANED
@MethodsReturnNonnullByDefault
public class AFCVector3d extends Vector3d {

    private static final AFCVector3d ORIGIN = new AFCVector3d(Vector3d.ZERO);

    public AFCVector3d(Vector3d vec3) {
        super(vec3.x, vec3.y, vec3.z);
    }

    public AFCVector3d(BlockPos pos) {
        super(pos.getX(), pos.getY(), pos.getZ());
    }

    public AFCVector3d(double x, double y, double z) {
        super(x, y, z);
    }

    public static AFCVector3d getOrigin() {
        return ORIGIN;
    }

    public AFCVector3d setX(double x) {
        return new AFCVector3d(x, this.y, this.z);
    }

    public AFCVector3d setY(double y) {
        return new AFCVector3d(this.x, y, this.z);
    }

    public AFCVector3d setZ(double z) {
        return new AFCVector3d(this.x, this.y, z);
    }

    public AFCVector3d makeNewVec(double x, double y, double z) {
        return new AFCVector3d(x, y, z);
    }

    public AFCVector3d rotateAroundX(double angle) {
        final double f1 = Math.cos(angle);
        final double f2 = Math.sin(angle);
        final double d1 = this.y * f1 + this.z * f2;
        final double d2 = this.z * f1 - this.y * f2;
        return this.makeNewVec(this.x, d1, d2);
    }

    public AFCVector3d rotateAroundY(double angle) {
        final double f1 = Math.cos(angle);
        final double f2 = Math.sin(angle);
        final double d0 = this.x * f1 + this.z * f2;
        final double d2 = this.z * f1 - this.x * f2;
        return this.makeNewVec(d0, this.y, d2);
    }

    public AFCVector3d rotateAroundZ(double angle) {
        final double f1 = Math.cos(angle);
        final double f2 = Math.sin(angle);
        final double d0 = this.x * f1 + this.y * f2;
        final double d1 = this.y * f1 - this.x * f2;
        return this.makeNewVec(d0, d1, this.z);
    }

    public AFCVector3d rotate(Vector3d origin, Matrix4d rotationMatrix) {
        final javax.vecmath.Vector3d vec = new javax.vecmath.Vector3d(origin.x - x, origin.y - y, origin.z - z);
        rotationMatrix.transform(vec);
        return new AFCVector3d(origin.x + vec.x, origin.y + vec.y, origin.z + vec.z);
    }

    @ParametersAreNonnullByDefault
    public AFCVector3d cross(Vector3d vec) {
        return new AFCVector3d(super.cross(vec));
    }
}

