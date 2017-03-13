package projektPO.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import projektPO.model.Passenger;

public class PassengerDialogController {

    private Stage dialogStage;

    @FXML
    private void initialize() {
    }

    @FXML
    private Label passengerLabel, firstNamePassengerLabel, lastNamePassengerLabel, agePassengerLabel,
            PESELPassengerLabel, tripPassengerLabel, returnTripPassengerLabel;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPassenger(Passenger passenger) {
        passengerLabel.setText(passenger.getIntroduction());
        firstNamePassengerLabel.setText(passenger.getFirstName());
        lastNamePassengerLabel.setText(passenger.getLastName());
        agePassengerLabel.setText(passenger.getAge());
        PESELPassengerLabel.setText(passenger.getPesel());
        tripPassengerLabel.setText(passenger.originalTripStringified());
        returnTripPassengerLabel.setText(passenger.returnTripStringified());
    }

}
