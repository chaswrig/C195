/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

Localization starts here. 
Localization techniques demonstrated by Malcom Wabara during Localization lecture

Additional Resource/Example: https://riptutorial.com/javafx/example/19339/loading-resource-bundle

 */
package fernandezgroupscheduler_caw;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.database;

/**
 *
 * @author chase
 */
public class FernandezGroupScheduler_CAW extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {

        Locale locale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("controller/Nat", locale);
        Parent root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"), rb);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        database.startConnection(); //Connects to the database.
        launch(args);
        database.closeConnection(); //Disconnects from the databse.
    }
    
}
