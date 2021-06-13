package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ControladorTelaPrincipal implements Initializable {
	
	@FXML
	private MenuItem menuItemClientes;
	@FXML
	private MenuItem menuItemPatrimonios;
	@FXML
	private MenuItem menuItemSobre;
	
	@FXML
	public void onMenuItemClientesAction() {
		loadView("/gui/ListaClientes.fxml");
		
	}
	
	@FXML
	public void onMenuItemPatrimoniosAction() {
		System.out.println("TesteP");
		
	}
	
	@FXML
	public void onMenuItemClientesSobre() {
		loadView("/gui/Sobre.fxml");
		
	}



	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
	}
	
	private void loadView(String absoluteName) {
		
		try {
			
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVBox = loader.load();
		
		Scene mainScene = Main.getMainScene();
		VBox mainVBox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();
		
		Node mainMenu = mainVBox.getChildren().get(0);
		mainVBox.getChildren().clear();
		mainVBox.getChildren().add(mainMenu);
		mainVBox.getChildren().addAll(newVBox.getChildren());
		
		
		}
		catch (IOException e) {
			Alerts.showAlert("Exceção de IO", "Erro ao abrir função", e.getMessage(), AlertType.ERROR);
		}
		
	}

}
