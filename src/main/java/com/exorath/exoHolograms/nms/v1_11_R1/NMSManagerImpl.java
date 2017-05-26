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

import com.exorath.exoHolograms.impl.SimpleHologramLine;
import com.exorath.exoHolograms.nms.NMSArmorStand;
import com.exorath.exoHolograms.nms.NMSManager;
import com.sun.org.apache.bcel.internal.generic.RET;
import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.MathHelper;
import net.minecraft.server.v1_11_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;

/**
 * Created by toonsev on 5/26/2017.
 */
public class NMSManagerImpl implements NMSManager {
    @Override
    public NMSArmorStand spawnArmorStand(Location location) {
        WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
        EntityNMSArmorStand as = new EntityNMSArmorStand(nmsWorld);
        as.setLocationNMS(location);
        if (!addEntityToWorld(nmsWorld, as))
            System.out.println("Failed to spawn armorstand :(");
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
        return true;
    }
}
