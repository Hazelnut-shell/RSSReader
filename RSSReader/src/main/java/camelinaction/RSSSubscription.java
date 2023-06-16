package camelinaction;

import org.apache.camel.builder.RouteBuilder;
import java.util.UUID;

public class RSSSubscription extends RouteBuilder {
	private String topic;

	public RSSSubscription(String topic) {
		super();
		this.topic = topic;
	}

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		
		from("jms:topic:RSS_" + topic)
		.setHeader("topic", constant(topic))
		.to("jms:queue:RSS_ALL");
	}

}
