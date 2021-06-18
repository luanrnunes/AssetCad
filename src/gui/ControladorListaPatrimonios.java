package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.RelacaoCliente;

public class ControladorListaPatrimonios implements Initializable, DataChangeListener {

	private RelacaoCliente RCliente;

	@FXML
	private TableView<Department> tableViewDepartment;

	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	@FXML
	private TableColumn<Department, String> tableColumnName;

	@FXML
	private TableColumn<Department, Department> tableColumnUpdate;

	@FXML
	private TableColumn<Department, Department> tableColumnRemover;

	private ObservableList<Department> obsList;

	@FXML
	private Button btNovo;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();
		createDialogForm(obj, "/gui/FormCliente.fxml", parentStage);
	}

	public void setRelacaoCliente(RelacaoCliente RCliente) {
		this.RCliente = RCliente;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));

		// Call da superclasse stage. Redimensiona o grid de dados para o tamanho da
		// janela
		// Guilherme, aqui que precisamos validar o que falei com vc no whats

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());

	}

	public void updateTableView() {
		if (RCliente == null) {
			throw new IllegalStateException("Cliente não pode ser nulo!");
		}
		List<Department> list = RCliente.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	// Propriedades da janela de cadastro de ativos.

	private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ControladorFormCliente controller = loader.getController();
			controller.setDepartment(obj);
			controller.setRelacaoCliente(new RelacaoCliente());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Digite os dados do cliente");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {

			Alerts.showAlert("Erro de IO", "Falha ao carrega a tela", e.getMessage(), AlertType.ERROR);
		}

	}

	// Metodo será executado quando houver alteração na base, e enviará
	// a mensagem para o listener do FormCliente. Luan.

	@Override
	public void onDataChanged() {
		updateTableView();

	}

	// Adiciona um botão editar em cada item do grid. Luan.

	private void initEditButtons() {
		tableColumnUpdate.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnUpdate.setCellFactory(param -> new TableCell<Department, Department>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Department obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/FormCliente.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnRemover.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemover.setCellFactory(param -> new TableCell<Department, Department>() {
			private final Button button = new Button("Excluir");

			@Override
			protected void updateItem(Department obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	protected void removeEntity(Department obj) {

		// Remove entidade da tabela, confirma exclusão. Guilherme.
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation",
				"Tem certeza que deseja excluir este registro?");

		if (result.get() == ButtonType.OK) {
			try {
				RCliente.remove(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alerts.showAlert("Erro ao tentar excluir cadastro", null, e.getMessage(), null);
			}

		}

	}
}
