package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;
import net.sf.l2j.gameserver.model.multisell.Entry;

import java.util.ArrayList;
import java.util.List;

public class TeleportListHolder {
    private final int _id;
    protected List<TeleportListHolder> _list = new ArrayList<>();

    public TeleportListHolder(StatSet set) {
        _id = set.getInteger("id");
    }

    public int getId() {
        return _id;
    }
}