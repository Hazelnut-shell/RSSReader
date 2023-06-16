package camelinaction;

import java.io.IOException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.xml.XPathBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ContentEnricher implements Processor {
	// readingSpeed is from UserConfig
	private int readingSpeed;

	public ContentEnricher(int readingSpeed) {
		super();
		this.readingSpeed = readingSpeed;
	}

	public void process(Exchange exchange) throws Exception {
		String xmlStr = exchange.getIn().getBody(String.class);
		
		// use xpath to get the link from the XML String
		XPathBuilder xpath = XPathBuilder.xpath("/rss/channel/item/link/text()");
		String url = xpath.evaluate(exchange.getContext(), xmlStr, String.class);
		
		Document doc = null;
		int estTime = -1;
		
		try {	// use Jsoup to parse the HTML
			doc = Jsoup.connect(url).get();
		} catch (IOException e) { 	// if some error occurs, set estReadingTime to -1
			// TODO Auto-generated catch block
			e.printStackTrace();
			exchange.getIn().setHeader("estReadingTime", estTime);
			return;
		}

		// Select all <p> elements and count the words in all the <p> elements
		Elements paragraphs = doc.select("p"); 
		int numWords = 0;
		StringBuilder textContent = new StringBuilder();

		for (Element paragraph : paragraphs) {
			String paragraphText = paragraph.text();
			numWords += paragraphText.split("\\s+").length;
//			    textContent.append(paragraphText).append("\n\n");
		}

		System.out.println(numWords);
		estTime = numWords/readingSpeed;  
		
		// set estReadingTime header, for future use
		exchange.getIn().setHeader("estReadingTime", estTime);
		return;
	}
}
