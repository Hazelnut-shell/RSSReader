/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package camelinaction;

import java.util.*;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;

public class RSSMain {
	public static UserConfig userConfig;
	
	public static void main(String args[]) throws Exception {
		
		final CamelContext context = new DefaultCamelContext(); 
		
		// connect to ActiveMQ JMS broker listening on localhost on port 61616
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		// load configuration made by user
		LoadUserConfig loadUserConfig =  new LoadUserConfig();
		userConfig = loadUserConfig.load();
		
		// add our route to the CamelContext
		context.addRoutes(new RouteBuilder() {
			public void configure() {
				
				from("file:data/inbox?fileName=sources.txt&noop=true")
				.split().tokenize("\n") // splitter pattern
				.process(new Processor() {   // trim whitespace. Otherwise, there is a newline character at the end, which makes '.*' at the end of the regex not match
					public void process(Exchange exchange) throws Exception {
						// TODO Auto-generated method stub
						String s = exchange.getIn().getBody(String.class);
						exchange.getIn().setBody(s.trim());
					}
					
				})
				.filter(body().isNotEqualTo("")) 		// filter out empty lines
				.choice()								// content-based router
					.when().simple("${body} regex '(?i).*(tech).*'") 	// seda:tech contains urls related to tech feeds
					.to("seda:tech")
					.when().simple("${body} regex '(?i).*(sport).*'")
					.to("seda:sports")
					.when().simple("${body} regex '(?i).*(health).*'")
					.to("seda:health")
					.when().simple("${body} regex '(?i).*(world).*'")
					.to("seda:world")
					.when().simple("${body} regex '(?i).*(science).*'")
					.to("seda:science")
					.otherwise()
					.to("seda:other")
				;
				
				// url -> jms topic
				from("seda:other").process(new TopicUrlProcessor("OTHER", context))
				.to("log:mylog");
				
				from("seda:sports").process(new TopicUrlProcessor("SPORTS", context))
				.to("log:mylog");
				
				from("seda:world").process(new TopicUrlProcessor("WORLD", context))
				.to("log:mylog");
				
				from("seda:health").process(new TopicUrlProcessor("HEALTH", context))
				.to("log:mylog");
				
				from("seda:tech").process(new TopicUrlProcessor("TECH", context))
				.to("log:mylog");
				
				from("seda:science").process(new TopicUrlProcessor("SCIENCE", context))
				.to("log:mylog");
			}

		});
		
		// various jms topics -> jms:queue:RSS_ALL
		// subscribe to topics users are interested in. RSS feeds of subscribed topics go to jms:queue:RSS_ALL
		subscribeRSSTopics(context);
		
		// -> jms:queue:RSS_ALL -> data/outbox/...
		// consume and process RSS feeds from jms:queue:RSS_ALL. go to data/outbox/
		context.addRoutes(new RSSProcessRoute(userConfig));

		
		// start the route and let it do its work
		context.start();
		Thread.sleep(140000);

		// stop the CamelContext
		context.stop();
		
	}
	
	public static void subscribeRSSTopics(CamelContext context) throws Exception {
		List<String> topics = userConfig.getSubscribedTopics();
		for(String topic: topics) {
			context.addRoutes(new RSSSubscription(topic.toUpperCase()));
		}

	}
}
