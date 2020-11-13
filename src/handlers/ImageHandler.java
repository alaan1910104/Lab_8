package handlers;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.util.ArrayList;
import java.util.List;

public class ImageHandler {
    //classe en charge de couper l'image princial
    private Image mainImage;


    public void setMainImage(Image mainImage) {
        this.mainImage = mainImage;
    }


    // returner une liste de 9 subimages basés sur l'image principal
    public List<Image> cut(){
        List<Image> imageList = new ArrayList<>();

        if(this.mainImage == null){return null;}

        PixelReader reader = this.mainImage.getPixelReader();

        //chaque subimage est 200x200, parce que l'image princial est 600x600
        for(int counterY = 0; counterY < 3; counterY++){
            for (int counterX = 0; counterX < 3; counterX++){
                imageList.add(new WritableImage(reader, (counterX * 200), (counterY * 200), 200, 200));
            }
        }

        return imageList;
    }

}
