package com.github.jeffalder.tomlconverter.data;

import java.io.BufferedWriter;
import java.io.IOException;

import com.github.jeffalder.tomlconverter.TomlTable;

public class PluginEntry implements TomlTable.TomlTableRow {
    private final String name;
    private final String group;
    private final String versionKey;
    private final String version;

    public PluginEntry(final GVACoordinates dependency, final String tomlId) {
        this.group = dependency.getGroup();
        this.name = dependency.getName();

        if (dependency.getVersion() == null) {
            this.versionKey = null;
            this.version = null;
        } else if (tomlId != null) {
            this.versionKey = "version.ref";
            this.version = tomlId;
        } else {
            this.versionKey = "version";
            this.version = dependency.getVersion();
        }
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getVersionKey() {
        return versionKey;
    }

    public String getVersion() {
        return version;
    }


    @Override
    public String getBaseId()  {
        return getName();
    }

    @Override
    public void write(BufferedWriter writer) throws IOException {
        writer.write(String.format("{ id = \"%s:%s\"", group, name));
        if (versionKey != null) {
            writer.write(String.format(", %s = \"%s\"", versionKey, version));
        }
        writer.write(" }");
    }
}
