package br.pitagoras.gestaoalunos;

import br.pitagoras.gestaoalunos.common.Utils;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        new Utils.Tela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLLogin.fxml")
                .ehTelaExterna(true)
                .redimensionarTelaExter(false)
                .estiloTelaExter(StageStyle.UNDECORATED)
                .centralizarTelaExter()
                .constroi();       
       
    }

    public static void main(String[] args) {        
        launch(args);
    }
}
