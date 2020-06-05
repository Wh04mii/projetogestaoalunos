package br.pitagoras.gestaoalunos.controller;

import br.pitagoras.gestaoalunos.common.Utils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class FXMLTelaPrincipalController implements Initializable {

    @FXML
    private AnchorPane anchorPaneRodape;

    @FXML
    private MenuItem cadAluno;

    @FXML
    private MenuItem cadCurso;

    @FXML
    private MenuItem cadProfessor;

    @FXML
    private MenuItem ferMatricula;

    @FXML
    private MenuItem ajuSobre;

    @FXML
    private AnchorPane anchorPaneConteudo;

    // abre a tela de cadastro de alunos
    @FXML
    private void abrirCadastroAluno(ActionEvent event) {

        new Utils.BuilderTela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroAluno.fxml")
                .ehTelaExterna(true)
                .addTituloTelaExter("Cadastro de Alunos")
                .redimensionarTelaExter(false)
                .build();

        /*   FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/pitagoras/gestaoalunos/view/FXMLCadastroAluno.fxml"));
          UtilsAntigo.retornaJanela(loader, "Cadastro de Alunos");
          AbrirTela tela = new AbrirTela();
          tela.retornaJanela(loader, "Cadastro de Alunos"); */
    }

    @FXML
    private void abrirCadastroCurso(ActionEvent event) {

        new Utils.BuilderTela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroCurso.fxml")
                .ehTelaExterna(true)
                .addTituloTelaExter("Cadastro de Cursos")
                .redimensionarTelaExter(false)
                .build();

        /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/pitagoras/gestaoalunos/view/FXMLCadastroCurso.fxml"));
        AbrirTela tela = new AbrirTela();
        tela.retornaJanela(loader, "Cadastro de Cursos"); */
    }

    // abre a tela de cadastro de professor
    @FXML
    private void abrirCadastroProfessor(ActionEvent event) {

        new Utils.BuilderTela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroProfessor.fxml")
                .ehTelaExterna(true)
                .addTituloTelaExter("Cadastro de Professores")
                .redimensionarTelaExter(false)
                .build();

        /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/pitagoras/gestaoalunos/view/FXMLCadastroProfessor.fxml"));
        AbrirTela tela = new AbrirTela();
        tela.retornaJanela(loader, "Cadastro de Professores");*/
    }

    // abre o alertbox sobre
    @FXML
    private void abrirSobre(ActionEvent event) {

        new Utils.BuilderExibeMsg()
                .addTituloJanela("Sobre")
                .addMsgCabecalho("JADATECH - Gereciamento de Alunos - 2020")
                .addMsgConteudo("Sistemas de Informação - 7ºp \n"
                        + "Lucas Cardoso \n"
                        + "Guilherme Francisco \n"
                        + "Jefferson Magalhães.")
                .addTipoMsg(Alert.AlertType.INFORMATION)
                .build();

        /*Utils.exibeMensagem("Sobre", "JADATECH - Gereciamento de Alunos - 2020", "Sistemas de Informação - 7ºp \n"
                + "Lucas Cardoso \n"
                + "Guilherme Francisco \n"
                + "Jefferson Magalhães.", Alert.AlertType.INFORMATION);*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
