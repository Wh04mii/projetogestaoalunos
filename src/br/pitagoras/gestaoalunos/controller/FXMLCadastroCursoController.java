package br.pitagoras.gestaoalunos.controller;

import br.pitagoras.gestaoalunos.dao.CursoDAO;
import br.pitagoras.gestaoalunos.common.Utils;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    // utilizados para receber lista do bd e preencher no tbl view
    private List<Curso> listCurso;
    private ObservableList<Curso> observableListCurso;

    @FXML
    private void alterar(ActionEvent event) {

        if (tblViewCurso.getSelectionModel().getSelectedItem() == null) {
            new Utils.Mensagem()
                    .addTituloJanela("Atenção")
                    .addMsgConteudo("Nenhum cadastro selecionado.")
                    .addTipoMsg(Alert.AlertType.INFORMATION)
                    .exibe();
            return;
        }

        Utils utilsTela = new Utils.Tela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroCursoAlterarInserir.fxml")
                .ehTelaInterna(true)
                .addAnchorPaneTelaInter(anchorPaneGeral)
                .constroi();

        FXMLCadastroCursoAlterarInserirController controller = (FXMLCadastroCursoAlterarInserirController) utilsTela.getController();
        controller.inicializaCampos(tblViewCurso.getSelectionModel().getSelectedItem().getIdCurso());

        /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/pitagoras/gestaoalunos/view/FXMLCadastroCursoAlterarInserir.fxml"));
        AbrirTela tela = new AbrirTela();
        tela.retornaJanelaInterna(loader, anchorPaneGeral);
        FXMLCadastroCursoAlterarInserirController controller = (FXMLCadastroCursoAlterarInserirController) loader.getController();
        controller.inicializaCampos(tblViewCurso.getSelectionModel().getSelectedItem().getIdCurso());*/
    }

    @FXML
    private void atualizar(ActionEvent event) {
        atualizarLista();
    }

    @FXML
    private void fechar(ActionEvent event) {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void inserir(ActionEvent event) {
        new Utils.Tela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroCursoAlterarInserir.fxml")
                .ehTelaInterna(true)
                .addAnchorPaneTelaInter(anchorPaneGeral)
                .constroi();
        
        /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/pitagoras/gestaoalunos/view/FXMLCadastroCursoAlterarInserir.fxml"));
        AbrirTela tela = new AbrirTela();
        tela.retornaJanelaInterna(loader, anchorPaneGeral);*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        atualizarLista();
    }

    private void atualizarLista() {
        CursoDAO cursoDao = new CursoDAO();
        listCurso = cursoDao.pesquisar();

        if (listCurso != null) {
            // limpa registros existentes.
            tblViewCurso.getItems().clear();
            // passa a identificação dos campos do bd para as colunas.
            tblColumnCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("CargaHoraria"));
            tblColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("DescCurso"));
            //preenche tblview
            observableListCurso = FXCollections.observableArrayList(listCurso);
            tblViewCurso.setItems(observableListCurso);

        } else {
            new Utils.Mensagem()
                    .addTituloJanela("Atenção")
                    .addMsgConteudo("Nenhum registro encontrado.")
                    .addTipoMsg(Alert.AlertType.INFORMATION)
                    .exibe();
        }
    }

}
