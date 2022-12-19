package net.sf.l2j.gameserver.data.xml;

import net.sf.l2j.Config;
import net.sf.l2j.commons.data.StatSet;
import net.sf.l2j.commons.data.xml.IXmlReader;
import net.sf.l2j.commons.lang.StringUtil;
import net.sf.l2j.gameserver.enums.TeleportType;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.holder.teleport.TeleportAreaHolder;
import net.sf.l2j.gameserver.model.holder.teleport.TeleportItemHolder;
import net.sf.l2j.gameserver.model.holder.teleport.TeleportLocationHolder;
import net.sf.l2j.gameserver.model.holder.teleport.TeleportMenuHolder;
import net.sf.l2j.gameserver.model.item.kind.Item;
import net.sf.l2j.gameserver.model.location.Location;
import net.sf.l2j.gameserver.taskmanager.GameTimeTaskManager;
import org.w3c.dom.Document;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Global gatekeeper data
 */
public class GlobalGatekeeperData implements IXmlReader {
    private final List<TeleportMenuHolder> _teleports = new ArrayList<>();

    /**
     * Global gatekeeper data
     */
    protected GlobalGatekeeperData() {
        load();
    }

    @Override
    public void load() {
        parseFile("./data/xml/gatekeeper/teleports.xml");
        LOGGER.info("Loaded {} teleports.", _teleports.size());
    }

    @Override
    public void parseDocument(Document doc, Path path) {
        forEach(doc, "list", listNode -> forEach(listNode, "menu", teleportMenu ->
        {
            final StatSet teleportSet = parseAttributes(teleportMenu);
            final TeleportMenuHolder teleport = new TeleportMenuHolder(teleportSet);

            forEach(teleportMenu, "item", teleportItems ->
            {
                final StatSet teleportItemSet = parseAttributes(teleportItems);
                final TeleportItemHolder item = new TeleportItemHolder(teleportItemSet);

                forEach(teleportItems, "area", teleportArea ->
                {
                    final StatSet teleportAreaSet = parseAttributes(teleportArea);
                    final TeleportAreaHolder area = new TeleportAreaHolder(teleportAreaSet);

                    forEach(teleportArea, "loc", teleportLocation ->
                    {
                        final StatSet teleportLocationSet = parseAttributes(teleportLocation);
                        area.getLocations().add(new TeleportLocationHolder(teleportLocationSet));
                    });

                    item.getAreas().add(area);
                });

                teleport.getItems().add(item);
            });

            _teleports.add(teleport.getId(), teleport);
        }));
    }

    /**
     * @param id id
     * @return TeleportMenuHolder
     */
    public TeleportMenuHolder getById(int id) {
        return _teleports.get(id);
    }

    /**
     * @param id         id
     * @param menuItemId menu item id
     * @return menu
     */
    public String getMenu(int id, int menuItemId) {
        TeleportMenuHolder menu = getById(id);
        List<TeleportItemHolder> items = menu.getItems();
        final StringBuilder sb = new StringBuilder();

        int index = 0;
        for (TeleportItemHolder item : items) {
            sb
                    .append("<td width=35 align=\"center\">")
                    .append("<a action=\"bypass -h ")
                    .append(item.getBypass())
                    .append("\">")
                    .append("<font color=")
                    .append(getMenuColor(item, index, menuItemId))
                    .append(">")
                    .append(item.getName())
                    .append("</font>")
                    .append("</a>")
                    .append("</td>")
                    .append("\">");
            index++;
        }

        return sb.toString();
    }

    /**
     * @param menuItem   menu item
     * @param index      index
     * @param menuItemId menu item id
     * @return menu color
     */
    public String getMenuColor(TeleportItemHolder menuItem, int index, int menuItemId) {
        String color = menuItem.getColor();
        if (color == null && index == menuItemId) {
            color = "B09878";
        } else if (color == null) {
            color = "6697FF";
        }
        return color;
    }

    /**
     * @param player     player
     * @param menuId     menu id
     * @param menuItemId menu item id
     * @return list
     */
    public String getList(Player player, int menuId, int menuItemId) {
        TeleportMenuHolder menu = getById(menuId);
        TeleportItemHolder item = menu.getItems().get(menuItemId);
        List<TeleportAreaHolder> areas = item.getAreas();
        final StringBuilder sb = new StringBuilder();
        int index = 0;

        for (TeleportAreaHolder area : areas) {
            final int price = calculatedPriceCount(player, area.getLocations().get(0));
            sb
                    .append("<table width=280 bgcolor=000000>")
                    .append("<tr>")
                    .append("<td width=130>")
                    .append("<a action=\"bypass -h Quest GlobalGatekeeper Locations")
                    .append(" ")
                    .append(menuId)
                    .append(" ")
                    .append(menuItemId)
                    .append(" ")
                    .append(index)
                    .append("\">")
                    .append(area.getName())
                    .append("</a>")
                    .append("</td>")
                    .append("<td width=100>")
                    .append("<font color=A3A0A3>")
                    .append(String.format(Locale.US, "%,d", price))
                    .append("</font>")
                    .append("</td>")
                    .append("<td width=80>")
                    .append("<a action=\"bypass -h Quest GlobalGatekeeper Teleport")
                    .append(" ")
                    .append(menuId)
                    .append(" ")
                    .append(menuItemId)
                    .append(" ")
                    .append(index)
                    .append(" ")
                    .append(0)
                    .append("\" msg=\"811;")
                    .append(area.getCapital())
                    .append("\">")
                    .append("<font color=B09878>")
                    .append(area.getCapital())
                    .append("</font>")
                    .append("</a>")
                    .append("</td>")
                    .append("</tr>")
                    .append("</table>")
                    .append("<img src=L2UI.SquareGray width=280 height=1>");
            index++;
        }
        return sb.toString();
    }

    /**
     * @param player     player
     * @param menuId     menu id
     * @param menuItemId menu item id
     * @param areaId     area id
     * @return locations
     */
    public String getLocations(Player player, int menuId, int menuItemId, int areaId) {
        TeleportMenuHolder menu = getById(menuId);
        TeleportItemHolder item = menu.getItems().get(menuItemId);
        TeleportAreaHolder area = item.getAreas().get(areaId);
        List<TeleportLocationHolder> locations = area.getLocations();

        final StringBuilder sb = new StringBuilder();

        int index = 0;
        for (TeleportLocationHolder location : locations) {
            if (location.getType() == TeleportType.NOBLE && !player.isNoble()) {
                index++;
                continue;
            }
            Item priceItem = ItemData.getInstance().getTemplate(location.getPriceId());
            StringTokenizer tokenizer = new StringTokenizer(priceItem.getName());
            String itemName = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
            int priceCount = calculatedPriceCount(player, location);
            sb
                    .append("<table width=280 bgcolor=000000>")
                    .append("<tr>")
                    .append("<td width=170>")
                    .append("<a action=\"bypass -h Quest GlobalGatekeeper Teleport")
                    .append(" ")
                    .append(menuId)
                    .append(" ")
                    .append(menuItemId)
                    .append(" ")
                    .append(areaId)
                    .append(" ")
                    .append(index)
                    .append("\"")
                    .append("msg=\"811;")
                    .append(location.getName())
                    .append("\">")
                    .append(location.getName())
                    .append("</a>")
                    .append("</td>")
                    .append("<td width=100>")
                    .append("<font color=A3A0A3>")
                    .append(location.getPoint())
                    .append("</td>")
                    .append("<td width=60>")
                    .append(String.format(Locale.US, "%,d", priceCount))
                    .append("</font>")
                    .append("</td>")
                    .append("<td width=50>")
                    .append("<font color=B09878>")
                    .append(itemName)
                    .append("</font>")
                    .append("</td>")
                    .append("</tr>")
                    .append("</table>")
                    .append("<img src=L2UI.SquareGray width=280 height=1>");
            index++;
        }
        if (index < 18) {
            StringUtil.append(sb, "<img height=", 20 * (15 - index), ">");
        }
        return sb.toString();
    }

    /**
     * @param player   player
     * @param teleport teleport
     * @return int
     */
    public int calculatedPriceCount(Player player, TeleportLocationHolder teleport) {
        if (Config.FREE_TELEPORT && Config.FREE_TELEPORT_LVL > 0
                && !player.isSubClassActive() && Config.FREE_TELEPORT_LVL >= player.getStatus().getLevel()
                && teleport.getPriceId() == 57
                && player.getKarma() == 0) {
            return 0;
        }

        int calculatedPrice = teleport.getPriceCount() == 0 ? Config.TELEPORT_BASE_PRICE : teleport.getPriceCount();

        if (teleport.getPriceId() == 57) {
            double distant = player.distance2D(new Location(teleport.getX(), teleport.getY(), teleport.getZ()));
            int distantMul = (int) distant / 1000;
            if (distantMul < 1) {
                distantMul = 1;
            }
            calculatedPrice *= distantMul;
        }

        float levelMul = player.isSubClassActive() ? 80 : player.getStatus().getLevel();
        levelMul = 1 + levelMul / 100;
        calculatedPrice *= levelMul;

        float currentHour = GameTimeTaskManager.getInstance().getGameHour();
        currentHour = 1 + currentHour / 100;
        calculatedPrice *= currentHour;

        if (player.getKarma() > 0) {
            calculatedPrice *= 10;
        }

        return calculatedPrice;
    }

    /**
     * Reload
     */
    public void reload() {
        _teleports.clear();
        load();
    }

    /**
     * @return instance
     */
    public static GlobalGatekeeperData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        protected static final GlobalGatekeeperData INSTANCE = new GlobalGatekeeperData();
    }
}