package camelinaction;

import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.control.TableRow;

public class TableDisplayStrategy extends DisplayStrategy {

	@Override
	public Control generateRSSView() {
		TableView<RSSFeed> tableView = new TableView<RSSFeed>();
		tableView.setItems(GUI.getItems());

		TableColumn<RSSFeed, String> titleColumn = new TableColumn<RSSFeed, String>("Title");
		titleColumn.setCellValueFactory(new PropertyValueFactory<RSSFeed, String>("title"));

		TableColumn<RSSFeed, String> linkColumn = new TableColumn<RSSFeed, String>("Link");
		linkColumn.setCellValueFactory(new PropertyValueFactory<RSSFeed, String>("link"));

		TableColumn<RSSFeed, String> descColumn = new TableColumn<RSSFeed, String>("Description");
		descColumn.setCellValueFactory(new PropertyValueFactory<RSSFeed, String>("desc"));

		TableColumn<RSSFeed, String> timeColumn = new TableColumn<RSSFeed, String>("Estimated Reading Time");
		timeColumn.setCellValueFactory(new PropertyValueFactory<RSSFeed, String>("readingTime"));

		tableView.getColumns().addAll(titleColumn, linkColumn, descColumn, timeColumn);
		
		setColor(tableView);

		// pop a stage, showing the html content from the link, when an item in the tableView is selected
		tableView.getSelectionModel().selectedItemProperty().addListener(new ItemSelectionListener());

		return tableView;
	}

	// set alternating colors for rows
	private void setColor(TableView<RSSFeed> tableView) {
		tableView.setRowFactory(new Callback<TableView<RSSFeed>, TableRow<RSSFeed>>() {

			public TableRow<RSSFeed> call(TableView<RSSFeed> tableView) {
				TableRow<RSSFeed> row = new TableRow<RSSFeed>() {
					@Override
					protected void updateItem(RSSFeed item, boolean empty) {
						super.updateItem(item, empty);
						if (getIndex() % 2 == 0) {
							setStyle("-fx-background-color: #e4f2ea;");
						} else {
							setStyle("-fx-background-color: #FFFFFF;");
						}
					}
				};
				return row;
			}
		});

	}

}
