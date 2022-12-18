package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;

import java.util.ArrayList;
import java.util.List;

public class TeleportAreaHolder {

    private final String _name;
    private final String _capital;
    private final String _bypass;

    protected List<TeleportLocationHolder> _locations = new ArrayList<>();

    public TeleportAreaHolder(StatSet set) {
        _name = set.getString("name", null);
        _capital = set.getString("capital", null);
        _bypass = set.getString("bypass", null);
    }

    public final String getName() {
        return _name;
    }

    public String getCapital() {
        return _capital;
    }

    public String getBypass() {
        return _bypass;
    }

    public List<TeleportLocationHolder> getLocations() {
        return _locations;
    }
}