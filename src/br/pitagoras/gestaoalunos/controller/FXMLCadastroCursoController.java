package br.pitagoras.gestaoalunos.controller;

import br.pitagoras.gestaoalunos.common.Utils;
import br.pitagoras.gestaoalunos.dao.CursoDAO;
import br.pitagoras.gestaoalunos.model.Curso;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLCadastroCursoController implements Initializable {

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
    private TableView<Curso> tblViewCurso;

    @FXML
    private TableColumn<Curso, Integer> tblColumnCargaHoraria;

    @FXML
    private TableColumn<Curso, String> tblColumnDescricao;

    @FXML
    private MenuItem contextMenuItemAlterar;

    @FXML
    private MenuItem contextMenuItemExcluir;

    @FXML
    private void handleBtnNovo(ActionEvent event) {
        inserir();
    }

    @FXML
    private void handleBtnEditar(ActionEvent event) {
        if (tblViewCurso.getSelectionModel().getSelectedItem() == null) {
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
        if (tblViewCurso.getSelectionModel().getSelectedItem() == null) {
            new Utils.Mensagem()
                    .addMsgConteudo("Selecione um cadastro.")
                    .addTipoMsg(Alert.AlertType.INFORMATION)
                    .exibir();
        } else {
            excluir(tblViewCurso.getSelectionModel().getSelectedItem());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        atualizar();

        // Habilita dois cliques para acessar a tela de edição do registro.
        tblViewCurso.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2 && tblViewCurso.getSelectionModel().getSelectedItem() != null) {
                alterar();
            }
        });

    }

    private void inserir() {
        new Utils.Tela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroCursoAlterarInserir.fxml")
                .ehTelaInterna(true)
                .addAnchorPaneTelaInter(anchorPaneGeral)
                .construir();

    }

    private void alterar() {
        Utils utilsTela = new Utils.Tela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroCursoAlterarInserir.fxml")
                .ehTelaInterna(true)
                .addAnchorPaneTelaInter(anchorPaneGeral)
                .construir();
        FXMLCadastroCursoAlterarInserirController controller = (FXMLCadastroCursoAlterarInserirController) utilsTela.getController();
        controller.inicializarCampos(tblViewCurso.getSelectionModel().getSelectedItem().getIdCurso());

    }

    private void atualizar() {
        ObservableList<Curso> observableListCurso;
        List<Curso> listCurso = new CursoDAO().pesquisar();

        if (listCurso != null) {
            // Limpa registros existentes.
            tblViewCurso.getItems().clear();
            // Passa a identificação dos campos do bd para as colunas.
            tblColumnCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("CargaHoraria"));
            tblColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("DescCurso"));
            // Preenche tblview.
            observableListCurso = FXCollections.observableArrayList(listCurso);
            tblViewCurso.setItems(observableListCurso);
        }

    }

    private void excluir(Curso curso) {
        ButtonType resposta = new Utils.Mensagem()
                .addTipoMsg(Alert.AlertType.CONFIRMATION)
                .addMsgCabecalho("Deseja excluir o registro?")
                .addMsgConteudo(curso.getDescCurso())
                .exibir().getResult();

        if (resposta == ButtonType.YES) {
            CursoDAO cursoDao = new CursoDAO();
            if (cursoDao.deletar(curso)) {
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