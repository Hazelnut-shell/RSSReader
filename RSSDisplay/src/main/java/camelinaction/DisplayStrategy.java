package camelinaction;
import javafx.scene.control.*;

// RSS feeds can be displayed using different strategies, such as list view, table view
public abstract class DisplayStrategy {
	public abstract Control generateRSSView();
}
