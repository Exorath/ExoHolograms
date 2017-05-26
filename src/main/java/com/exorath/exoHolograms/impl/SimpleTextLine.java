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
import com.exorath.exoHolograms.nms.NMSNameable;
import org.bukkit.Location;

/**
 * Created by toonsev on 5/25/2017.
 */
public class SimpleTextLine extends SimpleHologramLine implements TextLine {
    private String text;
    private NMSNameable nmsNameble;

    public SimpleTextLine(String text, NMSNameable nmsNameable) {
        super(0.23);
        this.nmsNameble = nmsNameable;
        setText(text);
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
        if (nmsNameble != null)
            nmsNameble.setNameNMS(text != null && !text.isEmpty() ? text : "");
    }

    @Override
    public void teleport(Location location) {
        if (nmsNameble != null)
            nmsNameble.setLocationNMS(new Location(location.getWorld(), location.getX(), location.getY() + getTextOffset(), location.getZ()));

    }

    private double getTextOffset() {
        return -0.29d;
    }
}
