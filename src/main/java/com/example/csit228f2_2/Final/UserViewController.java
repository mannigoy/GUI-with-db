package com.example.csit228f2_2.Final;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserViewController {
    @FXML
    ImageView logo;

    @FXML
    public void initialize(){
        Image image =new Image("C:\\Users\\Bruker\\IdeaProjects\\GUI-with-db\\src\\Image\\piggy2.png");
        logo.setImage(image);
    }

}
