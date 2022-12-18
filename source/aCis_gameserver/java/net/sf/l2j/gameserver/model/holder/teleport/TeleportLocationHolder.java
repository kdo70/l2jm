package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;
import net.sf.l2j.gameserver.enums.TeleportType;
import net.sf.l2j.gameserver.model.location.Location;

/**
 * Holder: location gatekeeper
 */
public class TeleportLocationHolder extends Location {

    private final String _name;
    private final String _color;
    private final String _capital;
    private final String _point;
    private final TeleportType _type;
    private final int _priceId;
    private final int _priceCount;
    private final int _castleId;

    /**
     * @param set stat set
     */
    public TeleportLocationHolder(StatSet set) {
        super(
                set.getInteger("x", 0),
                set.getInteger("y", 0),
                set.getInteger("z", 0)
        );
        _name = set.getString("name");
        _color = set.getString("color", null);
        _capital = set.getString("capital", null);
        _point = set.getString("point", null);
        _type = set.getEnum("type", TeleportType.class, TeleportType.STANDARD);
        _priceId = set.getInteger("priceId", 57);
        _priceCount = set.getInteger("priceCount", 0);
        _castleId = set.getInteger("castleId", 0);
    }

    public String getName() {
        return _name;
    }

    public String getColor() {
        return _color;
    }

    public String getCapital() {
        return _capital;
    }

    public String getPoint() {
        return _point;
    }

    public TeleportType getType() {
        return _type;
    }

    public int getPriceId() {
        return _priceId;
    }

    public int getPriceCount() {
        return _priceCount;
    }

    public int getCastleId() {
        return _castleId;
    }
}