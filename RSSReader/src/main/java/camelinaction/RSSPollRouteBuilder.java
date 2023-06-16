package camelinaction;

import org.apache.camel.builder.RouteBuilder;

public class RSSPollRouteBuilder extends RouteBuilder{
	private String RSSURL;
	private String topic;
	private String source; 
	

	public RSSPollRouteBuilder(String RSSURL, String topic) {
		super();
		this.RSSURL = RSSURL;
		this.topic = topic;
		getSourceFromURL();
	}
	
	private void getSourceFromURL(){
		String urlUpper = RSSURL.toLowerCase();
		if(urlUpper.contains("cnn")) {
			source = "cnn";
		} else if(urlUpper.contains("nytimes")) {
			source = "nytimes";
		} else if(urlUpper.contains("foxnews")) {
			source = "foxnews";
		} else if(urlUpper.contains("globalnews")) {
			source = "globalnews";
		} else if(urlUpper.contains("cbsnews")) {
			source = "cbsnews";
		} else {
			source = "other";
		}
		
	}
	

	@Override
	public void configure() throws Exception {
		// poll RSS feeds from the RSSURl to corresponding topic
		from("rss:" + RSSURL+"?consumer.delay=100")
		.marshal().rss()
		.setHeader("source", constant(source))	// set header "source"
		.to("jms:topic:RSS_"+ topic);   // 
		
	}
	

}
