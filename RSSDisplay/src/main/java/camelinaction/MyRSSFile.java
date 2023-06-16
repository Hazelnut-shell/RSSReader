package camelinaction;

import java.io.File;

// can be a directory, or a regular file
public abstract class MyRSSFile {
	protected File file;
	
	public MyRSSFile(File file) {
		this.file = file;
	}

	protected String summary;
	
	public abstract void generateSummary();

	public File getFile() {
		return file;
	}

	public String getSummary() {
		return summary;
	}
	
}
