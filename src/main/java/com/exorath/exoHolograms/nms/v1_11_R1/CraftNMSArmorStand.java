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


import java.util.Collection;

import com.exorath.exoHolograms.api.HologramsAPI;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

/**
 * Created by toonsev on 5/26/2017.
 */
public class CraftNMSArmorStand extends CraftArmorStand {
    public CraftNMSArmorStand(CraftServer server, EntityNMSArmorStand entity) {
        super(server, entity);
        setMetadata("doNotDespawn", new FixedMetadataValue(HologramsAPI.getInstance().getPlugin(), ""));
    }

    // DISSALOW BUKKIT METHODS
    @Override
    public void remove() {// Cannot be removed, this is the most important to override.
    }

    // Methods from Armor stand class
    @Override public void setArms(boolean arms) { }
    @Override public void setBasePlate(boolean basePlate) { }
    @Override public void setBodyPose(EulerAngle pose) { }
    @Override public void setBoots(ItemStack item) { }
    @Override public void setChestplate(ItemStack item) { }
    @Override public void setHeadPose(EulerAngle pose) { }
    @Override public void setHelmet(ItemStack item) { }
    @Override public void setItemInHand(ItemStack item) { }
    @Override public void setLeftArmPose(EulerAngle pose) { }
    @Override public void setLeftLegPose(EulerAngle pose) { }
    @Override public void setLeggings(ItemStack item) { }
    @Override public void setRightArmPose(EulerAngle pose) { }
    @Override public void setRightLegPose(EulerAngle pose) { }
    @Override public void setSmall(boolean small) { }
    @Override public void setVisible(boolean visible) { }
    @Override public void setMarker(boolean marker) { }

    // Methods from LivingEntity class
    @Override public boolean addPotionEffect(PotionEffect effect) { return false; }
    @Override public boolean addPotionEffect(PotionEffect effect, boolean param) { return false; }
    @Override public boolean addPotionEffects(Collection<PotionEffect> effects) { return false; }
    @Override public void setRemoveWhenFarAway(boolean remove) { }
    @Override public void setAI(boolean ai) { }
    @Override public void setCanPickupItems(boolean pickup) { }
    @Override public void setCollidable(boolean collidable) { }
    @Override public void setGliding(boolean gliding) {	}
    @Override public boolean setLeashHolder(Entity holder) { return false; }

    // Methods from Entity
    @Override public void setVelocity(Vector vel) { }
    @Override public boolean teleport(Location loc) { return false; }
    @Override public boolean teleport(Entity entity) { return false; }
    @Override public boolean teleport(Location loc, TeleportCause cause) { return false; }
    @Override public boolean teleport(Entity entity, TeleportCause cause) { return false; }
    @Override public void setFireTicks(int ticks) { }
    @Override public boolean setPassenger(Entity entity) { return false; }
    @Override public boolean eject() { return false; }
    @Override public boolean leaveVehicle() { return false; }
    @Override public void playEffect(EntityEffect effect) { }
    @Override public void setCustomName(String name) { }
    @Override public void setCustomNameVisible(boolean flag) { }
    @Override public void setGlowing(boolean flag) { }
    @Override public void setGravity(boolean gravity) { }
    @Override public void setInvulnerable(boolean flag) { }
    @Override public void setMomentum(Vector value) { }
    @Override public void setSilent(boolean flag) { }
    @Override public void setTicksLived(int value) { }
}
