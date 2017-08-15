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

import groovy.lang.Closure;

public class MetaItem {

    private final MetaItems key;
    private final Object value;

    private String generated = null;
    private boolean resolved = false;

    public MetaItem(final MetaItems key, final Object value) {
        this.key = key;
        this.value = value;
    }

    public MetaItems getKey() {
        return this.key;
    }

    public String getValue() {
        if (!this.resolved) {
            this.generated = this.resolve(this.value);
            this.resolved = true;
        }

        return this.generated;
    }

    public String getAsEntry() {
        return this.key.getId() + ": " + this.getValue();
    }

    public String getError() {
        if (this.key.isRequired() && this.getValue() == null) {
            return "Attribute \"" + this.key.getId() + "\" cannot be null!";
        }

        return null;
    }

    private String resolve(final Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return (String) object;
        }

        if (object instanceof Closure) {
            return this.resolve(((Closure) object).call());
        }

        if (object instanceof Class) {
            return ((Class) object).getName();
        }

        return object.toString();
    }

    @Override
    public boolean equals(final Object item) {
        return item == this || item instanceof MetaItem && ((MetaItem) item).getKey() == this.key;
    }
}
