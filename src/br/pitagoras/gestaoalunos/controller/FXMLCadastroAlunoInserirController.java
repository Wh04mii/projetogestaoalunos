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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLCadastroAlunoInserirController implements Initializable {
    
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
    private Button btnCancelar;

    //labels para validacao
    @FXML
    private Label obgNome;
    @FXML
    private Label obgend;
    @FXML
    private Label obgCpf;
    @FXML
    private Label obgTelefone;
    @FXML
    private Label obgEmail;
    
    @FXML
    private TextField txtNome;

    // sair da tela
    @FXML
    private void cancelar(ActionEvent event) {
        
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
        
    }

    // metodo para salvar um novo usuario no bd.
    @FXML
    private void salvar(ActionEvent event) {
        
        Aluno aluno = new Aluno();        
        
        String nome = txtNome.getText();
        String cpf = txtCPF.getText();
        String endereco = txtEndereco.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();        
        
        if (nome.length() > 0) {
            
            obgNome.setVisible(false);
        } else {
            obgNome.setVisible(true);
            
        }
        
        if (cpf.length() > 0) {
            obgCpf.setVisible(false);
        } else {
            obgCpf.setVisible(true);
        }
        if (endereco.length() > 0) {
            obgend.setVisible(false);
        } else {
            obgend.setVisible(true);
        }
        
        if (telefone.length() > 0) {
            obgTelefone.setVisible(false);
        } else {
            obgTelefone.setVisible(true);
        }
        if (email.length() > 0) {
            obgEmail.setVisible(false);
        } else {
            obgEmail.setVisible(true);
        }
        
        if ((nome.length() > 0)
                && (cpf.length() > 0)
                && (endereco.length() > 0)
                && (telefone.length() > 0)
                && (email.length() > 0)) {
            
            try {
                
                aluno.setNomeAluno(nome);
                aluno.setCpfAluno(cpf);
                aluno.setEmailAluno(email);
                aluno.setEnderecoAluno(endereco);
                aluno.setTelefoneAluno(telefone);

                // 
                AlunoDAO alunoDao = new AlunoDAO();

                // verifica se o registro foi inserido para que asim feche a tela.
                if (alunoDao.inserir(aluno)) {
                    UtilsAntigo.exibeMensagem("Parabens", null, "Cadastrado com sucesso", Alert.AlertType.INFORMATION);
                    Stage stage = (Stage) btnSalvar.getScene().getWindow();
                    stage.close();
                }
            } catch (Exception e) {
                UtilsAntigo.exibeMensagem("Atenção", "Ocorreu um erro ao tentar inserir registro.", e.getMessage(), Alert.AlertType.INFORMATION);
            }
            
        } else {
            UtilsAntigo.exibeMensagem("Atenção", null, "Cadastro inválido , preencha os campos obrigatório", Alert.AlertType.INFORMATION);
        }
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    //demais metodos
    private void salvar(java.awt.event.ActionEvent evt) {        
        
    }
    
}
