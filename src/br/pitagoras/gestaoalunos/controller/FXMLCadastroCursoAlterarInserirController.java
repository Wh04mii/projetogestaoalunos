package br.pitagoras.gestaoalunos.controller;

import br.pitagoras.gestaoalunos.common.Utils;
import br.pitagoras.gestaoalunos.dao.CursoDAO;
import br.pitagoras.gestaoalunos.dao.ProfessorDAO;
import br.pitagoras.gestaoalunos.model.Curso;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class FXMLCadastroCursoAlterarInserirController implements Initializable {

    @FXML
    private AnchorPane anchorPaneGeral;

    @FXML
    private AnchorPane anchorPaneConteudo;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnCancelar;

    @FXML
    private Label lblCodigoCadastro;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtDescricao;

    @FXML
    public TextField txtCargaHoraria;

    @FXML
    private ComboBox<Professor> comboBoxProfessor;

    private Curso cursoSelecionado;

    private Professor professor;

    @FXML
    private void cancelar(ActionEvent event) {
        new Utils.Tela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroCurso.fxml")
                .ehTelaInterna(true)
                .addAnchorPaneTelaInter(anchorPaneGeral)
                .construir();

        /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/pitagoras/gestaoalunos/view/FXMLCadastroCurso.fxml"));
        AbrirTela tela = new AbrirTela();
        tela.retornaJanelaInterna(loader, anchorPaneGeral); */
    }

    @FXML
    private void excluir(ActionEvent event) {

        try {
            CursoDAO cursoDao = new CursoDAO();
            if (cursoDao.deletar(cursoSelecionado)) {
                new Utils.Mensagem()
                        .addTituloJanela("Atenção")
                        .addMsgConteudo("Registro excluído com sucesso.")
                        .addTipoMsg(Alert.AlertType.INFORMATION)
                        .exibir();

                new Utils.Tela()
                        .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroCurso.fxml")
                        .ehTelaInterna(true)
                        .addAnchorPaneTelaInter(anchorPaneGeral)
                        .construir();
            }
        } catch (Exception e) {
            new Utils.Mensagem()
                    .addTituloJanela("Atenção")
                    .addMsgCabecalho("Ocorreu um erro ao tentar excluir o registro.")
                    .addMsgConteudo(e.getMessage())
                    .addTipoMsg(Alert.AlertType.INFORMATION)
                    .exibir();
        }
    }

    @FXML
    private void salvar(ActionEvent event) {

        String descricao = txtDescricao.getText();
        Double cargaHoraria = Double.parseDouble(txtCargaHoraria.getText());
        Professor professorFk = comboBoxProfessor.getSelectionModel().getSelectedItem();

        CursoDAO cursoDao = new CursoDAO();

        // atulaliza
        if (cursoSelecionado != null) {
            try {
                cursoSelecionado.setDescCurso(descricao);
                cursoSelecionado.setCargaHoraria(cargaHoraria);
                cursoSelecionado.setFkIdProfessor(professorFk);

                if (cursoDao.alterar(cursoSelecionado)) {
                    new Utils.Mensagem()
                            .addTituloJanela("Atenção")
                            .addMsgConteudo("Registro alterado com sucesso.")
                            .addTipoMsg(Alert.AlertType.INFORMATION)
                            .exibir();

                    new Utils.Tela()
                            .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroCurso.fxml")
                            .ehTelaInterna(true)
                            .addAnchorPaneTelaInter(anchorPaneGeral)
                            .construir();

                    /*  UtilsAntigo.exibeMensagem("Atenção", null, "Registro alterado com sucesso.", Alert.AlertType.INFORMATION);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/pitagoras/gestaoalunos/view/FXMLCadastroCurso.fxml"));
                    AbrirTela tela = new AbrirTela();
                    tela.retornaJanelaInterna(loader, anchorPaneGeral);*/
                }
            } catch (Exception e) {
                new Utils.Mensagem()
                        .addTituloJanela("Atenção")
                        .addMsgCabecalho("Ocorreu um erro ao tentar alterar o registro.")
                        .addMsgConteudo(e.getMessage())
                        .addTipoMsg(Alert.AlertType.INFORMATION)
                        .exibir();
                // UtilsAntigo.exibeMensagem("Atenção", "Ocorreu um erro ao tentar alterar registro.", e.getMessage(), Alert.AlertType.INFORMATION);
            }
        } // insere
        else {
            try {
                Curso curso = new Curso();
                curso.setDescCurso(descricao);
                curso.setCargaHoraria(cargaHoraria);
                curso.setFkIdProfessor(professorFk);

                if (cursoDao.inserir(curso)) {
                    new Utils.Mensagem()
                            .addTituloJanela("Atenção")
                            .addMsgConteudo("Registro inserido com sucesso.")
                            .addTipoMsg(Alert.AlertType.INFORMATION)
                            .exibir();

                    new Utils.Tela()
                            .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroCurso.fxml")
                            .ehTelaInterna(true)
                            .addAnchorPaneTelaInter(anchorPaneGeral)
                            .construir();

                    /* UtilsAntigo.exibeMensagem("Atenção", null, "Registro inserido com sucesso.", Alert.AlertType.INFORMATION);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/pitagoras/gestaoalunos/view/FXMLCadastroCurso.fxml"));
                    AbrirTela tela = new AbrirTela();
                    tela.retornaJanelaInterna(loader, anchorPaneGeral); */
                }
            } catch (Exception e) {
                new Utils.Mensagem()
                        .addTituloJanela("Atenção")
                        .addMsgCabecalho("Ocorreu um erro ao tentar inserir o registro.")
                        .addMsgConteudo(e.getMessage())
                        .addTipoMsg(Alert.AlertType.INFORMATION)
                        .exibir();
                // UtilsAntigo.exibeMensagem("Atenção", "Ocorreu um erro ao tentar inserir registro.", e.getMessage(), Alert.AlertType.INFORMATION);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preecheComboBoxProfessor(null);
    }

    public void inicializaCampos(int id) {

        // habilita componentes
        btnExcluir.setVisible(true);
        txtId.setVisible(true);
        lblCodigoCadastro.setVisible(true);

        // inicializa componentes
        CursoDAO cursoDao = new CursoDAO();
        cursoSelecionado = cursoDao.pesquisar(id);
        txtId.setText(String.valueOf(cursoSelecionado.getIdCurso()));
        txtDescricao.setText(cursoSelecionado.getDescCurso());
        txtCargaHoraria.setText(String.valueOf(cursoSelecionado.getCargaHoraria()));
        preecheComboBoxProfessor(cursoSelecionado.getFkIdProfessor());

    }

    // metodo para buscar os cadastos de professores e inserir no combo
    private void preecheComboBoxProfessor(Professor professor) {

        List<Professor> listProfessor;
        ObservableList<Professor> observableListProfessor;
        ProfessorDAO professorDao = new ProfessorDAO();
        listProfessor = professorDao.pesquisar();

        observableListProfessor = FXCollections.observableArrayList(listProfessor);
        comboBoxProfessor.getItems().clear();
        comboBoxProfessor.getItems().add(null);
        comboBoxProfessor.getItems().addAll(observableListProfessor);

        // se receber um registro, deixa o mesmo selecionado.
        if (professor != null) {
            comboBoxProfessor.getSelectionModel().select(professor);
        }
    }
}
