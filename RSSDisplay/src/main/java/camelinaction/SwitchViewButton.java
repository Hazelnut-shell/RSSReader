package camelinaction;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class SwitchViewButton extends Button {
	SwitchViewButton() {
		super("Switch to table view");
		
		this.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				Button btnSwitchView = (Button) event.getSource();
				String btnTxt = btnSwitchView.getText();
				
				if (btnTxt.equals("Switch to table view")) {
					btnSwitchView.setText("Switch to list view");
					GUI.setDisplayStrategy(new TableDisplayStrategy());
					GUI.switchToTableView();
				} else {
					btnSwitchView.setText("Switch to table view");
					GUI.setDisplayStrategy(new ListDisplayStrategy());
					GUI.switchToListView();
				}
			}
		});
	}
}
