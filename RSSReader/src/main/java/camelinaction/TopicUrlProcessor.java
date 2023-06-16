package camelinaction;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class TopicUrlProcessor implements Processor {

	private String topic;
	
	private CamelContext context;
	
	public TopicUrlProcessor(String topic, CamelContext context) {
		super();
		this.topic = topic;
		this.context = context;
	}

	// for each rss url, add a route: polling rss feeds from the url to the jms:topic dedicated to a specific topic, e.g. RSS_WORLD, RSS_SPORTS
	public void process(Exchange exchange) throws Exception {
		String url = exchange.getIn().getBody(String.class);
		context.addRoutes(new RSSPollRouteBuilder(url, topic));
	}
		
}
