package br.pitagoras.gestaoalunos.controller;


import br.pitagoras.gestaoalunos.common.TextFieldFormatter;
import br.pitagoras.gestaoalunos.common.Utils;
import br.pitagoras.gestaoalunos.dao.AlunoDAO;
import br.pitagoras.gestaoalunos.model.Aluno;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class FXMLCadastroAlunoAlterarInserirController implements Initializable {

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
    private TextField txtTelefone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtDataNascimento;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    private Aluno alunoSelecionado;

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
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroAluno.fxml")
                .ehTelaInterna(true)
                .addAnchorPaneTelaInter(anchorPaneGeral)
                .construir();

    }
    // método para inserir mascara no text field
     @FXML
    private void mascaraCPF(KeyEvent event) {
         TextFieldFormatter format = new TextFieldFormatter();
         format.setMask("###.###.###-##");
         format.setCaracteresValidos("1234567890");
         format.setTf(txtCPF);
         format.formatter();
         
    }
    // Método para criar máscara para telefone
    @FXML
    private void maskTelAluno(KeyEvent event) {
        TextFieldFormatter format = new TextFieldFormatter();
         format.setMask("(##)#####-####");
         format.setCaracteresValidos("1234567890");
         format.setTf(txtTelefone);
         format.formatter();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void inicializarCampos(int id) {
        // Habilitar componentes.
        btnExcluir.setVisible(true);
        txtId.setVisible(true);
        lblCodigoCadastro.setVisible(true);

        // Inicializar campos.
        alunoSelecionado = new AlunoDAO().pesquisar(id);
        txtId.setText(String.valueOf(alunoSelecionado.getIdAluno()));
        txtNome.setText(alunoSelecionado.getNomeAluno());
        txtCPF.setText(alunoSelecionado.getCpfAluno());
        txtEndereco.setText(alunoSelecionado.getEnderecoAluno());
        txtTelefone.setText(alunoSelecionado.getTelefoneAluno());
        txtEmail.setText(alunoSelecionado.getEmailAluno());

    }

    private void inserirAlterar() {
        String nome = txtNome.getText();
        String cpf = txtCPF.getText();
        String endereco = txtEndereco.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();

        AlunoDAO alunoDao = new AlunoDAO();

        // Atualizar cadastro.
        if (alunoSelecionado != null) {
            try {
                alunoSelecionado.setNomeAluno(nome);
                alunoSelecionado.setCpfAluno(cpf);
                alunoSelecionado.setEnderecoAluno(endereco);
                alunoSelecionado.setTelefoneAluno(telefone);
                alunoSelecionado.setEmailAluno(email);

                if (alunoDao.alterar(alunoSelecionado)) {
                    new Utils.Mensagem()
                            .addMsgConteudo("Registro alterado com sucesso.")
                            .addTipoMsg(Alert.AlertType.INFORMATION)
                            .exibir();

                    new Utils.Tela()
                            .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroAluno.fxml")
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
                Aluno aluno = new Aluno();
                aluno.setNomeAluno(nome);
                aluno.setCpfAluno(cpf);
                aluno.setEmailAluno(email);
                aluno.setEnderecoAluno(endereco);
                aluno.setTelefoneAluno(telefone);

                if (alunoDao.inserir(aluno)) {
                    new Utils.Mensagem()
                            .addMsgConteudo("Registro inserido com sucesso.")
                            .addTipoMsg(Alert.AlertType.INFORMATION)
                            .exibir();

                    new Utils.Tela()
                            .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroAluno.fxml")
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
                .addMsgConteudo(alunoSelecionado.getNomeAluno())
                .exibir().getResult();
        if (resposta == ButtonType.YES) {
            try {
                AlunoDAO alunoDao = new AlunoDAO();
                if (alunoDao.deletar(alunoSelecionado)) {
                    new Utils.Mensagem()
                            .addMsgConteudo("Registro excluído com sucesso.")
                            .addTipoMsg(Alert.AlertType.INFORMATION)
                            .exibir();

                    new Utils.Tela()
                            .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroAluno.fxml")
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

    private boolean validarCampos() {
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
        return continua;

    }
}
