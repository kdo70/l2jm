package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;

import java.util.ArrayList;
import java.util.List;

public class TeleportItemHolder {

    private final String _name;
    private final String _bypass;
    private final String _color;

    protected List<TeleportAreaHolder> _areas = new ArrayList<>();

    public TeleportItemHolder(StatSet set) {
        _name = set.getString("name");
        _bypass = set.getString("bypass", null);
        _color = set.getString("color", null);
    }

    public String getName() {
        return _name;
    }

    public String getBypass() {
        return _bypass;
    }

    public String getColor() {
        return _color;
    }

    public List<TeleportAreaHolder> getAreas() {
        return _areas;
    }
}