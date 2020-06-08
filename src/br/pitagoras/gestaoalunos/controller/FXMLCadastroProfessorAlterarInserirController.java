package br.pitagoras.gestaoalunos.controller;

import br.pitagoras.gestaoalunos.common.Utils;
import br.pitagoras.gestaoalunos.dao.ProfessorDAO;
import br.pitagoras.gestaoalunos.model.Professor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class FXMLCadastroProfessorAlterarInserirController implements Initializable {

    @FXML
    private AnchorPane anchorPaneGeral;

    @FXML
    private AnchorPane anchorPaneConteudo;

    @FXML
    private Label lblCodigoCadastro;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtRA;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    private Professor professorSelecionado;

    @FXML
    private void handleBtnExcluir(ActionEvent event) {
        excluir();

    }

    @FXML
    private void handleBtnSalvar(ActionEvent event) {
        if (validarCampos()) {
            inserirAlterar();
        }

    }

    @FXML
    private void handleBtnCancelar(ActionEvent event) {
        new Utils.Tela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroProfessor.fxml")
                .ehTelaInterna(true)
                .addAnchorPaneTelaInter(anchorPaneGeral)
                .construir();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarCampos(int id) {
        // Habilitar componentes.
        btnExcluir.setVisible(true);
        txtId.setVisible(true);
        lblCodigoCadastro.setVisible(true);
        txtUsuario.setDisable(true);
        txtUsuario.setFocusTraversable(false);

        // Inicializar campos.
        professorSelecionado = new ProfessorDAO().pesquisar(id);
        txtId.setText(String.valueOf(professorSelecionado.getIdProfessor()));
        txtNome.setText(professorSelecionado.getNomeProfessor());
        txtCPF.setText(professorSelecionado.getCpfProfessor());
        txtEndereco.setText(professorSelecionado.getEnderecoProfessor());
        txtRA.setText(String.valueOf(professorSelecionado.getRAProfessor()));
        txtUsuario.setText(professorSelecionado.getLoginProfessor());
        txtSenha.setText(professorSelecionado.getSenhaProfessor());

    }

    private void inserirAlterar() {
        String nome = txtNome.getText();
        String cpf = txtCPF.getText();
        String endereco = txtEndereco.getText();
        Integer ra = Integer.parseInt(txtRA.getText());
        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        ProfessorDAO professorDao = new ProfessorDAO();

        // Atualizar cadastro.
        if (professorSelecionado != null) {
            try {
                professorSelecionado.setNomeProfessor(nome);
                professorSelecionado.setCpfProfessor(cpf);
                professorSelecionado.setEnderecoProfessor(endereco);
                professorSelecionado.setRAProfessor(ra);
                professorSelecionado.setLoginProfessor(usuario);
                professorSelecionado.setSenhaProfessor(senha);

                if (professorDao.alterar(professorSelecionado)) {
                    new Utils.Mensagem()
                            .addMsgConteudo("Registro alterado com sucesso.")
                            .addTipoMsg(Alert.AlertType.INFORMATION)
                            .exibir();

                    new Utils.Tela()
                            .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroProfessor.fxml")
                            .ehTelaInterna(true)
                            .addAnchorPaneTelaInter(anchorPaneGeral)
                            .construir();

                }
            } catch (Exception e) {
                new Utils.Mensagem()
                        .addMsgCabecalho("Ocorreu um erro ao tentar alterar o registro.")
                        .addMsgConteudo(e.getMessage())
                        .addTipoMsg(Alert.AlertType.INFORMATION)
                        .exibir();

            }
        } // Inserir cadastro.
        else {
            try {
                Professor professor = new Professor();
                professor.setNomeProfessor(nome);
                professor.setCpfProfessor(cpf);
                professor.setEnderecoProfessor(endereco);
                professor.setRAProfessor(ra);
                professor.setLoginProfessor(usuario);
                professor.setSenhaProfessor(senha);

                if (professorDao.inserir(professor)) {
                    new Utils.Mensagem()
                            .addMsgConteudo("Registro inserido com sucesso.")
                            .addTipoMsg(Alert.AlertType.INFORMATION)
                            .exibir();

                    new Utils.Tela()
                            .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroProfessor.fxml")
                            .ehTelaInterna(true)
                            .addAnchorPaneTelaInter(anchorPaneGeral)
                            .construir();

                }
            } catch (Exception e) {
                new Utils.Mensagem()
                        .addMsgCabecalho("Ocorreu um erro ao tentar inserir o registro.")
                        .addMsgConteudo(e.getMessage())
                        .addTipoMsg(Alert.AlertType.INFORMATION)
                        .exibir();
            }
        }

    }

    private void excluir() {
        ButtonType resposta = new Utils.Mensagem()
                .addTipoMsg(Alert.AlertType.CONFIRMATION)
                .addMsgCabecalho("Deseja excluir o registro?")
                .addMsgConteudo(professorSelecionado.getNomeProfessor())
                .exibir().getResult();
        if (resposta == ButtonType.YES) {
            try {
                ProfessorDAO professorDao = new ProfessorDAO();
                if (professorDao.deletar(professorSelecionado)) {
                    new Utils.Mensagem()
                            .addMsgConteudo("Registro excluído com sucesso.")
                            .addTipoMsg(Alert.AlertType.INFORMATION)
                            .exibir();

                    new Utils.Tela()
                            .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroProfessor.fxml")
                            .ehTelaInterna(true)
                            .addAnchorPaneTelaInter(anchorPaneGeral)
                            .construir();
                }
            } catch (Exception e) {
                new Utils.Mensagem()
                        .addMsgCabecalho("Ocorreu um erro ao tentar excluir o registro.")
                        .addMsgConteudo(e.getMessage())
                        .addTipoMsg(Alert.AlertType.INFORMATION)
                        .exibir();
            }
        }

    }

    // Metódo para validar os campos obrigatórios.
    private boolean validarCampos() {
        /* Fonte https://en.it1352.com/article/a45a2908823a47fcaaf9ff3cbb7fdffb.html */
        final PseudoClass errorClass = PseudoClass.getPseudoClass("validacao-erro");
        boolean continua = true;
        final String msg = "Campo obrigatório";

        // Nome
        if (txtNome.getText().isEmpty()) {
            txtNome.pseudoClassStateChanged(errorClass, true);
            txtNome.setPromptText(msg);
            continua = false;
        } else {
            txtNome.pseudoClassStateChanged(errorClass, false);
            txtNome.setPromptText(null);
        }
        // Cpf
        if (txtCPF.getText().isEmpty()) {
            txtCPF.pseudoClassStateChanged(errorClass, true);
            txtCPF.setPromptText(msg);
            continua = false;
        } else {
            txtCPF.pseudoClassStateChanged(errorClass, false);
            txtCPF.setPromptText(null);
        }
        // Ra
        if (txtRA.getText().isEmpty()) {
            txtRA.pseudoClassStateChanged(errorClass, true);
            txtRA.setPromptText(msg);
            continua = false;
        } else {
            txtRA.pseudoClassStateChanged(errorClass, false);
            txtRA.setPromptText(null);
        }
        // Usuario
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.pseudoClassStateChanged(errorClass, true);
            txtUsuario.setPromptText(msg);
            continua = false;
        } else {
            txtUsuario.pseudoClassStateChanged(errorClass, false);
            txtUsuario.setPromptText(null);
        }
        // Senha
        if (txtSenha.getText().isEmpty()) {
            txtSenha.pseudoClassStateChanged(errorClass, true);
            txtSenha.setPromptText(msg);
            continua = false;
        } else {
            txtSenha.pseudoClassStateChanged(errorClass, false);
            txtSenha.setPromptText(null);
        }
        return continua;

    }
}
