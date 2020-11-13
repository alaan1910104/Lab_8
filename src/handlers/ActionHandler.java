package handlers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ActionHandler {
    //Class en charge de gerer le drag and drop et la rotation


    public void setDragAndDrop(List<ImageView> imageViewList, List<Image> imageList){

        // methode pour initialiser tant le fonctionnement du drag et du drop dans chaque subimage
        for (ImageView imageView : imageViewList
             ) {

            // le drag obtiene l'image du imageview
            imageView.setOnDragDetected(event -> {
                Dragboard dragboard = imageView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(imageView.getImage());
                dragboard.setDragView(imageView.getImage());
                dragboard.setContent(cbContent);
            });

            //Changement de colour pour indiquer la subimage selectionée
            imageView.setOnDragEntered(event -> {
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setHue(10);
                imageView.setEffect(colorAdjust);
            });

            //returner a normal quand le mouse sort
            imageView.setOnDragExited(event -> {
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setHue(0);
                imageView.setEffect(colorAdjust);
            });

            // accepter le transfer
            imageView.setOnDragOver(event -> event.acceptTransferModes(TransferMode.MOVE));


            // obtenir l'image et la rotation de l'image source, et les inversé
            imageView.setOnDragDropped(event -> {

                Image tempImage =  imageView.getImage();

                double tempRotation = imageView.getRotate();

                ImageView source = (ImageView) event.getGestureSource();

                imageView.setImage(source.getImage());

                imageView.setRotate(source.getRotate());

                source.setRotate(tempRotation);

                source.setImage(tempImage);

                event.setDropCompleted(true);
            });

            // checker si l'image est correcte
            imageView.setOnDragDone(event ->
                compare(imageViewList, imageList)
            );

            // changer la rotation de l'image en clicker desus, et checker si l'image est correcte
            imageView.setOnMouseClicked(event -> {if(event.getButton() == MouseButton.PRIMARY){
                imageView.setRotate(imageView.getRotate() + 90);
                compare(imageViewList, imageList);
            }});
        }
    }

    //methode en charge de comparer les listes de subimages pour savoir si l'image est correcte
    public void compare(List<ImageView> imageViewList, List<Image> controlList){

        boolean confirmation = false;
        //obtenir les images du imageview
        List<Image> list1 = imageViewList.stream().
                map(ImageView::getImage).
                collect(Collectors.toList());

        //confirmer
        confirmation = list1.equals(controlList);

        //si quelque image n'est pas droite, l'image n'et pas correcte
        for (ImageView iv: imageViewList
             ) {
            if(iv.getRotate() % 360 != 0)
                confirmation = false;
        }

        // si touts les checks sont reussis, faire un pop-up de congratulation
        if (confirmation){
            Alert winAlert = new Alert(Alert.AlertType.CONFIRMATION);
            winAlert.setHeaderText("You did it boyo!");
            winAlert.setTitle("Yay!");
            winAlert.setContentText("press Ok to restart");

            ButtonType confirm = winAlert.showAndWait().get();

            //en click sur le button ok, reshuffle les immages et recomencer le jeu.
            if(confirm == ButtonType.OK){
                Collections.shuffle(list1);
                for (int counter  = 0; counter < controlList.size(); counter++){
                    imageViewList.get(counter).setImage(list1.get(counter));
                    imageViewList.get(counter).setRotate(Math.round(Math.random() * 4) * 90);
                }
            }
        }
    }
}
