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

package com.exorath.exoHolograms.api;

import com.exorath.exoHolograms.impl.SimpleHologram;
import com.exorath.exoHolograms.nms.BaseNMSEntity;
import com.exorath.exoHolograms.nms.NMSManager;
import com.exorath.exoHolograms.nms.v1_11_R1.NMSManagerImpl;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.util.*;

/**
 * Created by toonsev on 5/26/2017.
 */
public class HologramsAPI implements Listener {
    private static boolean registered = false;
    private static HologramsAPI instance;
    private Plugin plugin;
    private NMSManager nmsManager;

    private Set<Hologram> holograms = new HashSet<>();

    public HologramsAPI(Plugin plugin) {
        this.plugin = plugin;
        if (!registered) {
            registered = true;
            if (!ChunkLoadEvent.getHandlerList().getRegisteredListeners(plugin).stream().filter(registeredListener -> registeredListener.getListener() == this).findFirst().isPresent())
                Bukkit.getPluginManager().registerEvents(this, plugin);
        }
        this.nmsManager = new NMSManagerImpl();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChunkLoad(ChunkLoadEvent event) {
        final Chunk chunk = event.getChunk();
        if (chunk.isLoaded()) {
            if (Bukkit.isPrimaryThread()) {
                loadChunk(chunk);
            } else
                Bukkit.getScheduler().runTask(plugin, () -> loadChunk(chunk));
        }
    }

    private void loadChunk(Chunk chunk) {
        for (Hologram hologram : holograms)
            if (hologram.getLocation().getChunk().equals(chunk))
                ((SimpleHologram) hologram).refreshSingleLines();
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkUnload(ChunkUnloadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (!entity.isDead()) {
                BaseNMSEntity entityBase = nmsManager.getNMSEntityBase(entity);
                if (entityBase != null) {
                    entityBase.getHologramLine().getParent().despawnEntities();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (nmsManager.getNMSEntityBase(event.getEntity()) != null)
            if (event.isCancelled()) {
                System.out.println("Uncancelled hologram spawn");
                event.setCancelled(false);
            }
    }

    public NMSManager getNMSManager() {
        return nmsManager;
    }

    public SimpleHologram addHologram(Location location) {
        SimpleHologram hologram = new SimpleHologram(location, nmsManager);
        holograms.add(hologram);
        return hologram;
    }

    public void removeHologram(Hologram hologram) {
        holograms.remove(hologram);
        if (!hologram.isRemoved())
            hologram.remove();
    }

    public Collection<Hologram> getHolograms() {
        return holograms;
    }

    public void clearHolograms() {
        for (Hologram hologram : holograms)
            if (!hologram.isRemoved())
                hologram.remove();
        holograms.clear();
    }

    public synchronized static HologramsAPI getInstance(Plugin plugin) {
        if (instance == null)
            HologramsAPI.instance = new HologramsAPI(plugin);
        return instance;
    }

    /**
     * This will use a random plugin as the plugin for the scheduler
     *
     * @return
     */
    public synchronized static HologramsAPI getInstance() {
        return getInstance(Bukkit.getPluginManager().getPlugins()[0]);
    }

    public Plugin getPlugin(){
        return plugin;
    }
}
