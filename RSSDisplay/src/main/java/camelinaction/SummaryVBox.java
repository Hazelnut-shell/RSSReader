package camelinaction;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SummaryVBox {
	private VBox vbox;
	private TextField textField;
	private Label label;
	private Button button;

	public SummaryVBox() {
		setLabel();
		setTextField();
		setButton();

		vbox = new VBox(label, textField, button);
		vbox.setSpacing(7);
	}

	private void setLabel() {
		label = new Label("Enter the path of the RSS feed folder (in the RSSReader project) for which you want to generate a feed summary. "
				+ "A feed summary is basically a collection of all the titles of RSS articles in the specified folder. "
				+ "The default value given is an example, which will generate a summary of all files under the '../RSSReader/data/outbox' folder.");
		label.setWrapText(true);
	}

	private void setTextField() {
		textField = new TextField();
		textField.setText("../RSSReader/data/outbox"); 
		textField.setPrefWidth(100);
		textField.setMaxWidth(400);
	}

	private void setButton() {
		button = new Button("Generate Summary");
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			// pop up a stage to show the summary
			public void handle(ActionEvent event) {
				String path = textField.getText();

				Stage popupStage = new Stage();
				popupStage.setTitle("Popup Window");

				Text text = new Text(new GenerateSummary(path).exec());

				StackPane popupRoot = new StackPane(text);
				Scene popupScene = new Scene(popupRoot, 200, 150);

				Image icon = new Image("/rssicon.png");
				popupStage.getIcons().add(icon);
				popupStage.setTitle("RSS Summary");
				popupStage.setScene(popupScene);
				popupStage.show();

				System.out.println(path);
			}
		});
	}

	public VBox getVbox() {
		return vbox;
	}

	public void setVbox(VBox vbox) {
		this.vbox = vbox;
	}

}
