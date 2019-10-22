package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Controller {
    private final static String inputFilename = ".\\input.jpg";
    private final static String saveFilename = ".\\result.png";
    @FXML private ImageView sourceImageView, resultImageView;

    private FileChooser fileChooser = new FileChooser();
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    void init() {
        alert.setTitle("¯\\_(ツ)_/¯");
        alert.setHeaderText("Ошибка");
    }

    // File
    @FXML private void onOpen(ActionEvent event) {
        File file = fileChooser.showOpenDialog(null);
        if (file == null)
            return;

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            if (bufferedImage == null)
                throw new IOException();
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            sourceImageView.setImage(image);
            sourceImageView.setFitWidth(image.getWidth());
            sourceImageView.setFitHeight(image.getHeight());
        }
        catch (IOException e) {
            alert.setContentText("Ошибка открытия файла.");
            alert.showAndWait();
        }
    }

    @FXML private void onFilter(ActionEvent event) {
        Image image = sourceImageView.getImage();
        if (image == null) {
            alert.setContentText("Изображение не выбрано.");
            alert.showAndWait();
            return;
        }

        BufferedImage result = PrewittFilter.make(SwingFXUtils.fromFXImage(image, null));

        resultImageView.setImage(SwingFXUtils.toFXImage(result, null));
        resultImageView.setFitWidth(result.getWidth());
        resultImageView.setFitHeight(result.getHeight());
    }

    @FXML private void onSave(ActionEvent event) {
        Image image = resultImageView.getImage();
        if (image == null) {
            alert.setContentText("Нечего сохранить. (╥_╥)");
            alert.showAndWait();
            return;
        }

        File file = fileChooser.showSaveDialog(null);
        if (file == null)
            return;

        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ImageIO.write(bufferedImage, "png", file);
        }
        catch (IOException e) {
            alert.setContentText("Ошибка сохранения файла.");
            alert.showAndWait();
        }
    }

    // ?
    @FXML private void onAuthor(ActionEvent event) {
        Alert authorAlert = new Alert(Alert.AlertType.INFORMATION);
        authorAlert.setTitle("( ͡° ͜ʖ ͡°)");
        authorAlert.setHeaderText("Автор");
        authorAlert.setContentText("Студент МАИ\nГруппа М8О-313Б-17\nСеменов С.Д.");
        authorAlert.showAndWait();
    }
}
