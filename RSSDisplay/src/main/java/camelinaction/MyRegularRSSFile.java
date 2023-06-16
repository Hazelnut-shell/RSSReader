package camelinaction;

import java.io.File;

// regular file, not directory
public class MyRegularRSSFile extends MyRSSFile {

	public MyRegularRSSFile(File file) {
		super(file);
	}

	@Override
	public void generateSummary() {
		String filename = file.getName();
		if(!filename.endsWith(".txt")) {	// ignore non-txt files 
			summary = "";
			return;
		}
		int index =  filename.lastIndexOf(".");
		// use filename (which is the title of an RSS article) as the summary
		summary = filename.substring(0,index) + "\n";
	}

}
