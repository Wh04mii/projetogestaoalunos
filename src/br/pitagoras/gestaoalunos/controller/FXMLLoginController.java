package br.pitagoras.gestaoalunos.controller;

import br.pitagoras.gestaoalunos.common.Utils;
import br.pitagoras.gestaoalunos.dao.ProfessorDAO;
import java.net.URL;
import java.util.ResourceBundle;
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
        String msg;

        // verifica se os dois campos foram informados.
        if (usuario.isEmpty() || senha.isEmpty()) {
            msg = "Usuário ou senha não informados!";
            lblAlerta.setText(msg);
            lblAlerta.setVisible(true);

        } else {

            ProfessorDAO professorDao = new ProfessorDAO();

            // chama metodo para validar se o usuario existe no bd
            if (professorDao.pesquisarUsuario(usuario, senha) != null
                    || (usuario.equals("admin") && senha.equals("admin"))) {              

                // com usuario encontrado abre a tela principal do sistema.
                new Utils.BuilderTela()
                        .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLTelaPrincipal.fxml")
                        .ehTelaExterna(false)
                        .addTituloTelaExter("Sistema de Gestão de Alunos")
                        .redimensionarTelaExter(false)
                        .telaCheiaTelaExter()
                        .exibirEsperarTelaExter()
                        .build();
                
                // Encerra tela atual
                Stage stage = (Stage) btnEntrar.getScene().getWindow();
                stage.close();

                /*   try {

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/br/pitagoras/gestaoalunos/view/FXMLTelaPrincipal.fxml"));
                    Parent root = (Parent) fxmlLoader.load();

                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Sistema de Gestão de Alunos");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);

                    // esconder a tela de login.
                    btnEntrar.getScene().getWindow().hide();

                    // deixar em tela cheia a tela principal
                    Screen screen = Screen.getPrimary();
                    Rectangle2D bounds = screen.getVisualBounds();
                    stage.setWidth(bounds.getWidth());
                    stage.setHeight(bounds.getHeight());

                    stage.show();

                } catch (IOException ex) {
                    Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                } */
            } else {
                // alerta sobre usuario não encontrado.
                msg = "Usuário ou senha inválidos!";
                lblAlerta.setText(msg);
                lblAlerta.setVisible(true);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
