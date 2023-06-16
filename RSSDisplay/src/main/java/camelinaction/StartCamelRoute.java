package camelinaction;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import javafx.application.Platform;

public class StartCamelRoute implements Command {

	public String exec() {
		CamelContext context = new DefaultCamelContext();

		try {
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					from("file:../RSSReader/data/outbox?noop=true&recursive=true")
					.filter(header("CamelFileName").endsWith(".txt"))
					
					.process(new Processor() {

						public void process(Exchange exchange) throws Exception {
							// construct an RSSFeed object from the .txt file 
							
							final String body = exchange.getIn().getBody(String.class);
							String[] lines = body.split("\n");
							
							for (int i = 0; i < lines.length; i++) {
								int idx = lines[i].indexOf(':');
								lines[i] = lines[i].substring(idx + 1).trim();
							}

							final RSSFeed feed = new RSSFeed(lines[0], lines[1], lines[2], lines[3]);
							
							// add the RSSFeed to items
							Platform.runLater(new Runnable() {
								public void run() {
									GUI.getItems().add(feed);
								}
							});
						}

					})
					.log("............${body}");
				}

			});
			
			context.start();
			Thread.sleep(1000000);

			// stop the CamelContext
			context.stop();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	
		
	}

}
