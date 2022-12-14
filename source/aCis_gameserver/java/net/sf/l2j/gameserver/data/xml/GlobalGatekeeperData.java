package net.sf.l2j.gameserver.data.xml;

import net.sf.l2j.commons.data.StatSet;
import net.sf.l2j.commons.data.xml.IXmlReader;
import net.sf.l2j.gameserver.model.holder.teleport.LocationHolder;
import net.sf.l2j.gameserver.model.holder.teleport.TeleportHolder;
import org.w3c.dom.Document;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class GlobalGatekeeperData implements IXmlReader {
    private final List<TeleportHolder> _teleports = new ArrayList<>();

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
        forEach(doc, "list", listNode -> forEach(listNode, "teleport", teleportNode ->
        {
            final StatSet teleportSet = parseAttributes(teleportNode);
            final TeleportHolder teleport = new TeleportHolder(teleportSet);
            forEach(teleportNode, "loc", locationNode ->
            {
                final StatSet set = parseAttributes(locationNode);
                teleport.getLocations().add(new LocationHolder(set));
            });
            _teleports.add(teleport.getId(), teleport);
        }));
    }

    public TeleportHolder getById(int id) {
        return _teleports.get(id);
    }

    public String getMenu(int id, int menuId) {
        TeleportHolder teleport = getById(id);
        TeleportHolder current = getById(menuId);
        List<LocationHolder> locations = teleport.getLocations();
        final StringBuilder sb = new StringBuilder();

        int index = 0;
        for (LocationHolder location : locations) {
            sb
                    .append("<td width=35 align=\"center\">")
                    .append("<a action=\"bypass -h ")
                    .append(location.getBypass())
                    .append("\">")
                    .append("<font color=")
                    .append(getMenuColor(location, current.getMenuId(), index))
                    .append(">")
                    .append(location.getName())
                    .append("</font>")
                    .append("</a>")
                    .append("</td>")
                    .append("\">");
            index++;
        }

        return sb.toString();
    }

    public String getMenuColor(LocationHolder location, int listId, int index) {
        String color = location.getColor();
        if (color == null && index == listId) {
            color = "B09878";
        } else if (color == null) {
            color = "6697FF";
        }
        return color;
    }

    public String getList(int id) {
        TeleportHolder teleport = GlobalGatekeeperData.getInstance().getById(id);
        List<LocationHolder> locations = teleport.getLocations();
        final StringBuilder sb = new StringBuilder();
        for (LocationHolder location : locations) {
            sb
                    .append("<table width=280 bgcolor=000000>")
                    .append("<tr>")
                    .append("<td width=130>")
                    .append("<a action=\"bypass -h Quest GlobalGatekeeper ")
                    .append(location.getBypass())
                    .append(" ")
                    .append(teleport.getMenuId())
                    .append(" ")
                    .append(location.getId())
                    .append("\">")
                    .append(location.getName())
                    .append("</a>")
                    .append("</td>")
                    .append("<td width=100>")
                    .append("<font color=A3A0A3>")
                    .append(teleport.getSize())
                    .append("</font>")
                    .append("</td>")
                    .append("<td width=80>")
                    .append("<a action=\"Quest GlobalGatekeeper Teleport 1 1\">")
                    .append("<font color=B09878>")
                    .append(location.getCapital())
                    .append("</font>")
                    .append("</a>")
                    .append("</td>")
                    .append("</tr>")
                    .append("</table>")
                    .append("<img src=L2UI.SquareGray width=280 height=1>");
        }
        return sb.toString();
    }

    public String getLocations(int id) {
        TeleportHolder teleport = GlobalGatekeeperData.getInstance().getById(id);
        List<LocationHolder> locations = teleport.getLocations();
        final StringBuilder sb = new StringBuilder();
        int locationId = 0;

        for (LocationHolder location : locations) {
            StringTokenizer tokenizer = new StringTokenizer(ItemData.getInstance().getTemplate(location.getPriceId()).getName());
            String itemName = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
            int priceCount = 0;

            sb
                    .append("<table width=280 bgcolor=000000>")
                    .append("<tr>")
                    .append("<td width=170>")
                    .append("<a action=\"bypass -h Quest GlobalGatekeeper ")
                    .append(location.getBypass())
                    .append(" ")
                    .append(teleport.getId())
                    .append(" ")
                    .append(locationId)
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
                    .append("<td width=55>")
                    .append(priceCount)
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

            locationId++;
        }
        return sb.toString();
    }

    public static GlobalGatekeeperData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        protected static final GlobalGatekeeperData INSTANCE = new GlobalGatekeeperData();
    }
}