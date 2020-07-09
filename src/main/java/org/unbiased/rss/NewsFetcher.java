package org.unbiased.rss;

import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class NewsFetcher {


    public static String getXML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    public static List<String> parseXML(String xmlToRead) throws ParserConfigurationException, IOException, SAXException     {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlToRead)));
            NodeList elems = doc.getElementsByTagName("link");
            List<String> data = new ArrayList<String>();
            for (int i=0; i<elems.getLength();i++){
                Node node = elems.item(i);
                data.add(node.getTextContent());
            }
            return data;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static void storeLinksWithMeta(List<String> links) throws IOException {
        //TODO Load pattern file for each source and apply to link to extract metadata

        JSONObject sampleObject = new JSONObject();
        for (String link : links) {
            sampleObject.put("source", "CNN");
            sampleObject.put("link", link);
        }
        Files.write(Paths.get("temp.json"), sampleObject.toJSONString().getBytes());
    }
}
