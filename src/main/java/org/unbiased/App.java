package org.unbiased;

/**
 *
 *
 */
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

import static org.unbiased.rss.NewsFetcher.*;

public class App 
{
    public static void main(String[] args) throws Exception
    {
        String elem = getXML("https://www.cnbc.com/id/100727362/device/rss/rss.html");
        System.out.println("HERRRE "+elem);
        List<String> data = parseXML(elem);
        if(data != null){
            storeLinksWithMeta(data);
        }else{
            System.out.println("kik");
        }

    }
}
