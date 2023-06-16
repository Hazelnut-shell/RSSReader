package camelinaction;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.web.WebView;

public class ItemSelectionListener implements ChangeListener<RSSFeed> {

	// when an RSS item get selected, pop up a window, loading the html of the RSS feed
	public void changed(ObservableValue<? extends RSSFeed> observable, RSSFeed oldValue, RSSFeed newValue) {
		if (newValue != null) {
			// get the link of the RSSFeed item
			String link = newValue.getLink();
			System.out.println(" Link: " + link);

			// load the HTML link
			WebView webView = new WebView();
			webView.getEngine().load(link);
			
			StackPane popupRoot = new StackPane(webView);
			Scene popupScene = new Scene(popupRoot, 200, 150);
			
			// pop up a new stage
			Stage popupStage = new Stage();
			Image icon = new Image("/rssicon.png");
			popupStage.getIcons().add(icon);
			popupStage.setTitle("RSS Feed");
			popupStage.setScene(popupScene);
			popupStage.show();
		}

	}
}
