package com.example.das;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddController{
    @FXML
    private TextField text_num, text_client, text_emp, text_date, text_shop,
            text_address;
    private Stage dialog;
    private ObservableList<Orders> deals = FXCollections.observableArrayList();

    @FXML
    void add(ActionEvent event) {
        if(!text_num.getText().isEmpty() && !text_client.getText().isEmpty() && !text_emp.getText().isEmpty()
                && !text_date.getText().isEmpty() && !text_shop.getText().isEmpty()
                && !text_address.getText().isEmpty()){
            deals.add(new Orders(
                    text_num.getText(), text_client.getText(), text_emp.getText(),
                    text_date.getText(), text_shop.getText(), text_address.getText()
            ));
            dialog.close();}
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialog);
            alert.setTitle("Пустое поле!");
            alert.setHeaderText("Одно или несколько полей пусты!");
            alert.showAndWait();
        }
    }

    @FXML
    void cancel(ActionEvent event) {dialog.close();}

    public void getdialog(Stage dialog)
    {this.dialog = dialog;}

    public void getagreements(ObservableList<Orders> deals) {this.deals = deals;}
}

