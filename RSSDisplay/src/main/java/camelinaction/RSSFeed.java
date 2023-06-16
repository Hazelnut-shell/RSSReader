package camelinaction;

public class RSSFeed {
	private String title;
	private String link;
	private String desc;
	private String readingTime;

	public RSSFeed(String title, String link, String desc, String readingTime) {
		super();
		this.title = title;
		this.link = link;
		this.desc = desc;
		this.readingTime = readingTime;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getDesc() {
		return desc;
	}

	public String getReadingTime() {
		return readingTime;
	}

	@Override
	public String toString() {
		return "title: " + title + "\nlink: " + link + "\ndescription: " + desc + "\nestimated reading time: " + readingTime;
	}

}
