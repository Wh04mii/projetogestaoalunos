package br.pitagoras.gestaoalunos.controller;

import br.pitagoras.gestaoalunos.common.UtilsAntigo;
import br.pitagoras.gestaoalunos.dao.ProfessorDAO;
import br.pitagoras.gestaoalunos.model.Professor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLCadastroProfessorInserirController implements Initializable {

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
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;
    @FXML
    private Label nomeObg;
    @FXML
    private Label cpfObg;
    @FXML
    private Label senhaObg;
    @FXML
    private Label usuarioObg;
    @FXML
    private Label endObg;
    @FXML
    private Label raObg;

    // sair da tela
    @FXML
    private void cancelar(ActionEvent event) {

        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();

    }

    // metodo para salvar um novo usuario no bd.
    @FXML
    private void salvar(ActionEvent event) {

        Professor professor = new Professor();
        String nome = txtNome.getText();
        String cpf = txtCPF.getText();
        String endereco = txtEndereco.getText();
        String ra = txtRA.getText();
        String user = txtUsuario.getText();
        String senha = txtSenha.getText();

        if (nome.length() > 0) {

            nomeObg.setVisible(false);
        } else {
            nomeObg.setVisible(true);

        }

        if (cpf.length() > 0) {
            cpfObg.setVisible(false);
        } else {
            cpfObg.setVisible(true);
        }
        if (endereco.length() > 0) {
            endObg.setVisible(false);
        } else {
            endObg.setVisible(true);
        }

        if (user.length() > 0) {
            usuarioObg.setVisible(false);
        } else {
            usuarioObg.setVisible(true);
        }
        if (ra.length() > 0) {
            raObg.setVisible(false);
        } else {
            raObg.setVisible(true);
        }
        if (senha.length() > 0) {
            senhaObg.setVisible(false);
        } else {
            senhaObg.setVisible(true);
        }

        if ((nome.length() > 0)
                && (cpf.length() > 0)
                && (endereco.length() > 0)
                && (ra.length() > 0)
                && (user.length() > 0)
                && (senha.length() > 0)
                && (user.length() > 0)) {

            try {
                professor.setNomeProfessor(nome);
                professor.setCpfProfessor(cpf);
                professor.setEnderecoProfessor(endereco);
                professor.setRAProfessor(Integer.parseInt(ra));
                professor.setLoginProfessor(user);
                professor.setSenhaProfessor(senha);

                ProfessorDAO professorDao = new ProfessorDAO();

                // verifica se o registro foi inserido para que asim feche a tela.
                if (professorDao.inserir(professor)) {
                    UtilsAntigo.exibeMensagem("Atenção", null, "Registro adicionado com sucesso.", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) btnSalvar.getScene().getWindow();
                    stage.close();
                }
            } catch (Exception e) {
               UtilsAntigo.exibeMensagem("Atenção", "Ocorreu um erro ao tentar inserir registro.", e.getMessage(), Alert.AlertType.INFORMATION);
            }
        } else {
            UtilsAntigo.exibeMensagem("Atenção", null, "Cadastro inválido, preencha os campos obrigatórios.", Alert.AlertType.INFORMATION);

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    
    public boolean validaCampos(){
        
        String erro = "";
        
        if (txtNome.getText().trim().isEmpty())
            erro += "Nome não informado.";
        
        
        if (erro.trim() == "")
            return true;
        else
            return false;
            
            
        
    }

}
