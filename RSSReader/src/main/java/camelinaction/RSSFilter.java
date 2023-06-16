package camelinaction;

import java.util.*;

import org.apache.camel.Body;
import org.apache.camel.Header;

public class RSSFilter {
	
	private List<String> filteredWords = new ArrayList<String>();
	private List<String> subscribedSources = new ArrayList<String>();
	
	public RSSFilter(List<String> filteredWords,List<String> sources ) {
		super();
		this.filteredWords = filteredWords;
		this.subscribedSources = sources;
	}

	public boolean filterWords(@Body String body) {
		if(filteredWords == null) return true;
		
		for(String word: filteredWords) {
			if(body.contains(word)) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean filterSources(@Header("source") String msgSource) {
		if(subscribedSources == null) return false;
		
		return subscribedSources.contains(msgSource);
		
	}
	
}
