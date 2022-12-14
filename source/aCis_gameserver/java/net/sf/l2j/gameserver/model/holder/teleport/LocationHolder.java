package net.sf.l2j.gameserver.model.holder.teleport;

import net.sf.l2j.commons.data.StatSet;
import net.sf.l2j.gameserver.model.location.Location;

/**
 * Holder: location gatekeeper
 */
public class LocationHolder extends Location {

    private final int _id;
    private final int _parentId;
    private final String _name;
    private final String _bypass;
    private final String _color;
    private final String _capital;
    private final String _point;
    private final String _type;
    private final int _priceId;
    private final int _priceCount;
    private final String _castleId;

    /**
     * @param set stat set
     */
    public LocationHolder(StatSet set) {
        super(
                set.getInteger("x", 0),
                set.getInteger("y", 0),
                set.getInteger("z", 0)
        );

        _id = set.getInteger("id", 0);
        _parentId = set.getInteger("parentId", 0);
        _name = set.getString("name");
        _bypass = set.getString("bypass", null);
        _color = set.getString("color", null);
        _capital = set.getString("capital", null);
        _point = set.getString("point", null);
        _type = set.getString("type", null);
        _priceId = set.getInteger("priceId", 57);
        _priceCount = set.getInteger("priceCount", 1);
        _castleId = set.getString("castleId", null);
    }

    public int getId() {
        return _id;
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

    public String getCapital() {
        return _capital;
    }

    public String getPoint() {
        return _point;
    }

    public String getType() {
        return _type;
    }

    public int getPriceId() {
        return _priceId;
    }

    public int getPriceCount() {
        return _priceCount;
    }

    public String getCastleId() {
        return _castleId;
    }
}