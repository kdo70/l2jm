package net.sf.l2j.gameserver.model.holder.teleport;

import java.util.ArrayList;
import java.util.List;

public class TeleportHolder {
    private final int _id;
    protected List<LocationHolder> _locations = new ArrayList<>();

    public TeleportHolder(int id) {
        _id = id;
    }

    public final int getId() {
        return _id;
    }

    public List<LocationHolder> getLocations() {
        return _locations;
    }
}