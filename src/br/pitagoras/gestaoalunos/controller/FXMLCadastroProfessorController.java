package br.pitagoras.gestaoalunos.controller;

import br.pitagoras.gestaoalunos.common.Utils;
import br.pitagoras.gestaoalunos.dao.ProfessorDAO;
import br.pitagoras.gestaoalunos.model.Professor;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLCadastroProfessorController implements Initializable {

    @FXML
    private AnchorPane anchorPaneGeral;

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
    private TableColumn<Professor, Integer> tblColumnRA;

    @FXML
    private TableColumn<Professor, String> tblColumnNome;

    @FXML
    private void handleBtnNovo(ActionEvent event) {
        inserir();

    }

    @FXML
    private void handleBtnEditar(ActionEvent event) {
        if (tblViewProfessores.getSelectionModel().getSelectedItem() == null) {
            new Utils.Mensagem()
                    .addMsgConteudo("Selecione um cadastro.")
                    .addTipoMsg(Alert.AlertType.INFORMATION)
                    .exibir();
        } else {
            alterar();
        }

    }

    @FXML
    private void handleBtnAtualizar(ActionEvent event) {
        atualizar();

    }

    @FXML
    private void handleBtnFechar(ActionEvent event) {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();

    }

    @FXML
    private void handleCtxtExcluir(ActionEvent event) {
        if (tblViewProfessores.getSelectionModel().getSelectedItem() == null) {
            new Utils.Mensagem()
                    .addMsgConteudo("Selecione um cadastro.")
                    .addTipoMsg(Alert.AlertType.INFORMATION)
                    .exibir();
        } else {
            excluir(tblViewProfessores.getSelectionModel().getSelectedItem());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        atualizar();

        // Habilita dois cliques para acessar a tela de edição do registro.
        tblViewProfessores.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2 && tblViewProfessores.getSelectionModel().getSelectedItem() != null) {
                alterar();
            }
        });

    }

    private void inserir() {
        new Utils.Tela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroProfessorAlterarInserir.fxml")
                .ehTelaInterna(true)
                .addAnchorPaneTelaInter(anchorPaneGeral)
                .construir();

    }

    private void alterar() {
        Utils utilsTela = new Utils.Tela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroProfessorAlterarInserir.fxml")
                .ehTelaInterna(true)
                .addAnchorPaneTelaInter(anchorPaneGeral)
                .construir();
        FXMLCadastroProfessorAlterarInserirController controller = (FXMLCadastroProfessorAlterarInserirController) utilsTela.getController();
        controller.inicializarCampos(tblViewProfessores.getSelectionModel().getSelectedItem().getIdProfessor());

    }

    private void atualizar() {
        ObservableList<Professor> observableListProfessor;
        List<Professor> listAlunos = new ProfessorDAO().pesquisar();

        if (listAlunos != null) {
            tblViewProfessores.getItems().clear();
            tblColumnRA.setCellValueFactory(new PropertyValueFactory<>("RAProfessor"));
            tblColumnNome.setCellValueFactory(new PropertyValueFactory<>("NomeProfessor"));
            observableListProfessor = FXCollections.observableArrayList(listAlunos);
            tblViewProfessores.setItems(observableListProfessor);
        }

    }

    private void excluir(Professor professor) {
        ButtonType resposta = new Utils.Mensagem()
                .addTipoMsg(Alert.AlertType.CONFIRMATION)
                .addMsgCabecalho("Deseja excluir o registro?")
                .addMsgConteudo(professor.getNomeProfessor())
                .exibir().getResult();

        if (resposta == ButtonType.YES) {
            ProfessorDAO professorDao = new ProfessorDAO();
            if (professorDao.deletar(professor)) {
                new Utils.Mensagem()
                        .addMsgConteudo("Registro excluído com sucesso.")
                        .addTipoMsg(Alert.AlertType.INFORMATION)
                        .exibir();
                atualizar();
            } else {
                new Utils.Mensagem()
                        .addMsgConteudo("Ocorreu um erro ao tentar excluir o registro.")
                        .addTipoMsg(Alert.AlertType.ERROR)
                        .exibir();
            }
        }

    }
}
