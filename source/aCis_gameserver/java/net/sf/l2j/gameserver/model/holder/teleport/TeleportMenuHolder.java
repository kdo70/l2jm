package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;

public class TeleportMenuHolder {
    private final String _name;
    private final String _bypass;
    private final String _color;

    public TeleportMenuHolder(StatSet set) {
        _name = set.getString("name");
        _bypass = set.getString("bypass");
        _color = set.getString("color");
    }

    public final String getName() {
        return _name;
    }

    public final String getBypass() {
        return _bypass;
    }

    public final String getColor() {
        return _color;
    }
}