package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;

public class ControladorListaPatrimonios implements Initializable {
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML 
	private Button btNovo;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("ClickTeste");
	}
	
	
	@Override
	public void initialize (URL url, ResourceBundle rb) {
		initializeNodes();
		
	}


	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		
		//Call da superclasse stage. Redimensiona o grid de dados para o tamanho da janela
		//Guilherme, aqui que precisamos validar o que falei com vc no whats
		
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
		
	}
	
	}

	


