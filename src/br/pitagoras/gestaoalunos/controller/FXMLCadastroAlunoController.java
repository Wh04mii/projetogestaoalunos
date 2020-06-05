package br.pitagoras.gestaoalunos.controller;

import br.pitagoras.gestaoalunos.common.UtilsAntigo;
import br.pitagoras.gestaoalunos.dao.AlunoDAO;
import br.pitagoras.gestaoalunos.model.Aluno;
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

public class FXMLCadastroAlunoController implements Initializable {

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnFechar;

    @FXML
    private TableView<Aluno> tblViewAlunos;

    @FXML
    private TableColumn<Aluno, Integer> tblColumnID;

    @FXML
    private TableColumn<Aluno, Integer> tblColumnCPF;

    @FXML
    private TableColumn<Aluno, String> tblColumnNome;

    // utilizados para receber lista do bd e preencher no tbl view
    private List<Aluno> listAlunos;
    private ObservableList<Aluno> observableListAluno;

    // metodo para chamar a tela de alteração/exclusão de cadastro
    @FXML
    private void alterar(ActionEvent event) {
     
        // verifica se tem algum registro selecionado.
        if (tblViewAlunos.getSelectionModel().getSelectedItem() == null) {     
            UtilsAntigo.exibeMensagem("Atenção", null, "Nenhum cadastro selecionado.", Alert.AlertType.INFORMATION);
            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/br/pitagoras/gestaoalunos/view/FXMLCadastroAlunoAlterar.fxml"));
            Parent editarCadastroParent = (Parent) loader.load();
            Scene editarCadastroParentScene = new Scene(editarCadastroParent);
            Stage editarCadastroStage = new Stage();

            // acessar o controller da tela secundária para preencher os dados.
            FXMLCadastroAlunoAlterarController controller = loader.getController();
            controller.inicializaCampos(tblViewAlunos.getSelectionModel().getSelectedItem().getIdAluno());

            editarCadastroStage.setTitle("Editar cadastro");
            editarCadastroStage.setScene(editarCadastroParentScene);
            editarCadastroStage.setResizable(false);
            editarCadastroStage.initModality(Modality.APPLICATION_MODAL);
            editarCadastroStage.showAndWait();

            // ao encerrar a tela secundária chama método para recarregar lista
            carregarAlunos();

        } catch (IOException ex) {
            Logger.getLogger(FXMLTelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // recarregar lista de cadastros.
    @FXML
    private void atualizarLista(ActionEvent event) {
         carregarAlunos();
    }

    // fechar a tela e retornar a tela principal.
    @FXML
    private void fechar(ActionEvent event) {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }

    // metodo utilizado para inserir um novo cadastro.
    @FXML
    private void inserir(ActionEvent event) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/br/pitagoras/gestaoalunos/view/FXMLCadastroAlunoInserir.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage adicionarCadastroStage = new Stage();

            adicionarCadastroStage.setTitle("Novo cadastro");
            adicionarCadastroStage.setScene(scene);
            adicionarCadastroStage.setResizable(false);
            adicionarCadastroStage.initModality(Modality.APPLICATION_MODAL);
            adicionarCadastroStage.showAndWait();

            // ao encerrar a tela secundária chama método para recarregar lista
            carregarAlunos();

        } catch (IOException ex) {
            Logger.getLogger(FXMLTelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // inicializa a tela chamando metodo que busca os dados.
        carregarAlunos();
    }

    // metodo utilizado para preencher o componente Table View com resultados do banco.
    @FXML
    private void carregarAlunos() {

        AlunoDAO alunoDao = new AlunoDAO();
        listAlunos = alunoDao.pesquisar();

        if (listAlunos != null) {
            // limpa registros existentes.
            tblViewAlunos.getItems().clear();
            // passa a identificação dos campos do bd para as colunas.
            tblColumnID.setCellValueFactory(new PropertyValueFactory<>("idAluno"));
            tblColumnCPF.setCellValueFactory(new PropertyValueFactory<>("cpfAluno"));
            tblColumnNome.setCellValueFactory(new PropertyValueFactory<>("NomeAluno"));
            //preenche tblview
            observableListAluno = FXCollections.observableArrayList(listAlunos);
            tblViewAlunos.setItems(observableListAluno);

        } else {
            UtilsAntigo.exibeMensagem("Atenção", null, "Nenhum registro encontrado.", Alert.AlertType.INFORMATION);
        }
    }

}
