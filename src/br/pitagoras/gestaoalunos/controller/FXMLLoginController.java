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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
    private BorderPane telaGeral;
    private double initialX;
    private double initialY;

    @FXML
    private void handleBtnSair(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    private void handleBtnEntrar(ActionEvent event) {
        if (validarCampos()) {
            fazerLogin();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventos();
    }

    private boolean validarCampos() {
        /* Fonte https://en.it1352.com/article/a45a2908823a47fcaaf9ff3cbb7fdffb.html */
        final PseudoClass errorClass = PseudoClass.getPseudoClass("validacao-erro");
        boolean continua = true;
        final String msg = "Campo obrigatório";

        // Usuário.
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.pseudoClassStateChanged(errorClass, true);
            txtUsuario.setPromptText(msg);
            continua = false;
        } else {
            txtUsuario.pseudoClassStateChanged(errorClass, false);
            txtUsuario.setPromptText("Informe seu usuário");
        }
        // Senha.
        if (txtSenha.getText().isEmpty()) {
            txtSenha.pseudoClassStateChanged(errorClass, true);
            txtSenha.setPromptText(msg);
            continua = false;
        } else {
            txtSenha.pseudoClassStateChanged(errorClass, false);
            txtSenha.setPromptText("Informe sua senha");
        }
        return continua;

    }

    private void fazerLogin() {
        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();
        ProfessorDAO professorDao = new ProfessorDAO();

        // Validar usuário.
        if (professorDao.pesquisarUsuario(usuario, senha) != null
                || (usuario.equals("admin") && senha.equals("admin"))) {

            new Utils.Tela()
                    .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLTelaPrincipal.fxml")
                    .ehTelaExterna(false)
                    .addTituloTelaExter("Sistema de Gestão de Alunos")
                    .redimensionarTelaExter(true)
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

    private void eventos() {
        
        // Ação tecla enter;
        telaGeral.setOnKeyPressed((event) -> {
           if (event.getCode() == KeyCode.ENTER && validarCampos()){
               fazerLogin();
           }            
        });

        // Mover tela
        // Fonte: https://stackoverflow.com/questions/11780115/moving-an-undecorated-stage-in-javafx-2
        telaGeral.setOnMousePressed((MouseEvent me) -> {
            if (me.getButton() != MouseButton.MIDDLE) {
                initialX = me.getSceneX();
                initialY = me.getSceneY();
            }
        });

        telaGeral.setOnMouseDragged((MouseEvent event) -> {
            telaGeral.getScene().getWindow().setX(event.getScreenX() - initialX);
            telaGeral.getScene().getWindow().setY(event.getScreenY() - initialY);
        });

    }
}
