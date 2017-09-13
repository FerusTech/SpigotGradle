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

import com.google.common.collect.Sets;
import org.gradle.api.Project;

import java.util.List;
import java.util.Set;

public class PluginMeta {

    private final Set<MetaItem> items;

    public PluginMeta() {
        this.items = Sets.newHashSet();
    }

    public void setDefaults(final Project project) {
        this.set(MetaItems.NAME, project.getName());
        this.set(MetaItems.MAIN, project.getGroup() + "." + project.getName().toLowerCase() + "." + project.getName());
        this.set(MetaItems.VERSION, project.getVersion());
    }

    public String get(final MetaItems key) {
        for (MetaItem item : this.items) {
            if (item.getKey() == key) {
                return item.getValue();
            }
        }

        return null;
    }

    public void setName(final Object object) {
        this.set(MetaItems.NAME, object);
    }

    public void setDescription(final Object object) {
        this.set(MetaItems.DESCRIPTION, object);
    }

    public void setMain(final Object object) {
        this.set(MetaItems.MAIN, object);
    }

    public void setVersion(final Object object) {
        this.set(MetaItems.VERSION, object);
    }

    public void setWebsite(final Object object) {
        this.set(MetaItems.WEBSITE, object);
    }

    public void setAuthor(final Object object) {
        this.set(MetaItems.AUTHOR, object);
    }

    public void setAuthors(final List<String> authors) {
        this.set(MetaItems.AUTHORS, authors);
    }

    public void setDepend(final List<String> depend) {
        this.set(MetaItems.DEPEND, depend);
    }

    private void set(final MetaItems key, final Object value) {
        this.items.add(new MetaItem(key, value));
    }

    public Set<MetaItem> getItems() {
        return this.items;
    }
}
