package camelinaction;
import java.io.File;
import java.util.*;

public class MyRSSFolder extends MyRSSFile {
	private List<MyRSSFile> myfiles = new ArrayList<MyRSSFile>();
	

	public MyRSSFolder(File file) {
		super(file);
		
		File[] childern = file.listFiles();
		for(File child: childern) {
			if(child.isFile()) {
				myfiles.add(new MyRegularRSSFile(child));
			} else { // is Directory
				myfiles.add(new MyRSSFolder(child));
			}
			
		}
	}


	@Override
	public void generateSummary() {
		if(summary != null) {
			return;
		}
		
		StringBuilder stb = new StringBuilder();
		
		for(MyRSSFile myfile: myfiles) {
			myfile.generateSummary();
			stb.append(myfile.getSummary());
		}
		
		summary = stb.toString();
	}

}
