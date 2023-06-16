package camelinaction;

import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ListDisplayStrategy extends DisplayStrategy {
	
	@Override
	public Control generateRSSView() {
		ListView<RSSFeed> listView = new ListView<RSSFeed>();
		listView.setItems(GUI.getItems());

		setTextAndColor(listView);
		
		listView.getSelectionModel().selectedItemProperty().addListener(new ItemSelectionListener());
		
		return listView;
	}
	
	public void setTextAndColor(ListView<RSSFeed> listView) {
		listView.setCellFactory(new Callback<ListView<RSSFeed>, ListCell<RSSFeed>>() {

			public ListCell<RSSFeed> call(ListView<RSSFeed> param) {
				return new ListCell<RSSFeed>() {
					@Override
					protected void updateItem(RSSFeed item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							// set text to the string representation of the RSSFeed 
							setText(item.toString());

							// set alternating colors based on the index
							int index = getIndex();
							if (index % 2 == 0) {
//                            	setTextFill(Color.CHOCOLATE);
								setStyle("-fx-background-color: #e4f2ea;");
							} else {
								setStyle("-fx-background-color: #FFFFFF;");
							}
						} else {
							setText(null);
							setGraphic(null);
						}
					}
				};
			}
		});
		
		
	}

}
