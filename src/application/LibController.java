package application;

import java.io.IOException;

import javafx.fxml.FXML;


public class LibController {
	
	
	@FXML
	private void handleLibrary() throws IOException{
		Main.showMainLibrary();
	}
	
	@FXML
	private void handleUsers() throws IOException{
		Main.showMainEdit();
	}
}
