package br.pitagoras.gestaoalunos.common;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UtilsAntigo {

    public static void retornaJanelaInterna(FXMLLoader loader, AnchorPane anchorPane) {
        try {
            AnchorPane pane = loader.load();
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 0.0);
            anchorPane.getChildren().clear();
            anchorPane.getChildren().addAll(pane);
        } catch (IOException ex) {
            exibeMensagem("Erro", null, ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static void retornaJanela(FXMLLoader loader, String titulo) {
        try {
            Parent parent = (Parent) loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            exibeMensagem("Erro", null, ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static void exibeMensagem(String titleBar, String headerMessage, String infoMessage, Alert.AlertType alertType) {

        Alert alert = new Alert(alertType);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();

    }

}
