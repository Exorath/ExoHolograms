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

import com.exorath.exoHolograms.api.lines.HologramLine;
import com.exorath.exoHolograms.api.lines.TextLine;
import org.bukkit.Location;

import java.util.List;

/**
 * Created by toonsev on 5/25/2017.
 */
public interface Hologram {
    TextLine appendTextLine(String line);

    TextLine insertTextLine(int index, String line);

    HologramLine getLine(int index);

    void removeLine(int index);

    void removeLine(HologramLine line);

    List<HologramLine> getLines();


    void clear();

    int size();

    void teleport(Location location);

    Location getLocation();

    void remove();

    double getHeight();

    boolean isRemoved();

    void despawnEntities();

    void setVisible(boolean visible);
}
