package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
import model.services.RelacaoCliente;

public class ControladorTelaPrincipal implements Initializable {
	
	@FXML
	private MenuItem menuItemClientes;
	@FXML
	private MenuItem menuItemPatrimonios;
	@FXML
	private MenuItem menuItemSobre;
	
	@FXML
	public void onMenuItemClientesAction() {
		loadView("/gui/ListaClientes.fxml", (ControladorListaPatrimonios controller) -> {controller.setRelacaoCliente (new RelacaoCliente());
		controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuItemPatrimoniosAction() {
		System.out.println("TesteP");
		
	}
	
	
	//Tela about carrega função nula. O redirecionamento pro git estava com problemas
	//removi, dispensável. Luan.
	
	@FXML
	public void onMenuItemClientesSobre() {
		loadView("/gui/Sobre.fxml", e -> {});
		
	}



	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
	private <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		
		try {
			
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVBox = loader.load();
		
		Scene mainScene = Main.getMainScene();
		VBox mainVBox = (VBox) ((ScrollPane)mainScene.getRoot()).getContent();
		
		Node mainMenu = mainVBox.getChildren().get(0);
		mainVBox.getChildren().clear();
		mainVBox.getChildren().add(mainMenu);
		mainVBox.getChildren().addAll(newVBox.getChildren());
		
		//Inicializa a função como argumento da lista. Precisa verificar;
		//Luan.
		
		T controller = loader.getController();
		initializingAction.accept(controller);
		
		
		}
		catch (IOException e) {
			Alerts.showAlert("Exceção de IO", "Erro ao abrir função", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	

}
