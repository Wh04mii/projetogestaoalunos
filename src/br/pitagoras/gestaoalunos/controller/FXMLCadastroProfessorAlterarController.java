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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLCadastroProfessorAlterarController implements Initializable {

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
    private Button btnSalvar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnCancelar;

    private Professor professorSelecionado;

    // sair da tela
    @FXML
    private void cancelar(ActionEvent event) {

        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();

    }

    // remover um cadastro
    @FXML
    private void excluir(ActionEvent event) {

        try {
            ProfessorDAO professorDao = new ProfessorDAO();
            if (professorDao.deletar(professorSelecionado)) {
                UtilsAntigo.exibeMensagem("Atenção", null, "Registro excluído com sucesso.", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) btnSalvar.getScene().getWindow();
                stage.close();
            }
        } catch (Exception e) {
            UtilsAntigo.exibeMensagem("Atenção", "Ocorreu um erro ao tentar excluir registro.", e.getMessage(), Alert.AlertType.INFORMATION);
        }

    }

    // salvar alteração do cadastro
    @FXML
    private void salvar(ActionEvent event) {
        
        try {
            professorSelecionado.setNomeProfessor(txtNome.getText());
            professorSelecionado.setCpfProfessor(txtCPF.getText());
            professorSelecionado.setEnderecoProfessor(txtEndereco.getText());
            professorSelecionado.setRAProfessor(Integer.parseInt(txtRA.getText()));
            professorSelecionado.setSenhaProfessor(txtSenha.getText());
            
            ProfessorDAO professorDao = new ProfessorDAO();

            if (professorDao.alterar(professorSelecionado)) {
                UtilsAntigo.exibeMensagem("Atenção", null, "Registro alterado com sucesso.", Alert.AlertType.INFORMATION);               
                Stage stage = (Stage) btnSalvar.getScene().getWindow();
                stage.close();
            }
        } catch (Exception e) {
            UtilsAntigo.exibeMensagem("Atenção", "Ocorreu um erro ao tentar alterar registro.", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // preencher os campos da tela de edição.
    public void inicializaCampos(int id) {

        ProfessorDAO professorDao = new ProfessorDAO();
        professorSelecionado = professorDao.pesquisar(id);
        txtId.setText(String.valueOf(professorSelecionado.getIdProfessor()));
        txtNome.setText(professorSelecionado.getNomeProfessor());
        txtCPF.setText(professorSelecionado.getCpfProfessor());
        txtEndereco.setText(professorSelecionado.getEnderecoProfessor());
        txtRA.setText(String.valueOf(professorSelecionado.getRAProfessor()));
        txtUsuario.setText(professorSelecionado.getLoginProfessor());
        txtSenha.setText(professorSelecionado.getSenhaProfessor());

    }

}
