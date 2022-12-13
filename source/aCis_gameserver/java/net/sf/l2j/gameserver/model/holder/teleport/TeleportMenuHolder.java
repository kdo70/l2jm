package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;

/**
 * Holder: element menu global gatekeeper
 */
public class TeleportMenuHolder {

    private final String _name;
    private final String _bypass;
    private final String _color;

    /**
     * @param set stat set
     */
    public TeleportMenuHolder(StatSet set) {
        _name = set.getString("name");
        _bypass = set.getString("bypass");
        _color = set.getString("color");
    }

    /**
     * @return name
     */
    public final String getName() {
        return _name;
    }

    /**
     * @return bypass
     */
    public final String getBypass() {
        return _bypass;
    }

    /**
     * @return color
     */
    public final String getColor() {
        return _color;
    }
}