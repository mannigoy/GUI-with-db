package com.example.csit228f2_2;

import javafx.application.Platform;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class StageHelper {
    private static final List<Stage> stages = new ArrayList<>();

    public static void addStage(Stage stage) {
        stages.add(stage);
        stage.setOnCloseRequest(event -> stages.remove(stage));
    }

    public static List<Stage> getStages() {
        return stages;
    }
}

