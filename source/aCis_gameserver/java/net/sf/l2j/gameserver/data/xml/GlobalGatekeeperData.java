package net.sf.l2j.gameserver.data.xml;

import net.sf.l2j.commons.data.StatSet;
import net.sf.l2j.commons.data.xml.IXmlReader;
import net.sf.l2j.gameserver.model.holder.teleport.LocationHolder;
import net.sf.l2j.gameserver.model.holder.teleport.TeleportHolder;
import net.sf.l2j.gameserver.model.holder.teleport.TeleportMenuHolder;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GlobalGatekeeperData implements IXmlReader {
    private final List<TeleportHolder> _teleports = new ArrayList<>();
    private final List<TeleportMenuHolder> _menu = new ArrayList<>();

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
            final NamedNodeMap attrs = teleportNode.getAttributes();
            final int id = Integer.parseInt(attrs.getNamedItem("id").getNodeValue());
            final String type = parseString(attrs, "type");
            final TeleportHolder teleport = new TeleportHolder(id);
            forEach(teleportNode, "loc", locationNode ->
            {
                final StatSet set = parseAttributes(locationNode);
                if (type.equals("menu")) {
                    _menu.add(new TeleportMenuHolder(set));

                } else {
                    teleport.getLocations().add(new LocationHolder(set));
                }
            });
            if (type.equals("list")) {
                _teleports.add(id, teleport);
            }
        }));
    }

    public TeleportHolder getById(int id) {
        return _teleports.get(id);
    }

    public List<TeleportMenuHolder> getMenu() {
        return _menu;
    }


    public static GlobalGatekeeperData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        protected static final GlobalGatekeeperData INSTANCE = new GlobalGatekeeperData();
    }
}