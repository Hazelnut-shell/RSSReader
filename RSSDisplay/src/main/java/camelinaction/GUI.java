/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package camelinaction;

import javafx.scene.control.TableView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class GUI extends Application {

	private final static ObservableList<RSSFeed> items = FXCollections.observableArrayList();	// RSSFeed items to display

	private static DisplayStrategy displayStrategy;		// list view display or table view display
	private static ListView listView;
	private static TableView tableView;
	private static VBox root;

	@Override
	public void start(Stage stage) throws Exception {
		
		// The default view is the list view
		displayStrategy = new ListDisplayStrategy();
		listView = (ListView)displayStrategy.generateRSSView();
		
		
		root = new VBox(new SummaryVBox().getVbox(),new Separator(), new SwitchViewButton(), listView);
		root.setSpacing(15);
	
		Scene scene = new Scene(root, 800, 600);
		
		// set stage properties
		Image icon = new Image("/rssicon.png");		//  The image is located in default package of the classpath
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.setTitle("RSS Reader");
		stage.show();

		// another thread for the camel route to read rss .txt files from RSSReader/data/outbox
		Thread camelThread = new Thread(new Runnable() {

			public void run() {
				try {
					new StartCamelRoute().exec();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		camelThread.setDaemon(true);
		camelThread.start();
		
	}

	public static void main(String[] args) throws Exception {
		launch();
	}

	public static void switchToListView() {
		if(listView == null) {
			listView = (ListView) displayStrategy.generateRSSView();
		}
		root.getChildren().remove(tableView);
		root.getChildren().add(listView);
	}
	
	public static void switchToTableView() {
		if(tableView == null) {
			tableView = (TableView) displayStrategy.generateRSSView();
		}
		root.getChildren().remove(listView);
		root.getChildren().add(tableView);
		
	}
	
	
	// getters and setters
	
	public static ObservableList<RSSFeed> getItems() {
		return items;
	}

	public static DisplayStrategy getDisplayStrategy() {
		return displayStrategy;
	}

	public static void setDisplayStrategy(DisplayStrategy displayStrategy) {
		GUI.displayStrategy = displayStrategy;
	}

	public static ListView getListView() {
		return listView;
	}

	public static TableView getTableView() {
		return tableView;
	}

	public static void setListView(ListView listView) {
		GUI.listView = listView;
	}

	public static void setTableView(TableView tableView) {
		GUI.tableView = tableView;
	}
}