package GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by userhp on 28/03/2016.
 */
public class GuiController {


    public TextField SimulationsToRun;
    @FXML
    private CardController CardInformationController;
    @FXML
    private BoardController BoardController;
    @FXML
    private SpaceInformationController PropertyInformationController;
    @FXML
    private BottomBarController BarController;

    public void initialize() {
        BoardController.accessToControllers(CardInformationController, PropertyInformationController);
        BarController.accessToBoardController(BoardController);
    }


}


