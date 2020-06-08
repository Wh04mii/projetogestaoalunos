package br.pitagoras.gestaoalunos.controller;

import br.pitagoras.gestaoalunos.common.Utils;
import br.pitagoras.gestaoalunos.dao.AlunoDAO;
import br.pitagoras.gestaoalunos.model.Aluno;
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

public class FXMLCadastroAlunoController implements Initializable {

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
    private TableView<Aluno> tblViewAlunos;

    @FXML
    private TableColumn<Aluno, Integer> tblColumnCPF;

    @FXML
    private TableColumn<Aluno, String> tblColumnNome;

    @FXML
    private void handleBtnNovo(ActionEvent event) {
        inserir();

    }

    @FXML
    private void handleBtnEditar(ActionEvent event) {
        if (tblViewAlunos.getSelectionModel().getSelectedItem() == null) {
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
        if (tblViewAlunos.getSelectionModel().getSelectedItem() == null) {
            new Utils.Mensagem()
                    .addMsgConteudo("Selecione um cadastro.")
                    .addTipoMsg(Alert.AlertType.INFORMATION)
                    .exibir();
        } else {
            excluir(tblViewAlunos.getSelectionModel().getSelectedItem());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        atualizar();

        // Habilita dois cliques para acessar a tela de edição do registro.
        tblViewAlunos.setOnMouseClicked((MouseEvent t) -> {
            if (t.getClickCount() == 2 && tblViewAlunos.getSelectionModel().getSelectedItem() != null) {
                alterar();
            }
        });

    }

    private void inserir() {
        new Utils.Tela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroAlunoAlterarInserir.fxml")
                .ehTelaInterna(true)
                .addAnchorPaneTelaInter(anchorPaneGeral)
                .construir();

    }

    private void alterar() {
        Utils utilsTela = new Utils.Tela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroAlunoAlterarInserir.fxml")
                .ehTelaInterna(true)
                .addAnchorPaneTelaInter(anchorPaneGeral)
                .construir();
        FXMLCadastroAlunoAlterarInserirController controller = (FXMLCadastroAlunoAlterarInserirController) utilsTela.getController();
        controller.inicializarCampos(tblViewAlunos.getSelectionModel().getSelectedItem().getIdAluno());

    }

    @FXML
    private void atualizar() {
        ObservableList<Aluno> observableListAluno;
        List<Aluno> listAlunos = new AlunoDAO().pesquisar();

        if (listAlunos != null) {
            // limpa registros existentes.
            tblViewAlunos.getItems().clear();
            // passa a identificação dos campos do bd para as colunas.
            tblColumnCPF.setCellValueFactory(new PropertyValueFactory<>("cpfAluno"));
            tblColumnNome.setCellValueFactory(new PropertyValueFactory<>("NomeAluno"));
            //preenche tblview
            observableListAluno = FXCollections.observableArrayList(listAlunos);
            tblViewAlunos.setItems(observableListAluno);
        }

    }

    private void excluir(Aluno aluno) {
        ButtonType resposta = new Utils.Mensagem()
                .addTipoMsg(Alert.AlertType.CONFIRMATION)
                .addMsgCabecalho("Deseja excluir o registro?")
                .addMsgConteudo(aluno.getNomeAluno())
                .exibir().getResult();

        if (resposta == ButtonType.YES) {
            AlunoDAO alunoDao = new AlunoDAO();
            if (alunoDao.deletar(aluno)) {
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