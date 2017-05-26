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

import com.exorath.exoHolograms.api.lines.TextLine;
import com.exorath.exoHolograms.nms.NMSArmorStand;
import com.exorath.exoHolograms.nms.NMSManager;
import org.bukkit.Location;

/**
 * Created by toonsev on 5/25/2017.
 */
public class SimpleTextLine extends SimpleHologramLine implements TextLine {
    private String text;
    private NMSArmorStand nmsArmorStand;

    private NMSManager nmsManager;

    public SimpleTextLine(String text, SimpleHologram parent, NMSManager nmsManager) {
        super(0.23, parent);
        this.nmsManager = nmsManager;
        setText(text);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
        if (nmsArmorStand != null)
            nmsArmorStand.setNameNMS(text != null && !text.isEmpty() ? text : "");
    }

    @Override
    public void teleport(Location location) {
        if (nmsArmorStand != null)
            nmsArmorStand.setLocationNMS(new Location(location.getWorld(), location.getX(), location.getY() + getTextOffset(), location.getZ()));

    }

    @Override
    public void spawn(Location location) {
        super.spawn(location);
        if (nmsArmorStand == null) {
            nmsArmorStand = nmsManager.spawnArmorStand(location, this);
            nmsArmorStand.setNameNMS(text != null && !text.isEmpty() ? text : "");
            this.teleport(location);
        }
    }

    @Override
    public void despawn() {
        super.despawn();
        if (nmsArmorStand != null) {
            nmsArmorStand.killEntityNMS();
            nmsArmorStand = null;
        }
    }

    private double getTextOffset() {
        return -0.29d;
    }
}
