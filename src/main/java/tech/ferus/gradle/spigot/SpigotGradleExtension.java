/*
 * This file is part of SpigotGradle, licensed under the MIT License.
 * Copyright 2017 FerusGrim
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package tech.ferus.gradle.spigot;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import tech.ferus.gradle.spigot.meta.PluginMeta;

public class SpigotGradleExtension {

    public static final String NAME = "spigot";
    public static final String DESCRIPTION = "Making Spigot plugins easier! Now with 100% more Gradle.";

    private final SpigotGradlePlugin plugin;

    public SpigotGradleExtension(final SpigotGradlePlugin plugin) {
        this.plugin = plugin;
    }

    void meta(@DelegatesTo(PluginMeta.class) Closure<?> closure) {
        plugin.getProject().configure(this.plugin.getPluginMeta(), closure);
    }
}
