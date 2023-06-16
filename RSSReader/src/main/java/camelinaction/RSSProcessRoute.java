package camelinaction;

import org.apache.camel.builder.RouteBuilder;

public class RSSProcessRoute extends RouteBuilder{
	private UserConfig config;
	
	public RSSProcessRoute(UserConfig config) {
		super();
		this.config = config;
	}

	@Override
	public void configure() throws Exception {
		// configure an RSSFilter based on UserConfig
		RSSFilter rssFilter = new RSSFilter(config.getFilteredWords(), config.getSubscribedSources());
		
		from("jms:queue:RSS_ALL")
		.convertBodyTo(String.class)
		
		// filter out messages that contain words the user doesn't want to see
		.filter().method(rssFilter, "filterWords")
		
		// filter out messages that comes from sources the user doesn't want to see
		.filter().method(rssFilter, "filterSources")
		
		// enrich the message, by adding estimated reading time 
		.process(new ContentEnricher(config.getReadingSpeed()))
		
		// transform body from xml string to a formatted string, which will be stored in .txt file
		.process(new TXTFeedFormatter())
		
		.to("log:mylog?showBodyType=true&multiline=true")
		
		// organize feeds by their topics, and sources. Each feed information in a .txt file
		.to("file:data/outbox/?fileName=${header.topic}/${header.source}/${header.title}.txt");
		
	}
}
