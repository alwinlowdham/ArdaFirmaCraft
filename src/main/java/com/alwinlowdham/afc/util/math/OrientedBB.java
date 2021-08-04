package com.alwinlowdham.afc.util.math;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

import javax.vecmath.Matrix4d;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// DONE
// CLEANED
public class OrientedBB {

    private final AxisAlignedBB aabb;
    private final double xRot, yRot, zRot;
    private final Vector3d pivotPoint;

    private final Matrix4d doRotationMatrix;
    private final Matrix4d undoRotationMatrix;

    private final List<AFCVector3d> points;
    private final List<AFCVector3d> normals;

    public OrientedBB(AxisAlignedBB aabb, double xRot, double yRot, double zRot, Vector3d pivotPoint) {
        this.aabb = aabb;
        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;
        this.pivotPoint = pivotPoint;

        System.out.println("pitch: " + xRot);
        System.out.println("yaw: " + yRot);
        System.out.println("roll: " + zRot);

        // order is zyx???!!!
        this.doRotationMatrix = RotationHelper.getRotationMatrix(xRot, yRot, zRot);
        this.undoRotationMatrix = RotationHelper.getRotationMatrix(-xRot, -yRot, -zRot);

        this.points = Lists.newArrayList(
                new AFCVector3d(aabb.minX, aabb.minY, aabb.minZ).rotate(pivotPoint, this.doRotationMatrix),
                new AFCVector3d(aabb.maxX, aabb.minY, aabb.minZ).rotate(pivotPoint, this.doRotationMatrix),
                new AFCVector3d(aabb.maxX, aabb.minY, aabb.maxZ).rotate(pivotPoint, this.doRotationMatrix),
                new AFCVector3d(aabb.minX, aabb.minY, aabb.maxZ).rotate(pivotPoint, this.doRotationMatrix),
                new AFCVector3d(aabb.minX, aabb.maxY, aabb.minZ).rotate(pivotPoint, this.doRotationMatrix),
                new AFCVector3d(aabb.maxX, aabb.maxY, aabb.minZ).rotate(pivotPoint, this.doRotationMatrix),
                new AFCVector3d(aabb.maxX, aabb.maxY, aabb.maxZ).rotate(pivotPoint, this.doRotationMatrix),
                new AFCVector3d(aabb.minX, aabb.maxY, aabb.maxZ).rotate(pivotPoint, this.doRotationMatrix)
        );
        System.out.println("Points: " + this.points);

        this.normals = Lists.newArrayList(new javax.vecmath.Vector3d(1, 0, 0), new javax.vecmath.Vector3d(0, 1, 0), new javax.vecmath.Vector3d(0, 0, 1)).stream().map(v -> {
            this.doRotationMatrix.transform(v);
            return new AFCVector3d(v.x, v.y, v.z);
        }).collect(Collectors.toList());
    }

    public OrientedBB(AxisAlignedBB aabb, Vector3d pivotPoint) {
        this.aabb = aabb;
        this.xRot = this.yRot = this.zRot = 0;
        this.pivotPoint = pivotPoint;

        this.doRotationMatrix = RotationHelper.getRotationMatrix(xRot, yRot, zRot);
        this.undoRotationMatrix = RotationHelper.getRotationMatrix(-xRot, -yRot, -zRot);

        this.points = Lists.newArrayList(
                new AFCVector3d(aabb.minX, aabb.minY, aabb.minZ),
                new AFCVector3d(aabb.maxX, aabb.minY, aabb.minZ),
                new AFCVector3d(aabb.maxX, aabb.minY, aabb.maxZ),
                new AFCVector3d(aabb.minX, aabb.minY, aabb.maxZ),
                new AFCVector3d(aabb.minX, aabb.maxY, aabb.minZ),
                new AFCVector3d(aabb.maxX, aabb.maxY, aabb.minZ),
                new AFCVector3d(aabb.maxX, aabb.maxY, aabb.maxZ),
                new AFCVector3d(aabb.minX, aabb.maxY, aabb.maxZ)
        );
        System.out.println("Points: " + this.points);

        this.normals = Lists.newArrayList(new javax.vecmath.Vector3d(1, 0, 0), new javax.vecmath.Vector3d(0, 1, 0), new javax.vecmath.Vector3d(0, 0, 1)).stream().map(v -> {
            this.doRotationMatrix.transform(v);
            return new AFCVector3d(v.x, v.y, v.z);
        }).collect(Collectors.toList());
    }

    private OrientedBB(OrientedBB bb, AFCVector3d offset) {
        this.aabb = bb.getAabb();
        this.xRot = bb.getXrot();
        this.yRot = bb.getYrot();
        this.zRot = bb.getZrot();
        this.pivotPoint = bb.getPivotPoint();

        this.doRotationMatrix = bb.getDoRotationMatrix();
        this.undoRotationMatrix = bb.getUndoRotationMatrix();

        this.points = bb.getPoints().stream().map(p -> new AFCVector3d(p.add(offset))).collect(Collectors.toList());
        this.normals = bb.getNormals();
    }

    public CollisionResult getCollisionResult(List<AxisAlignedBB> other, Vector3d move) {
        final List<CollisionResult> collisions = other.stream().map((bb) -> this.getCollisionResult(bb, move)).collect(Collectors.toList());
        final boolean willCollide = collisions.stream().anyMatch(CollisionResult::isWillCollide);
        final boolean isColliding = collisions.stream().anyMatch(CollisionResult::isColliding);
        if (!willCollide && !isColliding)
            return new CollisionResult().setHitPoint(new AFCVector3d(move)).setWillCollide(false).setColliding(false);

        final List<AFCVector3d> hits = collisions.stream().map(CollisionResult::getHitPoint).collect(Collectors.toList());
        final double minX = hits.stream().min(Comparator.comparingDouble(v -> Math.abs(v.x))).map(AFCVector3d::x).get();
        final double minY = hits.stream().min(Comparator.comparingDouble(v -> Math.abs(v.y))).map(AFCVector3d::y).get();
        final double minZ = hits.stream().min(Comparator.comparingDouble(v -> Math.abs(v.z))).map(AFCVector3d::z).get();

        return new CollisionResult()
                .setWillCollide(willCollide)
                .setColliding(isColliding)
                .setHitPoint(new AFCVector3d(minX, minY, minZ));
    }

    public CollisionResult getCollisionResult(AxisAlignedBB other, Vector3d move) {
        return this.getCollisionResult(new OrientedBB(other, other.getCenter()), move);
    }

    public CollisionResult getCollisionResult(OrientedBB other, Vector3d move) {
        final ImmutableList<AFCVector3d> axes = this.getAxes(other);
        final CollisionResult out = new CollisionResult();
        final List<Tuple<AFCVector3d, Double>> collisionDistances = Lists.newArrayList();

        for (AFCVector3d axe : axes) {
            if (axe.equals(AFCVector3d.getOrigin()))
                continue;

            final double[] proj = this.getPoints().stream().mapToDouble(p -> p.dot(axe)).toArray();
            final double[] otherProj = other.getPoints().stream().mapToDouble(p -> p.dot(axe)).toArray();
            double min = Arrays.stream(proj).min().orElse(0);
            double max = Arrays.stream(proj).max().orElse(0);
            final double otherMin = Arrays.stream(otherProj).min().orElse(0);
            final double otherMax = Arrays.stream(otherProj).max().orElse(0);

            double dist = min < otherMin ? otherMin - max : min - otherMax;
            if (dist > 0)
                out.setColliding(false);

            final double projectedVel = axe.dot(move);
            min += projectedVel < 0 ? projectedVel : 0;
            max += projectedVel >= 0 ? projectedVel : 0;
            dist = min < otherMin ? otherMin - max : min - otherMax;

            if (dist > 0) out.setWillCollide(false);

            if (!out.hasFoundCollision())
                break;

            collisionDistances.add(new Tuple<>(axe, Math.abs(dist)));
        }

        if (out.isWillCollide()) {
            final Tuple<AFCVector3d, Double> minDist = collisionDistances.stream().min(Comparator.comparing(Tuple::getB)).get();
            final AFCVector3d movementAxis = new AFCVector3d(
                    this.getAabb().getCenter().subtract(other.getAabb().getCenter()).dot(minDist.getA()) < 0
                            ? minDist.getA().multiply(-1, -1, -1)
                            : minDist.getA());
            out.setHitPoint(new AFCVector3d(movementAxis.multiply(minDist.getB(), minDist.getB(), minDist.getB())));
        }

        return out;
    }

    public ImmutableList<AFCVector3d> getAxes(OrientedBB other) {
        return ImmutableList.copyOf(this.getNormals()
                .stream()
                .flatMap(n0 -> other.getNormals()
                        .stream()
                        .map(n0::cross))
                .collect(Collectors.toList()));
    }

    public AxisAlignedBB getAabb() {
        return this.aabb;
    }

    public double getXrot() {
        return this.xRot;
    }

    public double getYrot() {
        return this.yRot;
    }

    public double getZrot() {
        return this.zRot;
    }

    public Vector3d getPivotPoint() {
        return this.pivotPoint;
    }

    public Matrix4d getDoRotationMatrix() {
        return this.doRotationMatrix;
    }

    public final Matrix4d getUndoRotationMatrix() {
        return this.undoRotationMatrix;
    }

    public ImmutableList<AFCVector3d> getPoints() {
        return ImmutableList.copyOf(this.points);
    }

    public ImmutableList<AFCVector3d> getNormals() {
        return ImmutableList.copyOf(this.normals);
    }

    public double getXsize() {
        return this.aabb.getXsize();
    }

    public double getYsize() {
        return this.aabb.getYsize();
    }

    public double getZsize() {
        return this.aabb.getZsize();
    }

    public OrientedBB offset(AFCVector3d by) {
        return new OrientedBB(this, by);
    }

    public AFCVector3d getIntersect(AFCVector3d start, AFCVector3d end) {
        final double xRot = -this.xRot;
        final double yRot = -this.yRot;
        final double zRot = -this.zRot;

        final Matrix4d undoRotationMatrix = RotationHelper.getRotationMatrix(xRot, yRot, zRot);
        final Matrix4d redoRotationMatrix = RotationHelper.getRotationMatrix(-xRot, -yRot, -zRot);

        final AFCVector3d startAA = new AFCVector3d(start.x, start.y, start.z)
                .rotate(this.pivotPoint, undoRotationMatrix);
        final AFCVector3d endAA = new AFCVector3d(end.x, end.y, end.z)
                .rotate(this.pivotPoint, undoRotationMatrix);

        Optional<Vector3d> optional = this.aabb.clip(startAA, endAA);
        if (optional.isPresent()) {

            final AFCVector3d intersectAA = new AFCVector3d(optional.get());
            final double distanceAA = startAA.distanceToSqr(intersectAA);

            final AFCVector3d intersect = new AFCVector3d(intersectAA.x, intersectAA.y, intersectAA.z)
                    .rotate(this.pivotPoint, redoRotationMatrix);

            final double distance = start.distanceToSqr(intersect); // temp for debug
            System.out.println("distanceAA: " + distanceAA);
            System.out.println("distance: " + distance);
            // distanceAA and distance should be EQUAL!

            return intersect;
        } else return null;
    }

    public class CollisionResult {
        public AFCVector3d hitPoint = AFCVector3d.getOrigin();
        public boolean isColliding = true;
        public boolean willCollide = true;

        public AFCVector3d getHitPoint() {
            return this.hitPoint;
        }

        protected CollisionResult setHitPoint(AFCVector3d hitPoint) {
            this.hitPoint = hitPoint;
            return this;
        }

        public boolean isColliding() {
            return this.isColliding;
        }

        protected CollisionResult setColliding(boolean colliding) {
            this.isColliding = colliding;
            return this;
        }

        public boolean isWillCollide() {
            return this.willCollide;
        }

        protected CollisionResult setWillCollide(boolean willCollide) {
            this.willCollide = willCollide;
            return this;
        }

        public boolean hasFoundCollision() {
            return this.isColliding || this.willCollide;
        }
    }
}



