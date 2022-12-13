package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;

/**
 * Holder: location gatekeeper
 */
public class LocationHolder {

    private final String _name;
    private final String _capital;

    /**
     * @param set stat set
     */
    public LocationHolder(StatSet set) {
        _name = set.getString("name");
        _capital = set.getString("capital");
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
    public final String getCapital() {
        return _capital;
    }
}