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
import com.exorath.exoHolograms.nms.NMSManager;
import com.exorath.exoHolograms.nms.v1_11_R1.NMSManagerImpl;
import org.bukkit.Location;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by toonsev on 5/26/2017.
 */
public class HologramsAPI {
    private static HologramsAPI instance;
    private NMSManager nmsManager;

    private Set<Hologram> holograms = new HashSet<>();

    public HologramsAPI() {
        this.nmsManager = new NMSManagerImpl();
        HologramsAPI.instance = this;
    }

    public NMSManager getNMSManager() {
        return nmsManager;
    }

    public SimpleHologram createSimpleHologram(Location location) {
        return new SimpleHologram(location, nmsManager);
    }

    //Hologram registry is currently unused
    private void addHologram(Hologram hologram) {
        holograms.add(hologram);
    }

    private void removeHologram(Hologram hologram) {
        holograms.remove(hologram);
        if (!hologram.isRemoved())
            hologram.remove();
    }

    private Collection<Hologram> getHolograms() {
        return holograms;
    }

    private void clear() {
        for (Hologram hologram : holograms)
            if (!hologram.isRemoved())
                hologram.remove();
        holograms.clear();
    }

    public synchronized static HologramsAPI getInstance() {
        if (instance == null)
            instance = new HologramsAPI();
        return instance;
    }
}
