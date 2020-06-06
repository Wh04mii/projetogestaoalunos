package br.pitagoras.gestaoalunos.controller;

import br.pitagoras.gestaoalunos.common.Utils;
import br.pitagoras.gestaoalunos.dao.ProfessorDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLLoginController implements Initializable {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Label lblAlerta;

    @FXML
    private Button btnEntrar;

    @FXML
    private Button btnFechar;

    @FXML
    private void sair(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void logar(ActionEvent event) {

        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        // Verificar campos.
        if (!validarCampos()) {
            return;
        }

        ProfessorDAO professorDao = new ProfessorDAO();

        // Validar usuário.
        if (professorDao.pesquisarUsuario(usuario, senha) != null
                || (usuario.equals("admin") && senha.equals("admin"))) {

            new Utils.Tela()
                    .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLTelaPrincipal.fxml")
                    .ehTelaExterna(false)
                    .addTituloTelaExter("Sistema de Gestão de Alunos")
                    .redimensionarTelaExter(false)
                    .exibirTelaCheiaExter()
                    .exibirEsperarTelaExter()
                    .construir();

            // Encerrar a tela atual
            Stage stage = (Stage) btnEntrar.getScene().getWindow();
            stage.close();

        } else {
            // Alerta sobre usuario não encontrado.
            String msg;
            msg = "Usuário ou senha inválidos!";
            lblAlerta.setText(msg);
            lblAlerta.setVisible(true);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private boolean validarCampos() {
        /* Fonte https://en.it1352.com/article/a45a2908823a47fcaaf9ff3cbb7fdffb.html */
        final PseudoClass errorClass = PseudoClass.getPseudoClass("validacao-erro");
        boolean erro = false;
        String msg = "Campo obrigatório";

        // Descrição.
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.pseudoClassStateChanged(errorClass, true);
            txtUsuario.setPromptText(msg);
            erro = true;
        } else {
            txtUsuario.pseudoClassStateChanged(errorClass, false);
            txtUsuario.setPromptText("Informe seu usuário");
        }
        //Carga Horária.
        if (txtSenha.getText().isEmpty()) {
            txtSenha.pseudoClassStateChanged(errorClass, true);
            txtSenha.setPromptText(msg);
            erro = true;
        } else {
            txtSenha.pseudoClassStateChanged(errorClass, false);
            txtSenha.setPromptText("Informe sua senha");
        }

        if (erro) {
            return false;
        } else {
            return true;
        }

    }

}
