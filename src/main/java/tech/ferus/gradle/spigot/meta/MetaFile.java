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

package tech.ferus.gradle.spigot.meta;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.gradle.api.GradleException;
import tech.ferus.gradle.spigot.SpigotGradlePlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class MetaFile {

    public static final String FILE_NAME = "plugin.yml";

    private final List<String> metaLines;
    private final List<String> staticLines;
    private final SpigotGradlePlugin plugin;
    private Path input;
    private Path output;

    public MetaFile(final SpigotGradlePlugin plugin) {
        this.metaLines = Lists.newArrayList();
        this.staticLines = Lists.newArrayList();
        this.plugin = plugin;
        this.output = new File(this.plugin.getProject().getBuildDir(), "sources/java/" + FILE_NAME).toPath();
    }

    public Path getOutput() {
        return this.output;
    }

    public void setInput(final Path input) {
        this.input = input;
    }

    public void setOutput(final Path output) {
        this.output = output;
    }

    public void write() {
        this.validate();
        this.filter();
        this.generate();
        this.write(false);
    }

    private void validate() {
        for (final MetaItem item : this.plugin.getPluginMeta().getItems()) {
            final String error = item.getError();
            if (error != null) {
                throw new GradleException("Failed to parse metadata for Spigot plugin: " + error);
            }
        }
    }

    private void filter() {
        this.staticLines.clear();
        if (!Files.exists(this.input)) {
            return;
        }

        try {
            Files.lines(this.input).forEachOrdered(line -> {
                if (this.isStaticLine(line)) {
                    this.staticLines.add(line);
                }
            });
        } catch (final IOException e) {
            throw new GradleException("Failed to iterate over meta file.", e);
        }

        this.write(true);
    }

    private void generate() {
        this.metaLines.clear();
        for (MetaItem item : this.plugin.getPluginMeta().getItems()) {
            if (item.getValue() != null) {
                this.metaLines.add(item.getAsEntry());
            }
        }
    }

    private void write(final boolean printStatic) {
        final Set<String> lines = Sets.newHashSet(this.metaLines);
        if (printStatic) {
            lines.addAll(this.staticLines);
        }

        final StringBuilder data = new StringBuilder();
        lines.forEach(line -> data.append(line).append("\n"));

        try {
            if (!Files.exists(this.output)) {
                Files.createDirectories(this.output.getParent());
                Files.createFile(this.output);
            }

            Files.write(this.output, data.toString().getBytes());
        } catch (final IOException e) {
            throw new GradleException("Failed to write to file.", e);
        }
    }

    private boolean isStaticLine(final String line) {
        return !this.isMetaLine(line) && (!line.isEmpty() || !this.staticLines.isEmpty());
    }

    private boolean isMetaLine(final String line) {
        for (final MetaItems attribute : MetaItems.values()) {
            if (line.startsWith(attribute.getId() + ":")) {
                return true;
            }
        }

        return false;
    }
}
