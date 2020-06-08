package br.pitagoras.gestaoalunos.common;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Utils {

    // Objeto para acessar o controller externamente.
    private Object controller;

    public Object getController() {
        return controller;
    }

    private Utils(Tela tela) {
        this.controller = tela.controller();
    }

    // Classe estática implementando o pattern Builder que vai retornar a tela.
    public static class Tela {

        /*  
            Nomenclatura
            TelaInter = tela interna, abre sobre o anchorPane;
            TelaExter = janela externa;
         */
        private AnchorPane anchorPane;
        private FXMLLoader loader;
        private Stage stage;
        private boolean telaInterna;
        private boolean telaExterna;
        private boolean exibirEsperarTelaExter;

        // Construtor inicializando o loader.
        public Tela() {
            this.loader = new FXMLLoader();
            this.telaInterna = false;
            this.telaExterna = false;
        }

        public Tela addCaminhoFXML(String caminho) {
            this.loader.setLocation(getClass().getResource(caminho));
            return this;
        }

        public Tela ehTelaInterna(boolean resposta) {
            this.telaInterna = true;
            return this;
        }

        public Tela ehTelaExterna(boolean resposta) {
            this.stage = new Stage();
            this.telaExterna = true;
            return this;
        }

        public Tela addAnchorPaneTelaInter(AnchorPane anchorPane) {
            this.anchorPane = anchorPane;
            return this;
        }

        public Tela addTituloTelaExter(String titulo) {
            stage.setTitle(titulo);
            return this;
        }

        public Tela redimensionarTelaExter(boolean resposta) {
            this.stage.setResizable(resposta);
            return this;
        }

        public Tela centralizarTelaExter() {
            this.stage.centerOnScreen();
            return this;
        }

        public Tela estiloTelaExter(StageStyle stageStyle) {
            this.stage.initStyle(stageStyle);
            return this;
        }

        public Tela exibirTelaCheiaExter() {
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            this.stage.setWidth(bounds.getWidth());
            this.stage.setHeight(bounds.getHeight());
            return this;
        }

        public Tela exibirEsperarTelaExter() {
            this.exibirEsperarTelaExter = true;
            return this;
        }

        public Utils construir() {
            // Verifica se o caminho do FXML é válido.
            if (this.loader.getLocation() == null) {
                new Mensagem()
                        .addMsgCabecalho("Ocorreu um erro ao abrir a janela.")
                        .addMsgConteudo("O caminho FXML não foi encontrado.")
                        .addTipoMsg(Alert.AlertType.ERROR)
                        .exibir();
                return null;
            }

            // Verifica se foi especificado o tipo de tela a ser retornado.
            if (!telaExterna && !telaInterna) {
                new Mensagem()
                        .addMsgCabecalho("Ocorreu um erro ao abrir a janela.")
                        .addMsgConteudo("Tipo de tela não definida.")
                        .addTipoMsg(Alert.AlertType.ERROR)
                        .exibir();
                return null;
            }

            // Verifica qual tipo de tela vai retornar.
            if (telaInterna) {
                retornaTelaInter();
            } else if (telaExterna) {
                retornaTelaExter();
            }

            return new Utils(this);
        }

        // Retorna o controller para acessar os métodos do FXML acessado.
        private Object controller() {
            return this.loader.getController();
            
        }

        // Metódo responsável por construir e retornar a tela interna.
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
                new Mensagem()
                        .addMsgCabecalho("Ocorreu um erro ao abrir a janela.")
                        .addMsgConteudo(Utils.class.getName() + "\n" + ex.toString())
                        .addTipoMsg(Alert.AlertType.ERROR)
                        .exibir();
            }
            
        }

        // Metódo resposável por construir e retornar a tela externa.
        private void retornaTelaExter() {
            try {
                Parent parent = (Parent) loader.load();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                // Verifica se o stage vai exibir e aguardar o usuário.
                if (!exibirEsperarTelaExter) {
                    stage.showAndWait();
                } else {
                    stage.show();
                }
            } catch (IOException ex) {
                new Mensagem()
                        .addMsgCabecalho("Ocorreu um erro ao abrir a janela.")
                        .addMsgConteudo(Utils.class.getName() + "\n" + ex.toString())
                        .addTipoMsg(Alert.AlertType.ERROR)
                        .exibir();
            }
            
        }
    }

    // Classe estática implementando o pattern Builder que vai retornar um Alert.
    public static class Mensagem {
        //   private String tituloJanela;
        private String msgCabecalho;
        private String msgConteudo;
        private String tipoAlerta;
        private double initialX;
        private double initialY;

        public Mensagem() {
        }

        public Mensagem addTipoMsg(Alert.AlertType alertType) {
            this.tipoAlerta = alertType.toString();
            return this;
        }

        /*   public Mensagem addTituloJanela(String tituloJanela) {
            this.tituloJanela = tituloJanela;
            return this;
        } */
        public Mensagem addMsgCabecalho(String msgCabecalho) {
            this.msgCabecalho = msgCabecalho;
            return this;
        }

        public Mensagem addMsgConteudo(String msgConteudo) {
            this.msgConteudo = msgConteudo;
            return this;
        }

        // Retornar o alert.
        public Alert exibir() {
            // Novo Alert.
            Alert alert = new Alert(Alert.AlertType.valueOf(tipoAlerta));
            // Título. 
            //alert.setTitle(tituloJanela);
            // Cabeçalho.
            alert.setHeaderText(msgCabecalho);
            // Conteúdo.
            alert.setContentText(msgConteudo);
            // Início sem decoração de janela.
            alert.initStyle(StageStyle.TRANSPARENT);
            // Limpa a cor de background.
            alert.getDialogPane().getScene().setFill(Color.TRANSPARENT);
            // Limpa o ícone.
            alert.setGraphic(null);

            // Verifica o tipo de alerta que vai ser exibido.
            if (this.tipoAlerta.equals("CONFIRMATION")) {
                // Limpa os botões existentes.                
                alert.getButtonTypes().clear();
                // Adicionar botões de sim/não.
                alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
            }

            // Metódo para permitir mover a tela quando o Alert não tem as decorações de janela.
            moverAlert(alert.getDialogPane());
            // Metódo para aplicar estilo css.
            aplicarCssAlert(alert.getDialogPane());
            // Metódo para centralizar os botões da ButoonBar do Alert.
            centralizarBotoes(alert.getDialogPane());

            alert.showAndWait();

            return alert;
        }

        // Metódo para permitir mover a tela quando o Alert não tem as decorações de janela.
        private void moverAlert(DialogPane dialogPane) {
            // Fonte: https://stackoverflow.com/questions/11780115/moving-an-undecorated-stage-in-javafx-2
            dialogPane.setOnMousePressed((MouseEvent me) -> {
                if (me.getButton() != MouseButton.MIDDLE) {
                    initialX = me.getSceneX();
                    initialY = me.getSceneY();
                }
            });

            dialogPane.setOnMouseDragged((MouseEvent event) -> {
                dialogPane.getScene().getWindow().setX(event.getScreenX() - initialX);
                dialogPane.getScene().getWindow().setY(event.getScreenY() - initialY);
            });

        }

        // Metódo para aplicar estilo css.
        private void aplicarCssAlert(DialogPane dialogPane) {
            dialogPane.getStylesheets()
                    .add(getClass()
                            .getResource("/br/pitagoras/gestaoalunos/res/css/Style.css")
                            .toExternalForm());

        }

        // Metódo para centralizar os botões da ButoonBar do Alert.
        private void centralizarBotoes(DialogPane dialogPane) {
            // Fonte: https://stackoverflow.com/questions/36009764/how-to-align-ok-button-of-a-dialog-pane-in-javafx
            Region spacer = new Region();
            ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);
            HBox.setHgrow(spacer, Priority.ALWAYS);
            dialogPane.applyCss();
            HBox hboxDialogPane = (HBox) dialogPane.lookup(".container");
            hboxDialogPane.getChildren().add(spacer);

        }
    }

}
