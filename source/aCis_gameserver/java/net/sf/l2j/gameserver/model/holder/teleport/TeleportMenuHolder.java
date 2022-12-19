package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Teleport menu holder
 */
public class TeleportMenuHolder {

    private final int _id;

    /**
     * Items
     */
    protected List<TeleportItemHolder> _items = new ArrayList<>();

    /**
     * @param set set
     */
    public TeleportMenuHolder(StatSet set) {
        _id = set.getInteger("id");
    }

    /**
     * @return id
     */
    public final int getId() {
        return _id;
    }

    /**
     * @return items
     */
    public List<TeleportItemHolder> getItems() {
        return _items;
    }
}