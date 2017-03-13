package projektPO.view;

import com.thoughtworks.xstream.XStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import projektPO.Main;
import projektPO.model.*;
import projektPO.utility.Process2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RootLayoutController {

    private List<Label> labels;
    private List<AnchorPane> panes;
    private Main mainApp;

    private Stage myStage;

    @FXML
    private Canvas rootCanvas, secondLayerCanvas, thirdLayerCanvas;

    @FXML
    private ComboBox<String> optionsBox, passengersBox, shipPassengersBox, locationPassengersBox;

    @FXML
    private Button createVehicleButton, serializeButton, deserializeButton, removePlaneButton;

    @FXML
    private Label planeSelectedLabel, planeCapacityLabel, planePassengersLabel, planePersonelLabel,
            planeFuelLabel, planePlanLabel, planeLocationLabel, planeNextStopLabel, planeIdLabel,
            planeMaxSpeedLabel, planeArmatureType;

    @FXML
    private Label shipSelectedLabel, shipCapacityLabel, shipPassengersLabel, shipLocationLabel, shipIdLabel,
            shipMaxSpeedLabel, shipArmatureType, shipCompanyLabel;

    @FXML
    private Label locationCapacityLabel,locationSelectedLabel, locationLocationLabel;

    @FXML
    private AnchorPane shipsPane, planesPane, locationsPane;

    @FXML
    private void initialize() {

        ObservableList<String> options = FXCollections.observableArrayList("Samolot pasażerski", "Samolot wojskowy",
                "Wycieczkowiec", "Lotniskowiec");
        this.optionsBox.getItems().addAll(options);

        thirdLayerCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        iWillFindYouAndIwillClickYou(e.getX(), e.getY());
                    }
                });

        passengersBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> selected, String oldValue, String newValue) {
                String arr[] = newValue.split(" ");
                int passengerId = Integer.parseInt(arr[arr.length-2]);

                ArrayList<Passenger> pass = World.getInstance().getPlanes().stream().map(vehicle -> vehicle.getPassengers()).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
                Passenger pas = pass.stream().filter(passenger -> passenger.getId() == passengerId).collect(Collectors.toCollection(ArrayList::new)).get(0);

                mainApp.showPassengerDialog(pas);
            }
        });

        shipPassengersBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> selected, String oldValue, String newValue) {
                String arr[] = newValue.split(" ");
                int passengerId = Integer.parseInt(arr[arr.length-2]);

                ArrayList<Passenger> pass = World.getInstance().getShips().stream().map(ship -> ship.getPassengers()).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
                Passenger pas = pass.stream().filter(passenger -> passenger.getId() == passengerId).collect(Collectors.toCollection(ArrayList::new)).get(0);

                mainApp.showPassengerDialog(pas);
            }
        });

        locationPassengersBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> selected, String oldValue, String newValue) {
                String arr[] = newValue.split(" ");
                int passengerId = Integer.parseInt(arr[arr.length-2]);

                ArrayList<Passenger> pass = World.getInstance().getLocations().stream().map(loc -> loc.getPassengers()).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
                Passenger pas = pass.stream().filter(passenger -> passenger.getId() == passengerId).collect(Collectors.toCollection(ArrayList::new)).get(0);

                mainApp.showPassengerDialog(pas);
            }
        });

        this.labels = Arrays.asList(planeSelectedLabel, planeCapacityLabel, planePassengersLabel, planePersonelLabel,
                planeFuelLabel, planePlanLabel, planeLocationLabel, planeNextStopLabel, planeIdLabel,
                planeMaxSpeedLabel, planeArmatureType, locationCapacityLabel, locationLocationLabel, locationSelectedLabel,
                shipSelectedLabel, shipCapacityLabel, shipPassengersLabel, shipLocationLabel, shipIdLabel,
                shipMaxSpeedLabel, shipArmatureType, shipCompanyLabel);

        this.panes = Arrays.asList(shipsPane, planesPane, locationsPane);
        for (AnchorPane pane : panes) {
            pane.setVisible(false);
            pane.setOpacity(1);
        }
        initDraw();
    }

    @FXML
    void removePlaneButtonClicked() {
        Vehicle vehicle = World.getInstance().getPlanes().stream().filter(plane -> plane.isSelected()).findFirst().get();

        vehicle.setDeleted(true);
        getThirdLayerContext().clearRect(0, 0, 1140, 900);
        panes.get(1).setVisible(false);

        World.getInstance().getVehicles().remove(vehicle);
    }

    @FXML
    void removeShipButtonClicked() {
        Vehicle vehicle = World.getInstance().getShips().stream().filter(ship -> ship.isSelected()).findFirst().get();

        vehicle.setDeleted(true);
        getLayerContext().clearRect(0, 0, 1140, 900);
        panes.get(0).setVisible(false);

        World.getInstance().getVehicles().remove(vehicle);
    }

    @FXML void handleMYButtonClicked() {
        if(optionsBoxValueNotSelected()) return;

        switch (getOptionsBoxValue()) {
            case "Samolot wojskowy":
                samolotWojskowy();
                break;
            case "Samolot pasażerski":
                samolotPasazerski();
                break;
            case "Lotniskowiec":
                lotniskowiec();
                break;
            case "Wycieczkowiec":
                wycieczkowiec();
                break;
        }

        disableButtonTemporarily();
    }

    public void setStage(Stage stage) {
        this.myStage = stage;
    }

    private String getOptionsBoxValue() {
        return optionsBox.getValue();
    }

    private boolean optionsBoxValueNotSelected() {
        return getOptionsBoxValue() == null;
    }

    private void disableButtonTemporarily() {
        createVehicleButton.setDisable(true);

        final Timeline animation = new Timeline(
                new KeyFrame(Duration.seconds(1.2),
                        new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent actionEvent) {
                                createVehicleButton.setDisable(false);
                            }
                        }));
        animation.setCycleCount(1);
        animation.play();
    }

    @FXML
    private void iWillFindYouAndIwillClickYou(double mouseX, double mouseY) {

        for(int i=0; i<panes.size(); i++)
            panes.get(i).setVisible(false);

        for(int i=0; i<labels.size(); i++)
            labels.get(i).setText("");

        for(Vehicle vehicle : World.getInstance().getVehicles()) vehicle.setSelected(false);

        for(int i=0; i<World.getInstance().getLocations().size(); i++) {
            Location loc = World.getInstance().getLocations().get(i);
            double dx = mouseX - loc.getCenterX();
            double dy = mouseY - loc.getCenterY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < 25) {
                locationCapacityLabel.setText(loc.capacityStringified());
                locationLocationLabel.setText(loc.currentLocationStringified());
                locationSelectedLabel.setText(loc.identityStringified());

                locationPassengersBox.getItems().clear();

                ArrayList<String> passengers = loc.getPassengers().stream().map(pas -> pas.optionsBoxName()).collect(Collectors.toCollection(ArrayList::new));
                if(passengers.isEmpty()) {
                    locationPassengersBox.setPlaceholder(new Label("Brak pasażerów"));
                } else {
                    locationPassengersBox.getItems().addAll(passengers);
                }

                panes.get(2).setVisible(true);
                return;
            }
        }


        for(int i=0; i<World.getInstance().getPlanes().size(); i++) {
            Vehicle vehicle = World.getInstance().getPlanes().get(i);
            double dx = mouseX - vehicle.getCenterX();
            double dy = mouseY - vehicle.getCenterY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < 30) {

                planeSelectedLabel.setText(vehicle.identityStringified());
                planeCapacityLabel.setText(vehicle.capacityStringified());
                planePassengersLabel.setText(vehicle.passengersStringified());
                planePersonelLabel.setText(vehicle.staffStringified());
                planeFuelLabel.setText(vehicle.fuelStringified());
                planePlanLabel.setText(vehicle.planStringified());
                planeLocationLabel.setText(vehicle.currentLocationStringified());
                planeNextStopLabel.setText(vehicle.nextStopStringified());
                planeIdLabel.setText(vehicle.idStringified());
                planeMaxSpeedLabel.setText(vehicle.speedStringified());
                planeArmatureType.setText(vehicle.armatureTypeStringified());
                vehicle.setSelected(true);

                passengersBox.getItems().clear();

                ArrayList<String> passengers = vehicle.getPassengers().stream().map(pas -> pas.optionsBoxName()).collect(Collectors.toCollection(ArrayList::new));
                if(passengers.isEmpty()) {
                    passengersBox.setPlaceholder(new Label("Brak pasażerów"));
                } else {
                    passengersBox.getItems().addAll(passengers);
                }

                panes.get(1).setVisible(true);
                return;
            }
        }

        for(int i=0; i<World.getInstance().getShips().size(); i++) {
            Vehicle vehicle = World.getInstance().getShips().get(i);
            double dx = mouseX - vehicle.getCenterX();
            double dy = mouseY - vehicle.getCenterY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < 30) {
                shipSelectedLabel.setText(vehicle.identityStringified());
                shipCapacityLabel.setText(vehicle.capacityStringified());
                shipPassengersLabel.setText(vehicle.passengersStringified());
                shipLocationLabel.setText(vehicle.currentLocationStringified());
                shipIdLabel.setText(vehicle.idStringified());
                shipMaxSpeedLabel.setText(vehicle.speedStringified());
                shipArmatureType.setText(vehicle.armatureTypeStringified());
                shipCompanyLabel.setText(vehicle.companyStringified());
                vehicle.setSelected(true);

                shipPassengersBox.getItems().clear();

                ArrayList<String> passengers = vehicle.getPassengers().stream().map(pas -> pas.optionsBoxName()).collect(Collectors.toCollection(ArrayList::new));
                if(passengers.isEmpty()) {
                    shipPassengersBox.setPlaceholder(new Label("Brak pasażerów"));
                } else {
                    shipPassengersBox.getItems().addAll(passengers);
                }

                panes.get(0).setVisible(true);

                return;
            }
        }
    }

    @FXML
    private void changeShipRoute() {
//        Vehicle vehicle = World.getInstance().getPlanes().stream().filter(ship -> ship.isSelected()).findFirst().get();
    }

    @FXML
    private void changePlaneRoute() {

    }

    @FXML
    private void emergencyLanding() {
        Vehicle plane = World.getInstance().getPlanes().stream().filter(p -> p.isSelected()).findFirst().get();
        plane.setEmergencyLandingRequired(true);
    }

    @FXML
    private void serializeButtonClicked() {

        FileOutputStream fos = null;
        String nazwaPliku = "./wynik.xml";
        try{
            fos = new FileOutputStream(nazwaPliku);
            fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
            byte[] bytes = new XStream().toXML(World.getInstance()).getBytes("UTF-8");
            fos.write(bytes);

        }catch (Exception e){
            System.err.println("Error in XML Write: " + e.getMessage());
        }
        finally{
            if(fos != null){
                try{
                    fos.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void deserializeButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(myStage);
    }

    private void newVehicleWithPlan(Plan plan, Vehicle vehicle, GraphicsContext context) {
        plan.selectRoute();

        vehicle.setPlan(plan);
        World.getInstance().addVehicle(vehicle);

        vehicle.draw(context);
    }

    private void samolotPasazerski() {

        PassengerFlightPlan plan = new PassengerFlightPlan();
        plan.selectRoute();

        Vehicle janusz = new PassengerPlane();
        janusz.setPlan(plan);
        World.getInstance().addVehicle(janusz);

        Random rand = new Random();
        Integer passengersCount = rand.nextInt(20) + 1;
        for (int i=0; i<passengersCount; i++) {
            Passenger pas = new Passenger(true);
            int locId = pas.getPlan().getOriginalRoute().get(0).getId();
            World.getInstance().getLocations().get(locId).addPassenger(pas);
        }
        janusz.draw(getThirdLayerContext());
    }

    private void samolotWojskowy() {
        if (World.getInstance().getAircraftCarriers().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Uwaga!");
            alert.setHeaderText("Błąd przy tworzeniu samolotu wojskowego");
            alert.setContentText("Aby wyprodukować odrzutowiec, najpierw stworz lotniskowiec!");

            alert.showAndWait();
        } else {
            nowyOdrzutowiec();
        }
    }

    private void nowyOdrzutowiec() {
        Random rand = new Random();
        int id = rand.nextInt(World.getInstance().getAircraftCarriers().size());
        Vehicle ship = World.getInstance().getAircraftCarriers().get(id);

        MilitaryFlightPlan plan = new MilitaryFlightPlan();
        plan.selectRoute();

        plan.getOriginalRoute().add(0, new Location(ship.getCenterX(), ship.getCenterY(), -1));

        Vehicle torpeda = new MilitaryPlane();
        torpeda.setPlan(plan);

        torpeda.setWeaponType(ship.getWeaponType());
        World.getInstance().addVehicle(torpeda);

        torpeda.draw(getThirdLayerContext());
    }

    private void lotniskowiec() {
        newVehicleWithPlan(new SailPlan(), new AircraftCarrier(),  getLayerContext());
    }

    private void wycieczkowiec() {

        SailPlan plan = new SailPlan();
        plan.selectRoute();

        Vehicle janusz = new CruiseShip();
        janusz.setPlan(plan);
        World.getInstance().addVehicle(janusz);

        Random rand = new Random();
        Integer passengersCount = rand.nextInt(25) + 1;
        for (int i=0; i<passengersCount; i++) {
            Passenger mariusz = new Passenger(false);
            int locId = mariusz.getPlan().getOriginalRoute().get(0).getId();
            World.getInstance().getDocks().get(locId).addPassenger(mariusz);
        }

        janusz.draw(getLayerContext());
    }

    private void initDraw() {
        World.getInstance().createLocations();
        World.getInstance().drawLocations(getCurrentContext());
    }

    private GraphicsContext getCurrentContext() {
        return rootCanvas.getGraphicsContext2D();
    }

    public GraphicsContext getThirdLayerContext() {
        return thirdLayerCanvas.getGraphicsContext2D();
    }

    private GraphicsContext getLayerContext() { return secondLayerCanvas.getGraphicsContext2D();

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
