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

import com.google.common.collect.ImmutableMap;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPluginConvention;
import tech.ferus.gradle.spigot.meta.MetaFile;
import tech.ferus.gradle.spigot.meta.PluginMeta;
import tech.ferus.gradle.spigot.meta.PluginMetaTask;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;

public class SpigotGradlePlugin implements Plugin<Project> {

    private Project project;
    private PluginMeta pluginMeta;

    @Override
    public void apply(final Project project) {
        this.project = project;

        this.pluginMeta = new PluginMeta();
        this.pluginMeta.setDefaults(this.project);

        this.project.getExtensions().create(SpigotGradleExtension.NAME, SpigotGradleExtension.class, this);

        this.defineMetaTask();
    }

    private void defineMetaTask() {
        final PluginMetaTask pluginMetaTask = this.makeTask("generatePluginMeta", PluginMetaTask.class);
        pluginMetaTask.setMetaFile(new MetaFile(this));

        pluginMetaTask.setGroup(SpigotGradleExtension.NAME);
        pluginMetaTask.setDescription(SpigotGradleExtension.DESCRIPTION);

        this.project.getTasks().getByName("processResources").dependsOn(pluginMetaTask);
        this.project.copySpec().from(pluginMetaTask.getMetaFile().getOutput().toFile());

        pluginMetaTask.getMetaFile().setInput(this.project.getConvention().getPlugin(JavaPluginConvention.class)
                .getSourceSets().getByName("main").getResources().getSrcDirs().iterator()
                .next().toPath().resolve(MetaFile.FILE_NAME));
        pluginMetaTask.getMetaFile().setOutput(new File(this.project.getBuildDir(),
                "resources/main/" + MetaFile.FILE_NAME).toPath());
    }

    public Project getProject() {
        return this.project;
    }

    public PluginMeta getPluginMeta() {
        return this.pluginMeta;
    }

    private <T extends Task> T makeTask(final String name, final Class<T> type) {
        return (T) this.project.task(ImmutableMap.of("name", name, "type", type), name);
    }
}
