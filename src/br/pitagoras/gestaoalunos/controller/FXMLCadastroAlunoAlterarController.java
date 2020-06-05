package br.pitagoras.gestaoalunos.controller;

import br.pitagoras.gestaoalunos.common.UtilsAntigo;
import br.pitagoras.gestaoalunos.dao.AlunoDAO;
import br.pitagoras.gestaoalunos.model.Aluno;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLCadastroAlunoAlterarController implements Initializable {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtTelefone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtDataNascimento;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnCancelar;

    private Aluno alunoSelecionado;

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
            AlunoDAO alunoDao = new AlunoDAO();
            if (alunoDao.deletar(alunoSelecionado)) {
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

            alunoSelecionado.setNomeAluno(txtNome.getText());
            alunoSelecionado.setCpfAluno(txtCPF.getText());
            alunoSelecionado.setEnderecoAluno(txtEndereco.getText());
            alunoSelecionado.setTelefoneAluno(txtTelefone.getText());
            alunoSelecionado.setEmailAluno(txtEmail.getText());

            AlunoDAO alunoDao = new AlunoDAO();

            if (alunoDao.alterar(alunoSelecionado)) {
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

        AlunoDAO alunoDao = new AlunoDAO();
        alunoSelecionado = alunoDao.pesquisar(id);
        txtId.setText(String.valueOf(alunoSelecionado.getIdAluno()));
        txtNome.setText(alunoSelecionado.getNomeAluno());
        txtCPF.setText(alunoSelecionado.getCpfAluno());
        txtEndereco.setText(alunoSelecionado.getEnderecoAluno());
        txtTelefone.setText(alunoSelecionado.getTelefoneAluno());
        txtEmail.setText(alunoSelecionado.getEmailAluno());
        //txtDataNascimento.setText(alunoSelecionado.getDataNascAluno());

    }
}
