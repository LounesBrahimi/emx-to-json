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

    public static void main(String[] args) throws Exception {
        File emxFile = new File("modele.emx");

        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(emxFile);

        doc.getDocumentElement().normalize();

        Map<String, Object> jsonStructure = new LinkedHashMap<>();
        traverse(doc.getDocumentElement(), jsonStructure);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(new FileWriter("modele.json"), jsonStructure);

        System.out.println("Conversion terminée → modele.json");
    }

    private static void traverse(Node node, Map<String, Object> jsonMap) {
        if (node.hasAttributes()) {
            var attributes = node.getAttributes();
            Map<String, String> attrMap = new LinkedHashMap<>();
            for (int i = 0; i < attributes.getLength(); i++) {
                var attr = attributes.item(i);
                attrMap.put(attr.getNodeName(), attr.getNodeValue());
            }
            jsonMap.put(node.getNodeName(), attrMap);
        }

        var children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            var child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                Map<String, Object> childMap = new LinkedHashMap<>();
                traverse(child, childMap);
                jsonMap.put(child.getNodeName(), childMap);
            }
        }
    }
}
