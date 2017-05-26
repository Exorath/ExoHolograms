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
import com.exorath.exoHolograms.nms.BaseNMSEntity;
import com.exorath.exoHolograms.nms.NMSArmorStand;
import com.exorath.exoHolograms.nms.NMSManager;
import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.MathHelper;
import net.minecraft.server.v1_11_R1.World;
import net.minecraft.server.v1_11_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;

import java.lang.reflect.Method;

/**
 * Created by toonsev on 5/26/2017.
 */
public class NMSManagerImpl implements NMSManager {

    private Method validateEntityMethod;

    public NMSManagerImpl() {
        try {
            validateEntityMethod = World.class.getDeclaredMethod("b", Entity.class);
            validateEntityMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    @Override
    public NMSArmorStand spawnArmorStand(Location location, HologramLine parent) {
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        EntityNMSArmorStand as = new EntityNMSArmorStand(nmsWorld, parent);
        as.setLocationNMS(location);
        if (!addEntityToWorld(nmsWorld, as))
            System.out.println("Failed to spawn armorstand :(");
        if (as == null)
            System.out.println("Spawning armorstand returned null");
        return as;
    }

    private boolean addEntityToWorld(WorldServer nmsWorld, Entity nmsEntity) {
        if (!Bukkit.isPrimaryThread()) {
            System.out.println("Tried to add entity while not on primary thread.");
            return false;
        }
        final int chunkX = MathHelper.floor(nmsEntity.locX / 16.0);
        final int chunkZ = MathHelper.floor(nmsEntity.locZ / 16.0);
        if (!nmsWorld.getChunkProviderServer().isLoaded(chunkX, chunkZ)) {
            nmsEntity.dead = true;
            return false;
        }
        nmsWorld.getChunkAt(chunkX, chunkZ).a(nmsEntity);
        nmsWorld.entityList.add(nmsEntity);
        try {
            validateEntityMethod.invoke(nmsWorld, nmsEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public BaseNMSEntity getNMSEntityBase(org.bukkit.entity.Entity bukkitEntity) {
        Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        return nmsEntity instanceof BaseNMSEntity ? (BaseNMSEntity) nmsEntity : null;
    }
}
