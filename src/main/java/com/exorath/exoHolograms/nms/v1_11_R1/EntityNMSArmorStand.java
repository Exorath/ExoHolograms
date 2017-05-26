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

import com.exorath.exoHolograms.api.lines.HologramLine;
import com.exorath.exoHolograms.impl.SimpleHologramLine;
import com.exorath.exoHolograms.nms.NMSArmorStand;
import net.minecraft.server.v1_11_R1.EntityArmorStand;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_11_R1.World;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.DamageSource;
import net.minecraft.server.v1_11_R1.EnumInteractionResult;
import net.minecraft.server.v1_11_R1.EntityHuman;
import net.minecraft.server.v1_11_R1.Vec3D;
import net.minecraft.server.v1_11_R1.AxisAlignedBB;
import net.minecraft.server.v1_11_R1.EnumHand;
import net.minecraft.server.v1_11_R1.EnumItemSlot;
import net.minecraft.server.v1_11_R1.ItemStack;
import net.minecraft.server.v1_11_R1.SoundEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;

/**
 * Created by toonsev on 5/26/2017.
 */
public class EntityNMSArmorStand extends EntityArmorStand implements NMSArmorStand {
    private boolean lockTicks;
    private HologramLine hologramLine;

    public EntityNMSArmorStand(World world, HologramLine hologramLine) {
        super(world);
        super.setInvisible(true);
        super.setSmall(true);
        super.setArms(false);
        super.setNoGravity(true);
        super.setBasePlate(true);
        super.setMarker(true);
        super.collides = false;
        super.a(new NullBoundingBox());
        this.hologramLine = hologramLine;
    }

    @Override
    public HologramLine getHologramLine() {
        return hologramLine;
    }

    @Override
    public void setNameNMS(String name) {
        if (name != null && name.length() > 300)
            name = name.substring(0, 300);
        super.setCustomName(name);
        super.setCustomNameVisible(name != null && !name.isEmpty());
    }

    @Override
    public String getNameNMS() {
        return super.getCustomName();
    }

    @Override
    public void setLockTicks(boolean lockTicks) {
        this.lockTicks = lockTicks;
    }

    @Override
    public void setLocationNMS(Location location) {
        super.setPosition(location.getX(), location.getY(), location.getZ());
        PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport(this);
        for (Object obj : super.world.players) {
            if (obj instanceof EntityPlayer) {
                EntityPlayer nmsPlayer = (EntityPlayer) obj;

                double distanceSquared = Math.pow(nmsPlayer.locX - super.locX, 2) + Math.pow(nmsPlayer.locZ - super.locZ, 2);
                if (distanceSquared < 8192 && nmsPlayer.playerConnection != null) {
                    nmsPlayer.playerConnection.sendPacket(teleportPacket);
                }
            }
        }
    }

    @Override
    public void killEntityNMS() {
        super.dead = true;
    }

    //NMS OVERRIDE
    @Override
    public CraftEntity getBukkitEntity() {
        if (super.bukkitEntity == null)
            super.bukkitEntity = new CraftNMSArmorStand(super.world.getServer(), this);
        return super.bukkitEntity;
    }
    @Override
    public boolean c(NBTTagCompound nbttagcompound) {// Do not save NBT.
        return false;
    }

    @Override
    public boolean d(NBTTagCompound nbttagcompound) {// Do not save NBT.
        return false;
    }
    @Override
    public void b(NBTTagCompound nbttagcompound) {// Do not save NBT.
    }
    @Override
    public void f(NBTTagCompound nbttagcompound) {// Do not load NBT.
    }

    @Override
    public void a(NBTTagCompound nbttagcompound) {// Do not load NBT.
    }

    @Override
    public NBTTagCompound e(NBTTagCompound nbttagcompound) {// Do not save NBT.
        return nbttagcompound;
    }
    @Override
    public boolean isCollidable() {
        return false;
    }
    @Override
    public boolean isInvulnerable(DamageSource source) {
        return true;
    }
    @Override
    public void setCustomNameVisible(boolean visible) {
    }
    @Override
    public EnumInteractionResult a(EntityHuman human, Vec3D vec3d, EnumHand enumhand) {// Prevent stand being equipped
        return EnumInteractionResult.PASS;
    }
    @Override
    public boolean c(int i, ItemStack item) {// Prevent stand being equipped
        return false;
    }
    @Override
    public void a(AxisAlignedBB boundingBox) {// Do not change it!
    }
    @Override
    public void setSlot(EnumItemSlot enumitemslot, ItemStack itemstack) {// Prevent stand being equipped
    }


    @Override
    public void a(SoundEffect soundeffect, float f, float f1) {// Remove sounds.
    }
    @Override
    public int getId() {
        //TODO: return fake id on new movement packet?
        return super.getId();
    }

    @Override
    public void A_() {
        if (!lockTicks)
            super.A_();
    }

    @Override
    public void die() {// Prevent being killed.
    }

    @Override
    protected void doTick() {
        if(!lockTicks)
            super.doTick();
    }
}
