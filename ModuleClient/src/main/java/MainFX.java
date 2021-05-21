import bugtracing.service.IService;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MainFX extends Application {
    public static void main(String[] args){
        launch(args);
    }

    public static IService getService(){
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:ClientConfig.xml");
        return (IService)factory.getBean("ServiceApp");


    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LogIn.fxml"));
            Parent root = loader.load();
            LoginController ctrl = loader.getController();
            ctrl.setService(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("LogIn");
            primaryStage.show();
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error while starting the app" + ex);
            alert.showAndWait();


        }

    }

}
