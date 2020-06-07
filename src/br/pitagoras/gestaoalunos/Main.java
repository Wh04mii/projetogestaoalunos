package br.pitagoras.gestaoalunos;

import br.pitagoras.gestaoalunos.common.Utils;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        
        new Utils.Tela()
                .addCaminhoFXML("/br/pitagoras/gestaoalunos/view/FXMLCadastroCurso.fxml")
                .ehTelaExterna(true)
                .redimensionarTelaExter(false)
           //     .estiloTelaExter(StageStyle.UNDECORATED)
                .centralizarTelaExter()
                .construir();       
       
    }

    public static void main(String[] args) {        
        launch(args);
    }
}
