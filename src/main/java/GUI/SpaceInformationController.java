package GUI;

import Board.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by userhp on 28/03/2016.
 */
public class SpaceInformationController {

    public Button SaveChangesButton;
    private Space lastSpaceLoaded;

    public GridPane SpaceInfo;
    public ComboBox GroupComboBox;
    public TextField MortgagePriceTextField;
    public TextField CostTextField;
    public TextField HouseCostTextField;
    public TextField BaseRentTextField;
    public TextField OneHouseRentTextField;
    public TextField TwoHouseRentTextField;
    public TextField ThreeHouseRentTextField;
    public TextField FourHouseRentTextField;
    public TextField HotelRentTextField;
    public TextField NameOfSpaceTextField;
    public Text MortgagePriceLabel;
    public Text CostLabel;
    public Text HouseCostLabel;
    public Text BaseRentLabel;
    public Text OneHouseRentLabel;
    public Text TwoHouseRentLabel;
    public Text ThreeHouseRentLabel;
    public Text FourHouseRentLabel;
    public Text HotelRentLabel;

    public void initialize() {
        SpaceInfo.setVisible(false);
        ArrayList<Group> groups = new ArrayList<Group>(Arrays.asList(Group.values()));
        GroupComboBox.setItems(FXCollections.observableArrayList(groups));

        GroupComboBox.valueProperty().addListener(new ChangeListener<Group>() {
            @Override
            public void changed(ObservableValue<? extends Group> observable, Group oldValue, Group newValue) {
                switch (newValue) {
                    case Utility:
                        CostLabel.setVisible(true);
                        MortgagePriceLabel.setVisible(true);
                        BaseRentLabel.setVisible(false);
                        OneHouseRentLabel.setVisible(false);
                        TwoHouseRentLabel.setVisible(false);
                        ThreeHouseRentLabel.setVisible(false);
                        FourHouseRentLabel.setVisible(false);
                        HotelRentLabel.setVisible(false);
                        HouseCostLabel.setVisible(false);
                        CostTextField.setVisible(true);
                        MortgagePriceTextField.setVisible(true);
                        BaseRentTextField.setVisible(false);
                        OneHouseRentTextField.setVisible(false);
                        TwoHouseRentTextField.setVisible(false);
                        ThreeHouseRentTextField.setVisible(false);
                        FourHouseRentTextField.setVisible(false);
                        HotelRentTextField.setVisible(false);
                        HouseCostTextField.setVisible(false);
                        break;
                    case Station:
                        CostLabel.setVisible(true);
                        MortgagePriceLabel.setVisible(true);
                        BaseRentLabel.setVisible(false);
                        OneHouseRentLabel.setVisible(false);
                        TwoHouseRentLabel.setVisible(false);
                        ThreeHouseRentLabel.setVisible(false);
                        FourHouseRentLabel.setVisible(false);
                        HotelRentLabel.setVisible(false);
                        HouseCostLabel.setVisible(false);
                        CostTextField.setVisible(true);
                        MortgagePriceTextField.setVisible(true);
                        BaseRentTextField.setVisible(false);
                        OneHouseRentTextField.setVisible(false);
                        TwoHouseRentTextField.setVisible(false);
                        ThreeHouseRentTextField.setVisible(false);
                        FourHouseRentTextField.setVisible(false);
                        HotelRentTextField.setVisible(false);
                        HouseCostTextField.setVisible(false);
                        break;
                    case GO:
                        CostLabel.setVisible(false);
                        MortgagePriceLabel.setVisible(false);
                        BaseRentLabel.setVisible(false);
                        OneHouseRentLabel.setVisible(false);
                        TwoHouseRentLabel.setVisible(false);
                        ThreeHouseRentLabel.setVisible(false);
                        FourHouseRentLabel.setVisible(false);
                        HotelRentLabel.setVisible(false);
                        HouseCostLabel.setVisible(false);
                        CostTextField.setVisible(false);
                        MortgagePriceTextField.setVisible(false);
                        BaseRentTextField.setVisible(false);
                        OneHouseRentTextField.setVisible(false);
                        TwoHouseRentTextField.setVisible(false);
                        ThreeHouseRentTextField.setVisible(false);
                        FourHouseRentTextField.setVisible(false);
                        HotelRentTextField.setVisible(false);
                        HouseCostTextField.setVisible(false);
                        break;
                    case Tax:
                        CostLabel.setVisible(false);
                        MortgagePriceLabel.setVisible(false);
                        BaseRentLabel.setVisible(false);
                        OneHouseRentLabel.setVisible(false);
                        TwoHouseRentLabel.setVisible(false);
                        ThreeHouseRentLabel.setVisible(false);
                        FourHouseRentLabel.setVisible(false);
                        HotelRentLabel.setVisible(false);
                        HouseCostLabel.setVisible(false);
                        CostTextField.setVisible(false);
                        MortgagePriceTextField.setVisible(false);
                        BaseRentTextField.setVisible(false);
                        OneHouseRentTextField.setVisible(false);
                        TwoHouseRentTextField.setVisible(false);
                        ThreeHouseRentTextField.setVisible(false);
                        FourHouseRentTextField.setVisible(false);
                        HotelRentTextField.setVisible(false);
                        HouseCostTextField.setVisible(false);
                        break;
                    case Jail:
                        CostLabel.setVisible(false);
                        MortgagePriceLabel.setVisible(false);
                        BaseRentLabel.setVisible(false);
                        OneHouseRentLabel.setVisible(false);
                        TwoHouseRentLabel.setVisible(false);
                        ThreeHouseRentLabel.setVisible(false);
                        FourHouseRentLabel.setVisible(false);
                        HotelRentLabel.setVisible(false);
                        HouseCostLabel.setVisible(false);
                        CostTextField.setVisible(false);
                        MortgagePriceTextField.setVisible(false);
                        BaseRentTextField.setVisible(false);
                        OneHouseRentTextField.setVisible(false);
                        TwoHouseRentTextField.setVisible(false);
                        ThreeHouseRentTextField.setVisible(false);
                        FourHouseRentTextField.setVisible(false);
                        HotelRentTextField.setVisible(false);
                        HouseCostTextField.setVisible(false);
                        break;
                    case GoToJail:
                        CostLabel.setVisible(false);
                        MortgagePriceLabel.setVisible(false);
                        BaseRentLabel.setVisible(false);
                        OneHouseRentLabel.setVisible(false);
                        TwoHouseRentLabel.setVisible(false);
                        ThreeHouseRentLabel.setVisible(false);
                        FourHouseRentLabel.setVisible(false);
                        HotelRentLabel.setVisible(false);
                        HouseCostLabel.setVisible(false);
                        CostTextField.setVisible(false);
                        MortgagePriceTextField.setVisible(false);
                        BaseRentTextField.setVisible(false);
                        OneHouseRentTextField.setVisible(false);
                        TwoHouseRentTextField.setVisible(false);
                        ThreeHouseRentTextField.setVisible(false);
                        FourHouseRentTextField.setVisible(false);
                        HotelRentTextField.setVisible(false);
                        HouseCostTextField.setVisible(false);
                        break;
                    case Chance:
                        CostLabel.setVisible(false);
                        MortgagePriceLabel.setVisible(false);
                        BaseRentLabel.setVisible(false);
                        OneHouseRentLabel.setVisible(false);
                        TwoHouseRentLabel.setVisible(false);
                        ThreeHouseRentLabel.setVisible(false);
                        FourHouseRentLabel.setVisible(false);
                        HotelRentLabel.setVisible(false);
                        HouseCostLabel.setVisible(false);
                        CostTextField.setVisible(false);
                        MortgagePriceTextField.setVisible(false);
                        BaseRentTextField.setVisible(false);
                        OneHouseRentTextField.setVisible(false);
                        TwoHouseRentTextField.setVisible(false);
                        ThreeHouseRentTextField.setVisible(false);
                        FourHouseRentTextField.setVisible(false);
                        HotelRentTextField.setVisible(false);
                        HouseCostTextField.setVisible(false);
                        break;
                    case CommunityChest:
                        CostLabel.setVisible(false);
                        MortgagePriceLabel.setVisible(false);
                        BaseRentLabel.setVisible(false);
                        OneHouseRentLabel.setVisible(false);
                        TwoHouseRentLabel.setVisible(false);
                        ThreeHouseRentLabel.setVisible(false);
                        FourHouseRentLabel.setVisible(false);
                        HotelRentLabel.setVisible(false);
                        HouseCostLabel.setVisible(false);
                        CostTextField.setVisible(false);
                        MortgagePriceTextField.setVisible(false);
                        BaseRentTextField.setVisible(false);
                        OneHouseRentTextField.setVisible(false);
                        TwoHouseRentTextField.setVisible(false);
                        ThreeHouseRentTextField.setVisible(false);
                        FourHouseRentTextField.setVisible(false);
                        HotelRentTextField.setVisible(false);
                        HouseCostTextField.setVisible(false);
                        break;
                    case FreeParking:
                        CostLabel.setVisible(false);
                        MortgagePriceLabel.setVisible(false);
                        BaseRentLabel.setVisible(false);
                        OneHouseRentLabel.setVisible(false);
                        TwoHouseRentLabel.setVisible(false);
                        ThreeHouseRentLabel.setVisible(false);
                        FourHouseRentLabel.setVisible(false);
                        HotelRentLabel.setVisible(false);
                        HouseCostLabel.setVisible(false);
                        CostTextField.setVisible(false);
                        MortgagePriceTextField.setVisible(false);
                        BaseRentTextField.setVisible(false);
                        OneHouseRentTextField.setVisible(false);
                        TwoHouseRentTextField.setVisible(false);
                        ThreeHouseRentTextField.setVisible(false);
                        FourHouseRentTextField.setVisible(false);
                        HotelRentTextField.setVisible(false);
                        HouseCostTextField.setVisible(false);
                        break;
                    default:
                        CostLabel.setVisible(true);
                        MortgagePriceLabel.setVisible(true);
                        BaseRentLabel.setVisible(true);
                        OneHouseRentLabel.setVisible(true);
                        TwoHouseRentLabel.setVisible(true);
                        ThreeHouseRentLabel.setVisible(true);
                        FourHouseRentLabel.setVisible(true);
                        HotelRentLabel.setVisible(true);
                        HouseCostLabel.setVisible(true);
                        CostTextField.setVisible(true);
                        MortgagePriceTextField.setVisible(true);
                        BaseRentTextField.setVisible(true);
                        OneHouseRentTextField.setVisible(true);
                        TwoHouseRentTextField.setVisible(true);
                        ThreeHouseRentTextField.setVisible(true);
                        FourHouseRentTextField.setVisible(true);
                        HotelRentTextField.setVisible(true);
                        HouseCostTextField.setVisible(true);
                }
            }


        });
    }

    public void fillFields(String location) {
        Space space = BoardHelper.getInstance().getSpaceOnBoard(Integer.valueOf(location));
        lastSpaceLoaded = space;
        NameOfSpaceTextField.setText(space.getName());
        GroupComboBox.getSelectionModel().select(space.getGroup());
        if (space instanceof Ownable) {
            CostTextField.setText(Integer.toString(((Ownable) space).getCost()));
            MortgagePriceTextField.setText(Integer.toString(((Ownable) space).getMortgagePrice()));
            if (space instanceof Property) {
                BaseRentTextField.setText(Integer.toString(((Property) space).getBaseRent()));
                HouseCostTextField.setText(Integer.toString(((Property) space).getHouseCost()));
                OneHouseRentTextField.setText(Integer.toString(((Property) space).getOneHouseRent()));
                TwoHouseRentTextField.setText(Integer.toString(((Property) space).getTwoHouseRent()));
                ThreeHouseRentTextField.setText(Integer.toString(((Property) space).getThreeHouseRent()));
                FourHouseRentTextField.setText(Integer.toString(((Property) space).getFourHouseRent()));
                HotelRentTextField.setText(Integer.toString(((Property) space).getHotelRent()));
            }
        }


    }

    public void saveInformation(Event event) {
        Space newSpace;
        Group group = (Group) GroupComboBox.getSelectionModel().getSelectedItem();

        String nameOfSpace = NameOfSpaceTextField.getText();
        int cost;
        int mtgPrice;
        int baseRent;
        int houseCost;
        int oneHouseRent;
        int twoHouseRent;
        int threeHouseRent;
        int fourHouseRent;
        int hotelRent;
        switch (group) {
            case Utility:
                cost = Integer.valueOf(CostTextField.getText());
                mtgPrice = Integer.valueOf(MortgagePriceTextField.getText());
                newSpace = new Utilities(nameOfSpace, lastSpaceLoaded.getLocation(), group, cost, mtgPrice);
                break;
            case Station:
                cost = Integer.valueOf(CostTextField.getText());
                mtgPrice = Integer.valueOf(MortgagePriceTextField.getText());
                newSpace = new Station(nameOfSpace, lastSpaceLoaded.getLocation(), group, cost, mtgPrice);
                break;
            case GO:
                newSpace = new GO(nameOfSpace, lastSpaceLoaded.getLocation(), group);
                break;
            case Tax:
                cost = Integer.valueOf(CostTextField.getText());
                newSpace = new Tax(nameOfSpace, lastSpaceLoaded.getLocation(), cost);
                break;
            case Jail:
                newSpace = new Jail(nameOfSpace, lastSpaceLoaded.getLocation(), group);
                break;
            case GoToJail:
                newSpace = new GoToJail(nameOfSpace, lastSpaceLoaded.getLocation(), group);
                break;
            case Chance:
                newSpace = new Chance(nameOfSpace, lastSpaceLoaded.getLocation(), group);
                break;
            case CommunityChest:
                newSpace = new CommunityChest(nameOfSpace, lastSpaceLoaded.getLocation(), group);
                break;
            case FreeParking:
                newSpace = new FreeParking(nameOfSpace, lastSpaceLoaded.getLocation(), group);
                break;
            default:
                cost = Integer.valueOf(CostTextField.getText());
                mtgPrice = Integer.valueOf(MortgagePriceTextField.getText());
                baseRent = Integer.valueOf(BaseRentTextField.getText());
                houseCost = Integer.valueOf(HouseCostTextField.getText());
                oneHouseRent = Integer.valueOf(OneHouseRentTextField.getText());
                twoHouseRent = Integer.valueOf(TwoHouseRentTextField.getText());
                threeHouseRent = Integer.valueOf(ThreeHouseRentTextField.getText());
                fourHouseRent = Integer.valueOf(FourHouseRentTextField.getText());
                hotelRent = Integer.valueOf(HotelRentTextField.getText());
                newSpace = new Property(nameOfSpace, group, lastSpaceLoaded.getLocation(),
                        baseRent, cost, mtgPrice, houseCost, oneHouseRent, twoHouseRent, threeHouseRent, fourHouseRent, hotelRent);
                break;
        }


        BoardHelper.getInstance().replaceSpaceOnBoard(lastSpaceLoaded, newSpace);
        lastSpaceLoaded = newSpace;

    }
}