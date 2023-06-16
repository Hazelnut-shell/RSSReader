package camelinaction;

import java.io.File;

public class GenerateSummary implements Command {
	private String folderPath;

	public GenerateSummary(String folderPath) {
		super();
		this.folderPath = folderPath;
	}

	// generate a feed summary (a collection of RSS feed item titles) from the given folder. 
	public String exec() {
		File folder = new File(folderPath);
		
		MyRSSFolder myFolder = new MyRSSFolder(folder);
		
		myFolder.generateSummary();
		String summary = myFolder.getSummary();
		System.out.println(summary);
		return summary;
	}

}
