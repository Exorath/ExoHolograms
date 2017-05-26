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
import com.exorath.exoHolograms.api.lines.TextLine;
import com.exorath.exoHolograms.nms.NMSManager;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toonsev on 5/25/2017.
 */
public class SimpleHologram implements Hologram {
    private static final double SPACE_BETWEEN_LINES = 0.02d;
    private Location location;
    private final List<SimpleHologramLine> lines = new ArrayList<>();
    private boolean removed;
    private NMSManager nmsManager;

    public SimpleHologram(Location location, NMSManager nmsManager) {
        this.location = location;
        this.nmsManager = nmsManager;
    }

    public TextLine appendTextLine(String text) {
        if (removed)
            return null;
        SimpleTextLine simpleTextLine = new SimpleTextLine(text, nmsManager.spawnArmorStand(location));
        lines.add(simpleTextLine);
        refreshSingleLines();
        return simpleTextLine;
    }

    public TextLine insertTextLine(int index, String text) {
        if (removed)
            return null;
        SimpleTextLine simpleTextLine = new SimpleTextLine(text, nmsManager.spawnArmorStand(location));
        lines.add(index, simpleTextLine);
        refreshSingleLines();
        return simpleTextLine;
    }

    public HologramLine getLine(int index) {
        return lines.get(index);
    }

    public void removeLine(int index) {
        if (removed)
            return;
        lines.remove(index);

    }

    @Override
    public double getHeight() {
        if (lines.isEmpty())
            return 0;
        double height = 0.0;
        for (SimpleHologramLine line : lines)
            height += line.getHeight();
        height += SPACE_BETWEEN_LINES * (lines.size() - 1);
        return height;

    }

    public void clear() {
        lines.forEach(line -> line.remove());
        lines.clear();
    }

    public int size() {
        return lines.size();
    }

    public void teleport(Location location) {
        boolean difWorld = this.location.getWorld() != location.getWorld();
        this.location = location;
        if (difWorld) {
            despawnEntities();
            refreshSingleLines();
            return;
        }
        refreshSingleLines();


    }

    public Location getLocation() {
        return location;
    }

    public void remove() {
        if (removed) {
            removed = true;
            clear();
        }
    }

    public boolean isRemoved() {
        return removed;
    }

    private void refreshSingleLines() {
        if (location.getWorld().isChunkLoaded(location.getChunk().getX(), location.getChunk().getZ())) {
            double currentY = location.getY();
            boolean first = true;

            for (SimpleHologramLine line : lines) {
                currentY -= line.getHeight();
                if (first) {
                    first = false;
                } else
                    currentY -= SPACE_BETWEEN_LINES;
                if (line.isSpawned()) {
                    line.teleport(new Location(location.getWorld(), location.getX(), currentY, location.getZ()));
                } else
                    line.spawn(new Location(location.getWorld(), location.getX(), currentY, location.getZ()));
            }
        }
    }

    private void despawnEntities() {
        for (SimpleHologramLine line : lines)
            line.remove();
    }
}
