package camelinaction;

import java.util.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

// corresponding to data/inbox/config.xml file
@XmlRootElement(name = "userConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserConfig {
	@XmlElementWrapper(name = "topicsSubscribed")
	@XmlElement(name = "topic")
	private List<String> subscribedTopics;

	@XmlElementWrapper(name = "sourcesInterested")
	@XmlElement(name = "source")
	private List<String> subscribedSources;

	@XmlElement(name = "readSpeed")
	private int readingSpeed;

	@XmlElementWrapper(name = "filterOutWords")
	@XmlElement(name = "word")
	private List<String> filteredWords;

	public List<String> getSubscribedTopics() {
		return subscribedTopics;
	}

	public List<String> getSubscribedSources() {
		return subscribedSources;
	}

	public int getReadingSpeed() {
		return readingSpeed;
	}

	public List<String> getFilteredWords() {
		return filteredWords;
	}

}
