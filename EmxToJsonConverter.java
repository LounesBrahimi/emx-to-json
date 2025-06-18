import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.TreeWalker;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class EmxToJsonConverter {

    private static void traverse(Node node, Map<String, Object> jsonMap) {
    if (node.hasAttributes()) {
        var attributes = node.getAttributes();
        Map<String, String> attrMap = new LinkedHashMap<>();
        for (int i = 0; i < attributes.getLength(); i++) {
            var attr = attributes.item(i);
            attrMap.put(attr.getNodeName(), attr.getNodeValue());
        }

        // Si ce noeud a déjà été rencontré, on crée ou complète une liste
        if (jsonMap.containsKey(node.getNodeName())) {
            Object existing = jsonMap.get(node.getNodeName());
            if (existing instanceof List) {
                ((List<Object>) existing).add(attrMap);
            } else {
                List<Object> newList = new ArrayList<>();
                newList.add(existing);
                newList.add(attrMap);
                jsonMap.put(node.getNodeName(), newList);
            }
        } else {
            jsonMap.put(node.getNodeName(), attrMap);
        }
    }

    var children = node.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
        var child = children.item(i);
        if (child.getNodeType() == Node.ELEMENT_NODE) {
            Map<String, Object> childMap = new LinkedHashMap<>();
            traverse(child, childMap);

            if (jsonMap.containsKey(child.getNodeName())) {
                Object existing = jsonMap.get(child.getNodeName());
                if (existing instanceof List) {
                    ((List<Object>) existing).add(childMap.get(child.getNodeName()));
                } else {
                    List<Object> newList = new ArrayList<>();
                    newList.add(existing);
                    newList.add(childMap.get(child.getNodeName()));
                    jsonMap.put(child.getNodeName(), newList);
                }
            } else {
                jsonMap.putAll(childMap);
            }
        }
    }
}

}
