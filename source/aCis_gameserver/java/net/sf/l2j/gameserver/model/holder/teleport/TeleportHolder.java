package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;

import java.util.ArrayList;
import java.util.List;

public class AreaHolder {
    private final int _id;
    protected List<LocationHolder> _locations = new ArrayList<>();

    public AreaHolder(StatSet set) {
        _id = set.getInteger("id");
    }

    public int getId() {
        return _id;
    }

    public List<LocationHolder> getLocations() {
        return _locations;
    }
}