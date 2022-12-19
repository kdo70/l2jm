package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Teleport item holder
 */
public class TeleportItemHolder {

    private final String _name;
    private final String _bypass;
    private final String _color;

    /**
     * Areas
     */
    protected List<TeleportAreaHolder> _areas = new ArrayList<>();

    /**
     * @param set set
     */
    public TeleportItemHolder(StatSet set) {
        _name = set.getString("name");
        _bypass = set.getString("bypass", null);
        _color = set.getString("color", null);
    }

    /**
     * @return name
     */
    public String getName() {
        return _name;
    }

    /**
     * @return bypass
     */
    public String getBypass() {
        return _bypass;
    }

    /**
     * @return color
     */
    public String getColor() {
        return _color;
    }

    /**
     * @return areas
     */
    public List<TeleportAreaHolder> getAreas() {
        return _areas;
    }
}