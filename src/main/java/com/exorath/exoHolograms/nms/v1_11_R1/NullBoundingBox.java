/*
 * Copyright 2017 Exorath
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.exorath.exoHolograms.nms.v1_11_R1;

import net.minecraft.server.v1_11_R1.AxisAlignedBB;
import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.MovingObjectPosition;
import net.minecraft.server.v1_11_R1.Vec3D;

/**
 * Created by toonsev on 5/26/2017.
 */
public class NullBoundingBox extends AxisAlignedBB {
    public NullBoundingBox() {
        super(0, 0, 0, 0, 0, 0);
    }
    @Override
    public double a() {
        return 0.0;
    }

    @Override
    public double a(AxisAlignedBB arg0, double arg1) {
        return 0.0;
    }

    @Override
    public AxisAlignedBB a(AxisAlignedBB arg0) {
        return this;
    }

    @Override
    public AxisAlignedBB a(double arg0, double arg1, double arg2) {
        return this;
    }

    @Override
    public MovingObjectPosition b(Vec3D arg0, Vec3D arg1) {
        return null;
    }

    @Override
    public double b(AxisAlignedBB arg0, double arg1) {
        return 0.0;
    }

    @Override
    public double c(AxisAlignedBB arg0, double arg1) {
        return 0.0;
    }

    @Override
    public AxisAlignedBB grow(double arg0, double arg1, double arg2) {
        return this;
    }

    @Override
    public AxisAlignedBB shrink(double arg0) {
        return this;
    }

    @Override
    public AxisAlignedBB a(BlockPosition arg0) {
        return this;
    }

    @Override
    public boolean a(double arg0, double arg1, double arg2, double arg3, double arg4, double arg5) {
        return false;
    }

    @Override
    public boolean b(Vec3D arg0) {
        return false;
    }

    @Override
    public boolean c(Vec3D arg0) {
        return false;
    }

    @Override
    public boolean d(Vec3D arg0) {
        return false;
    }

    @Override
    public AxisAlignedBB e(double arg0) {
        return this;
    }

    @Override
    public AxisAlignedBB g(double arg0) {
        return this;
    }

    @Override
    public AxisAlignedBB a(Vec3D arg0) {
        return this;
    }

    @Override
    public AxisAlignedBB b(AxisAlignedBB arg0) {
        return this;
    }

    @Override
    public AxisAlignedBB b(double arg0, double arg1, double arg2) {
        return this;
    }

    @Override
    public boolean c(AxisAlignedBB arg0) {
        return false;
    }

    @Override
    public AxisAlignedBB d(double arg0, double arg1, double arg2) {
        return this;
    }

    @Override
    public boolean e(Vec3D arg0) {
        return false;
    }


}
