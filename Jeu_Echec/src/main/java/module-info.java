module iut.jeu_echec {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens iut.jeu_echec to javafx.fxml;
    opens iut.jeu_echec.controllers to javafx.fxml;
    exports iut.jeu_echec;
    exports iut.jeu_echec.controllers;
}