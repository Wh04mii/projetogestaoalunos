package br.pitagoras.gestaoalunos.common;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Utils {

    // Objeto para acessar o controller de fora da classe
    private Object controller;

    public Object getController() {
        return controller;
    }

    // Construtor que recebe um builder tela e inicia o object controller.
    private Utils(BuilderTela builderTela) {
        this.controller = builderTela.controller();
    }

    // Classe estática para implementar o pattern Builder
    public static class BuilderTela {

        /*  Nomenclatura
            TelaInter = tela interna, abre sobre o anchorPane;
            TelaExter = janela externa;
         */
        private AnchorPane anchorPane;
        private FXMLLoader loader;
        private Stage stage;
        private boolean telaInterna;
        private boolean telaExterna;
        private boolean exibirEsperarTelaExterna;

        // Construtor inicializando o loader
        public BuilderTela() {
            this.loader = new FXMLLoader();
            this.telaInterna = false;
            this.telaExterna = false;
        }

        public BuilderTela addCaminhoFXML(String caminho) {
            this.loader.setLocation(getClass().getResource(caminho));
            return this;
        }

        public BuilderTela ehTelaInterna(boolean resposta) {
            this.telaInterna = true;
            return this;
        }

        public BuilderTela ehTelaExterna(boolean resposta) {
            this.stage = new Stage();
            this.telaExterna = true;
            return this;
        }

        public BuilderTela addAnchorPaneTelaInter(AnchorPane anchorPane) {
            this.anchorPane = anchorPane;
            return this;
        }

        public BuilderTela addTituloTelaExter(String titulo) {
            stage.setTitle(titulo);
            return this;
        }

        public BuilderTela redimensionarTelaExter(boolean resposta) {
            this.stage.setResizable(resposta);
            return this;
        }

        public BuilderTela centralizaTelaExterna() {
            this.stage.centerOnScreen();
            return this;
        }

        public BuilderTela estiloTelaExter(StageStyle stageStyle) {
            this.stage.initStyle(stageStyle);
            return this;
        }

        public BuilderTela telaCheiaTelaExter() {
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            this.stage.setWidth(bounds.getWidth());
            this.stage.setHeight(bounds.getHeight());
            return this;
        }

        public BuilderTela exibirEsperarTelaExter() {
            this.exibirEsperarTelaExterna = true;
            return this;
        }

        public Utils build() {
            // Valida o caminho FXML
            if (this.loader.getLocation() == null) {
                new BuilderExibeMsg()
                        .addTituloJanela("Erro")
                        .addMsgCabecalho("Ocorreu um erro ao abrir a janela.")
                        .addMsgConteudo("O caminho FXML não foi encontrado.")
                        .addTipoMsg(Alert.AlertType.ERROR)
                        .build();
                return null;
            }

            if (!telaExterna && !telaInterna) {
                new BuilderExibeMsg()
                        .addTituloJanela("Erro")
                        .addMsgCabecalho("Ocorreu um erro ao abrir a janela.")
                        .addMsgConteudo("Tipo de tela não definida.")
                        .addTipoMsg(Alert.AlertType.ERROR)
                        .build();
                return null;
            }

            if (telaInterna) {
                retornaTelaInter();
            } else if (telaExterna) {
                retornaTelaExter();
            }

            return new Utils(this);
        }

        private Object controller() {
            return this.loader.getController();
        }

        // Constroi tela interna
        private void retornaTelaInter() {
            try {
                AnchorPane pane = loader.load();
                AnchorPane.setTopAnchor(pane, 0.0);
                AnchorPane.setRightAnchor(pane, 0.0);
                AnchorPane.setLeftAnchor(pane, 0.0);
                AnchorPane.setBottomAnchor(pane, 0.0);
                anchorPane.getChildren().clear();
                anchorPane.getChildren().addAll(pane);
            } catch (IOException ex) {
                new BuilderExibeMsg()
                        .addTituloJanela("Erro")
                        .addMsgCabecalho("Ocorreu um erro ao abrir a janela.")
                        .addMsgConteudo(UtilsAntigo.class.getName() + "\n" + ex.toString())
                        .addTipoMsg(Alert.AlertType.ERROR)
                        .build();
            }
        }

        private void retornaTelaExter() {
            try {
                Parent parent = (Parent) loader.load();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                if (!exibirEsperarTelaExterna) {
                    stage.showAndWait();
                } else {
                    stage.show();
                }

            } catch (IOException ex) {
                new BuilderExibeMsg()
                        .addTituloJanela("Erro")
                        .addMsgCabecalho("Ocorreu um erro ao abrir a janela.")
                        .addMsgConteudo(UtilsAntigo.class.getName() + "\n" + ex.toString())
                        .addTipoMsg(Alert.AlertType.ERROR)
                        .build();
            }
        }
    }

    public static class BuilderExibeMsg {

        private String tituloJanela;
        private String msgCabecalho;
        private String msgConteudo;
        private String tipoAlerta;

        public BuilderExibeMsg() {
        }

        public BuilderExibeMsg addTipoMsg(Alert.AlertType alertType) {
            this.tipoAlerta = alertType.toString();
            return this;
        }

        public BuilderExibeMsg addTituloJanela(String tituloJanela) {
            this.tituloJanela = tituloJanela;
            return this;
        }

        public BuilderExibeMsg addMsgCabecalho(String msgCabecalho) {
            this.msgCabecalho = msgCabecalho;
            return this;
        }

        public BuilderExibeMsg addMsgConteudo(String msgConteudo) {
            this.msgConteudo = msgConteudo;
            return this;
        }

        public Alert build() {
            Alert alert = new Alert(Alert.AlertType.valueOf(tipoAlerta));
            alert.setTitle(tituloJanela);
            alert.setHeaderText(msgCabecalho);
            alert.setContentText(msgConteudo);
            alert.showAndWait();

            return alert;
        }
    }

}
