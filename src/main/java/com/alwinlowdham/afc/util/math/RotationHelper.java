package com.alwinlowdham.afc.util.math;

import javax.vecmath.Matrix4d;
import javax.vecmath.Quat4d;

// DONE
// CLEANED
public class RotationHelper {

    public static Matrix4d getRotationMatrix(double xRot, double yRot, double zRot) {

        final double pitch = xRot * 0.5f;
        final double sinPitch = Math.sin(pitch);
        final double cosPitch = Math.cos(pitch);

        final double yaw = yRot * 0.5f;
        final double sinYaw = Math.sin(yaw);
        final double cosYaw = Math.cos(yaw);

        final double roll = zRot * 0.5f;
        final double sinRoll = Math.sin(roll);
        final double cosRoll = Math.cos(roll);

        final double cosRollcosPitch = cosRoll * cosPitch;
        final double sinRollsinPitch = sinRoll * sinPitch;
        final double cosRollsinPitch = cosRoll * sinPitch;
        final double sinRollcosPitch = sinRoll * cosPitch;

        final double quatX = (cosRollcosPitch * sinYaw + sinRollsinPitch * cosYaw);
        final double quatY = (sinRollcosPitch * cosYaw + cosRollsinPitch * sinYaw);
        final double quatZ = (cosRollsinPitch * cosYaw - sinRollcosPitch * sinYaw);
        final double quatW = (cosRollcosPitch * cosYaw - sinRollsinPitch * sinYaw);

        final Quat4d quat = new Quat4d(quatX, quatY, quatZ, quatW);
        final Matrix4d matrix = new Matrix4d();
        matrix.set(quat);
        return matrix;
    }
}
