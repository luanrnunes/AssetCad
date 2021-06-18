package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.RelacaoCliente;

public class ControladorFormCliente implements Initializable {

	private Department entidade;
	
	private RelacaoCliente client;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
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
	
	public void setRelacaoCliente(RelacaoCliente client)
	{
		this.client = client;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener )
	{
		dataChangeListeners.add(listener);
	}
	
	@FXML
	public void onBtSaveAction (ActionEvent event)
	{	
		//Controle de exceções com o banco em tentativas de injeção
		//eventos da janela de salvamento de clientes
		try {
		entidade = getFormData();
		client.saveOrUpdate(entidade);
		notifyDataChangeListeners();
		Utils.currentStage(event).close();
		}catch (ValidationException e) {
			setErrorMessages(e.getErros());
		}
		catch (DbException e)
		{
			Alerts.showAlert("Erro ao salvar no banco de dados!", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	//Classe que notifica os listeners para alteração de dados
	//no banco. Luan.
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners)
		{
			listener.onDataChanged();
		}
		
	}

	private Department getFormData() {
		
		ValidationException exception = new ValidationException ("Campos obrigatórios não foram preenchidos, por favor, verifique os campos");
		Department obj = new Department();
		obj.setId(Utils.tryParseToInt(txtID.getText()));
		if (txtCliente.getText() == null || txtCliente.getText().trim().equals(""))
		{
			exception.addError("client", "O campo cliente não pode ser vazio!");
		}
		obj.setName(txtCliente.getText());
		
		//Valida se foi detectado algum erro no processo do cadastro
		//Se for maior que 0, não ira comitar ao banco. Luan.
		
		if (exception.getErros().size() > 0)
		{
			throw exception;
		}
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event)
	{
		Utils.currentStage(event).close();
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
	private void setErrorMessages(Map<String, String> errors)
	{
		Set<String> fields = errors.keySet();
		if (fields.contains("client"))
		{
			labelErroCliente.setText(errors.get("client"));
		}
	}

}
