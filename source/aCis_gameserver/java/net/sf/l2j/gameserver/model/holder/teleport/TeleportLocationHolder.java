package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;
import net.sf.l2j.gameserver.enums.TeleportType;
import net.sf.l2j.gameserver.model.location.Location;

/**
 *  Teleport location holder
 */
public class TeleportLocationHolder extends Location {

    private final String _name;
    private final String _point;
    private final TeleportType _type;
    private final int _priceId;
    private final int _priceCount;
    private final int _castleId;

    /**
     * @param set set
     */
    public TeleportLocationHolder(StatSet set) {
        super(
                set.getInteger("x", 0),
                set.getInteger("y", 0),
                set.getInteger("z", 0)
        );
        _name = set.getString("name");
        _point = set.getString("point", null);
        _type = set.getEnum("type", TeleportType.class, TeleportType.STANDARD);
        _priceId = set.getInteger("priceId", 57);
        _priceCount = set.getInteger("priceCount", 0);
        _castleId = set.getInteger("castleId", 0);
    }

    /**
     * @return name
     */
    public String getName() {
        return _name;
    }

    /**
     * @return point
     */
    public String getPoint() {
        return _point;
    }

    /**
     * @return type
     */
    public TeleportType getType() {
        return _type;
    }

    /**
     * @return price id
     */
    public int getPriceId() {
        return _priceId;
    }

    /**
     * @return price count
     */
    public int getPriceCount() {
        return _priceCount;
    }

    /**
     * @return castle id
     */
    public int getCastleId() {
        return _castleId;
    }
}