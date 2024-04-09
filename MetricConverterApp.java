import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MetricConverterApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        Label lblInput = new Label("Input:");
        TextField txtInput = new TextField();
        Label lblResult = new Label();
        Button btnConvert = new Button("Convert");

        // Create layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add components to layout
        gridPane.add(lblInput, 0, 0);
        gridPane.add(txtInput, 1, 0);
        gridPane.add(btnConvert, 1, 1);
        gridPane.add(lblResult, 0, 2, 2, 1);

        // Set action for the convert button
        btnConvert.setOnAction(event -> {
            String input = txtInput.getText();
            if (input.equalsIgnoreCase("exit") || input.equals("-1")) {
                primaryStage.close();
            } else {
                try {
                    String[] parts = input.split("\\s*=\\s*");
                    if (parts.length == 2) {
                        String unit = parts[1].trim();
                        double value = Double.parseDouble(parts[0].trim());
                        double result = convert(value, unit);
                        lblResult.setText(String.format("%.2f %s is equal to %.2f %s", value, unit, result, getTargetUnit(unit)));
                    } else {
                        lblResult.setText("Invalid input format. Please input in the format: <value> <source_unit> = <target_unit>");
                    }
                } catch (NumberFormatException e) {
                    lblResult.setText("Invalid input format. Please enter a valid number.");
                } catch (UnsupportedOperationException e) {
                    lblResult.setText("Conversion not supported for the specified units.");
                }
            }
        });

        // Create scene and set it in stage
        Scene scene = new Scene(gridPane, 400, 200);
        primaryStage.setTitle("Metric Converter");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private double convert(double value, String sourceUnit) {
        switch (sourceUnit.toLowerCase()) {
            case "km":
                return value * 0.621371; // km to mile
            case "kg":
                return value * 2.20462; // kg to lb
            case "g":
                return value * 0.035274; // gram to ounce
            case "mm":
                return value * 0.0393701; // mm to inch
            default:
                throw new UnsupportedOperationException("Conversion not supported for the specified units.");
        }
    }

    private String getTargetUnit(String sourceUnit) {
        switch (sourceUnit.toLowerCase()) {
            case "km":
                return "miles";
            case "kg":
                return "pounds";
            case "g":
                return "ounces";
            case "mm":
                return "inches";
            default:
                throw new UnsupportedOperationException("Conversion not supported for the specified units.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
