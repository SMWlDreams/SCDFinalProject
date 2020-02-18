README:

NOTE: This application only runs on Java 11. The Audio DLL in the Amazon Corretto Java 8 JRE is broken and crashes the game. In order to run the application, set the following as VM options:

--module-path ${PATH_TO_FX} --add-modules=javafx.controls,javafx.fxml,javafx.media