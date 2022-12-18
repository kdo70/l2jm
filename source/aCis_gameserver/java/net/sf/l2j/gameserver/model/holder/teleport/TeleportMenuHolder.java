package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;

import java.util.ArrayList;
import java.util.List;

public class TeleportMenuHolder {

    private final int _id;

    protected List<TeleportItemHolder> _items = new ArrayList<>();

    public TeleportMenuHolder(StatSet set) {
        _id = set.getInteger("id");
    }

    public final int getId() {
        return _id;
    }

    public List<TeleportItemHolder> getItems() {
        return _items;
    }
}