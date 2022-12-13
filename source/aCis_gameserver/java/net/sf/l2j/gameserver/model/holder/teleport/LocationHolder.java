package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;

public class LocationHolder {
    private final String _name;
    private final String _capital;

    public LocationHolder(StatSet set) {
        _name = set.getString("name");
        _capital = set.getString("capital");
    }

    public final String getName() {
        return _name;
    }
    public final String getCapital() {
        return _capital;
    }
}