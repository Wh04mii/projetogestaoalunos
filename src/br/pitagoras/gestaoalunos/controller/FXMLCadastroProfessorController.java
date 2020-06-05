package br.pitagoras.gestaoalunos.controller;

import br.pitagoras.gestaoalunos.common.UtilsAntigo;
import br.pitagoras.gestaoalunos.dao.ProfessorDAO;
import br.pitagoras.gestaoalunos.model.Professor;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLCadastroProfessorController implements Initializable {

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnFechar;

    @FXML
    private TableView<Professor> tblViewProfessores;

    @FXML
    private TableColumn<Professor, Integer> tblColumnID;

    @FXML
    private TableColumn<Professor, Integer> tblColumnRA;

    @FXML
    private TableColumn<Professor, String> tblColumnNome;

    private List<Professor> listAlunos;
    private ObservableList<Professor> observableListProfessor;

    @FXML
    private void inserir(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/br/pitagoras/gestaoalunos/view/FXMLCadastroProfessorInserir.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage adicionarCadastroStage = new Stage();

            adicionarCadastroStage.setTitle("Novo cadastro");
            adicionarCadastroStage.setScene(scene);
            adicionarCadastroStage.setResizable(false);
            adicionarCadastroStage.initModality(Modality.APPLICATION_MODAL);
            adicionarCadastroStage.showAndWait();
            carregarProfessores();

        } catch (IOException ex) {
            Logger.getLogger(FXMLTelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void carregarProfessores() {
        ProfessorDAO alunoDao = new ProfessorDAO();
        listAlunos = alunoDao.pesquisar();

        if (listAlunos != null) {
            //limpar tbl View
            tblViewProfessores.getItems().clear();

            // passa a identificação dos campos do bd para as colunas.
            tblColumnID.setCellValueFactory(new PropertyValueFactory<>("IdProfessor"));
            tblColumnRA.setCellValueFactory(new PropertyValueFactory<>("RAProfessor"));
            tblColumnNome.setCellValueFactory(new PropertyValueFactory<>("NomeProfessor"));
            observableListProfessor = FXCollections.observableArrayList(listAlunos);
            tblViewProfessores.setItems(observableListProfessor);

        } else {
            UtilsAntigo.exibeMensagem("Atenção", null, "Nenhum registro encontrado.", Alert.AlertType.INFORMATION);
        }
    }

    // recarregar lista de cadastros.
    @FXML
    private void atualizarLista(ActionEvent event) {
        carregarProfessores();
    }

    @FXML
    private void fechar(ActionEvent event) {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void alterar(ActionEvent event) {

        if (tblViewProfessores.getSelectionModel().getSelectedItem() == null) {
            UtilsAntigo.exibeMensagem("Atenção", null, "Selecione um cadastro.", Alert.AlertType.INFORMATION);
            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/br/pitagoras/gestaoalunos/view/FXMLCadastroProfessorAlterar.fxml"));
            Parent editarCadastroParent = (Parent) loader.load();
            Scene editarCadastroParentScene = new Scene(editarCadastroParent);
            Stage editarCadastroStage = new Stage();

            // acessar o controller da tela secundária para preencher os dados.
            FXMLCadastroProfessorAlterarController controller = loader.getController();
            controller.inicializaCampos(tblViewProfessores.getSelectionModel().getSelectedItem().getIdProfessor());
            editarCadastroStage.setTitle("Editar cadastro");
            editarCadastroStage.setScene(editarCadastroParentScene);
            editarCadastroStage.setResizable(false);
            editarCadastroStage.initModality(Modality.APPLICATION_MODAL);
            editarCadastroStage.showAndWait();
            carregarProfessores();

        } catch (IOException ex) {
            Logger.getLogger(FXMLTelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarProfessores();
    }

}
