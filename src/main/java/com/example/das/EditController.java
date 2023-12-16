package com.example.das;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditController {
    @FXML

    private TextField text_num, text_client, text_emp, text_date, text_shop,
            text_address;

    private Stage dialog;

    private Orders orders;

    @FXML
    void edit(ActionEvent event) {
        if (!text_num.getText().isEmpty() && !text_client.getText().isEmpty()
                && !text_emp.getText().isEmpty() && !text_date.getText().isEmpty()
                && !text_shop.getText().isEmpty() && !text_address.getText().isEmpty()){
            orders.setId(text_num.getText());
            orders.setClient(text_client.getText());
            orders.setEmp(text_emp.getText());
            orders.setDate(text_date.getText());
            orders.setShop(text_shop.getText());
            orders.setAddress(text_address.getText());
            dialog.close();}
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialog);
            alert.setTitle("Пустое поле!");
            alert.setHeaderText("Одно или несколько полей пусты");
            alert.showAndWait();
        }
    }

    @FXML
    void cancel(ActionEvent event) {dialog.close();}
    public void getdialog(Stage dialogStage) {this.dialog = dialogStage;}
    public void getagreeemet(Orders orders) {
        text_num.setText(orders.getId());
        text_client.setText(orders.getClient());
        text_emp.setText(orders.getEmp());
        text_date.setText(orders.getDate());
        text_shop.setText(orders.getShop());
        text_address.setText(orders.getAddress());

        this.orders = orders;}
}
