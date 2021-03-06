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
    private final List<HologramLine> lines = new ArrayList<>();
    private boolean removed;
    private NMSManager nmsManager;
    private boolean visible = true;

    public SimpleHologram(Location location, NMSManager nmsManager) {
        this.location = location;
        this.nmsManager = nmsManager;
    }

    @Override
    public List<HologramLine> getLines() {
        return lines;
    }

    public TextLine appendTextLine(String text) {
        if (removed)
            return null;
        SimpleTextLine simpleTextLine = new SimpleTextLine(text, this, nmsManager);
        if (!visible)
            simpleTextLine.setVisible(false);
        lines.add(simpleTextLine);
        refreshSingleLines();
        return simpleTextLine;
    }

    public TextLine insertTextLine(int index, String text) {
        if (removed)
            return null;
        SimpleTextLine simpleTextLine = new SimpleTextLine(text, this, nmsManager);
        if (!visible)
            simpleTextLine.setVisible(false);
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
    public void removeLine(HologramLine line) {
        boolean removed = lines.remove(line);
        if (removed)
            refreshSingleLines();
        line.despawn();

    }

    @Override
    public double getHeight() {
        if (lines.isEmpty())
            return 0;
        double height = 0.0;
        for (HologramLine line : lines)
            height += line.getHeight();
        height += SPACE_BETWEEN_LINES * (lines.size() - 1);
        return height;

    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        lines.forEach(line -> line.setVisible(visible));
        if (visible)
            refreshSingleLines();
    }

    public void clear() {
        lines.forEach(line -> line.despawn());
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
        if (!removed) {
            removed = true;
            clear();
        }
    }

    public boolean isRemoved() {
        return removed;
    }

    public void refreshSingleLines() {
        if (!visible)
            return;
        if (location.getWorld().isChunkLoaded(location.getChunk().getX(), location.getChunk().getZ())) {
            double currentY = location.getY();
            boolean first = true;

            for (HologramLine line : lines) {
                SimpleHologramLine simpleHologramLine = (SimpleHologramLine) line;
                currentY -= line.getHeight();
                if (first) {
                    first = false;
                } else
                    currentY -= SPACE_BETWEEN_LINES;
                if (simpleHologramLine.isSpawned()) {
                    simpleHologramLine.teleport(new Location(location.getWorld(), location.getX(), currentY, location.getZ()));
                } else
                    simpleHologramLine.spawn(new Location(location.getWorld(), location.getX(), currentY, location.getZ()));
            }
        } else
            System.out.println("Tried to update some holo lines but the chunk wasn't loaded");
    }

    public void despawnEntities() {
        for (HologramLine line : lines)
            line.despawn();
    }
}
