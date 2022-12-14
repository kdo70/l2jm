package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Holder: teleport gatekeeper
 */
public class TeleportHolder {

    private final int _id;
    private final int _menuId;

    /**
     * Locations
     */
    protected List<LocationHolder> _locations = new ArrayList<>();

    /**
     * @param set set
     */
    public TeleportHolder(StatSet set) {
        _id = set.getInteger("id", 0);
        _menuId = set.getInteger("menuId", 0);
    }

    /**
     * @return id
     */
    public final int getId() {
        return _id;
    }

    /**
     * @return menuId
     */
    public final int getMenuId() {
        return _menuId;
    }

    /**
     * @return locations
     */
    public List<LocationHolder> getLocations() {
        return _locations;
    }

    /**
     * @return size
     */
    public int getSize() {
        return _locations.size();
    }
}