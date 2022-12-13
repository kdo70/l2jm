package net.sf.l2j.gameserver.model.holder.teleport;

import java.util.ArrayList;
import java.util.List;

/**
 * Holder: teleport gatekeeper
 */
public class TeleportHolder {

    private final int _id;

    /**
     * Locations
     */
    protected List<LocationHolder> _locations = new ArrayList<>();

    /**
     * @param id id
     */
    public TeleportHolder(int id) {
        _id = id;
    }

    /**
     * @return id
     */
    public final int getId() {
        return _id;
    }

    /**
     * @return locations
     */
    public List<LocationHolder> getLocations() {
        return _locations;
    }
}