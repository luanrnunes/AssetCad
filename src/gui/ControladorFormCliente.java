package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class ControladorFormCliente implements Initializable {

	private Department entidade;
	
	@FXML
	private TextField txtID;
	
	@FXML
	private TextField txtCliente;
	
	@FXML
	private Label labelErroCliente;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCancel;
	
	public void setDepartment(Department entidade) {
		this.entidade = entidade;
	}
	
	@FXML
	public void onBtSaveAction ()
	{
		System.out.println("Salvo");
	}
	
	@FXML
	public void onBtCancelAction()
	{
		System.out.println("Cancelado");
	}
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		
	}
	private void initializeNodes()
	{
		Constraints.setTextFieldInteger(txtID);
		Constraints.setTextFieldMaxLength(txtCliente, 50);
	}
	
	public void updateFormData()
	{
		txtID.setText(String.valueOf(entidade.getId()));
		txtCliente.setText(entidade.getName());
	}
	

}
