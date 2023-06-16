package camelinaction;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.xml.XPathBuilder;

public class TXTFeedFormatter implements Processor {

	public void process(Exchange exchange) throws Exception {
		String title, link, description;
		Integer estReadingTime = (Integer) exchange.getIn().getHeader("estReadingTime");
		
		String xmlStr = exchange.getIn().getBody(String.class);
		
		// use xpath to extract info from xml
		XPathBuilder xpath = XPathBuilder.xpath("/rss/channel/item/title/text()");
	    title = xpath.evaluate(exchange.getContext(), xmlStr, String.class);
	    
	    xpath = XPathBuilder.xpath("/rss/channel/item/link/text()");
	    link = xpath.evaluate(exchange.getContext(), xmlStr, String.class);
	    
	    xpath = XPathBuilder.xpath("/rss/channel/item/description/text()");
	    description = xpath.evaluate(exchange.getContext(), xmlStr, String.class);
	    
	    StringBuilder stb = new StringBuilder();
	    stb.append("title: ").append(title).append("\n");
	    stb.append("link: ").append(link).append("\n");
	    stb.append("description: ").append(description).append("\n");
	    
	    stb.append("estimated reading time: ");
	    if(estReadingTime == -1) {
	    	stb.append("unknown");
	    } else {
	    	stb.append(estReadingTime).append(" min");
	    }
	    
	    exchange.getIn().setHeader("title", title);
	    
	    // set body to the string, which will be stored in a local .txt file later
	    exchange.getIn().setBody(stb.toString());
	}

}
