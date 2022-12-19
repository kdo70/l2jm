package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Teleport area holder
 */
public class TeleportAreaHolder {

    private final String _name;
    private final String _capital;
    private final String _bypass;

    /**
     * Locations
     */
    protected List<TeleportLocationHolder> _locations = new ArrayList<>();

    /**
     * @param set set
     */
    public TeleportAreaHolder(StatSet set) {
        _name = set.getString("name", null);
        _capital = set.getString("capital", null);
        _bypass = set.getString("bypass", null);
    }

    /**
     * @return name
     */
    public final String getName() {
        return _name;
    }

    /**
     * @return capital
     */
    public String getCapital() {
        return _capital;
    }

    /**
     * @return bypass
     */
    public String getBypass() {
        return _bypass;
    }

    /**
     * @return locations
     */
    public List<TeleportLocationHolder> getLocations() {
        return _locations;
    }
}