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

package com.exorath.exoHolograms.impl;

import com.exorath.exoHolograms.api.Hologram;
import com.exorath.exoHolograms.api.lines.HologramLine;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toonsev on 5/25/2017.
 */
public class SimpleHologram implements Hologram {
    private Location location;
    private final List<HologramLine> lines;
    private boolean removed;

    public SimpleHologram(Location location) {
        this.location = location;
        lines = new ArrayList<>();
    }

    public void  appendLine(HologramLine line) {
        if(removed)
            return;
        lines.add(line);
    }

    public void insertLine(int index, HologramLine line) {
        if(removed)
            return;
        lines.add(index, line);
    }

    public HologramLine getLine(int index) {
        return lines.get(index);
    }

    public void removeLine(int index) {
        if(removed)
            return;
        lines.remove(index);

    }

    public void clear() {

    }

    public int size() {
        return lines.size();
    }

    public void teleport(Location location) {
        if(this.location.getWorld() != location.getWorld()){
            //despawn entities and recreate;
            return;
        }
        double height = location.getY();




    }

    public Location getLocation() {
        return location;
    }

    public void remove() {
        if(removed) {
            removed = true;
            clear();
        }
    }

    public boolean isRemoved() {
        return removed;
    }
}
