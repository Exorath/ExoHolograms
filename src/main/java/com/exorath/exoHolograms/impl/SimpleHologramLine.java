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

import com.exorath.exoHolograms.api.lines.HologramLine;
import org.bukkit.Location;

/**
 * Created by toonsev on 5/25/2017.
 */
public abstract  class SimpleHologramLine implements HologramLine {
    private double height;
    private boolean spawned;

    public SimpleHologramLine(double height) {
        this.height = height;
    }

    public void spawn(Location location) {
        remove();
        spawned = true;
    }

    public double getHeight() {
        return height;
    }

    public boolean isSpawned() {
        return spawned;
    }

    @Override
    public void remove() {
        spawned = false;
    }
    public abstract void teleport(Location location);
}
