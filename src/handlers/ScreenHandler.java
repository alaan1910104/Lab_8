package handlers;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScreenHandler {

    //Classe qui genere et sets le scene principal

    static final int screenSize = 600;
    private ImageHandler imageHandler;
    private ActionHandler actionHandler;


    public ScreenHandler(Stage mainStage){
        this.imageHandler = new ImageHandler();
        this.actionHandler = new ActionHandler();
        Scene mainscene = setScene();
        mainStage.setScene(mainscene);
        mainStage.setTitle("Lab 8!");
    }

    // methode en charge de generer le elements du UI
    private Scene setScene() {

        //Menus
        MenuBar mainMenuBar = new MenuBar();
        Menu mainMenu = new Menu("Choose your image");

        // Image defaut
        Image mainImage = new Image("images/mario_complet.jpg", screenSize, screenSize, false, false);

        RadioMenuItem
                choise1 = new RadioMenuItem("Image 1"),
                choise2 = new RadioMenuItem("Image 2"),
                choise3  = new RadioMenuItem("Image 3");

        ToggleGroup mainToggleGroup = new ToggleGroup();


        // pane principal
        GridPane mainPane = new GridPane();

        mainPane.setMaxSize(screenSize,screenSize);
        mainPane.setHgap(10);
        mainPane.setVgap(10);


        //Preparer l'image et la metre dans le pane
        prepareImage(mainImage, mainPane);

        // Set les actions des elements du menu
        choise1.setToggleGroup(mainToggleGroup);
        choise1.setOnAction(event -> prepareImage(mainImage, mainPane));

        choise2.setToggleGroup(mainToggleGroup);
        choise2.setOnAction(event -> prepareImage(
                new Image("images/Exodia.jpg",
                        screenSize,
                        screenSize,
                        false,
                        false
                ),
                mainPane
        ));

        choise3.setToggleGroup(mainToggleGroup);
        choise3.setOnAction(event -> prepareImage(
                new Image("images/PikaChu.jpg",
                        screenSize,
                        screenSize,
                        false,
                        false
                ),
                mainPane
        ));


        mainMenu.getItems().addAll(choise1,choise2,choise3);
        mainMenuBar.getMenus().add(mainMenu);


        // mettre tout dans une Vbox
        VBox mainBox = new VBox();
        mainBox.getChildren().addAll(mainMenuBar, mainPane);

        return new Scene(mainBox);
    }

    //Metode en charge de coupé l'image, generer listes de subimages et les mettre dans le Pane
    private void prepareImage(Image mainImage, GridPane mainPane){

        this.imageHandler.setMainImage(mainImage);

        List<Image> baseImageList = this.imageHandler.cut();
        List<Image> imageList = new ArrayList<>(baseImageList);
        Collections.shuffle(imageList);
        List<ImageView> viewList = new ArrayList<>();


        // mettre les subimages dns un page 3x3
        int indexCounter = 0;
        for (int counterY = 0; counterY < 3; counterY++) {
            for (int counterX = 0; counterX < 3; counterX++) {

                ImageView imageView = new ImageView();
                imageView.setImage(imageList.get(indexCounter));

                //generer un rotation random
                imageView.setRotate(Math.round(Math.random() * 4) * 90);

                viewList.add(imageView);

                mainPane.add(imageView, counterX, counterY);
                indexCounter++;
            }
        }

        // set le fonctionament des images
        this.actionHandler.setDragAndDrop(viewList, baseImageList);

    }
}
