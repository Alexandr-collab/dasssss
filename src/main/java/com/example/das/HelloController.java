package com.example.das;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class HelloController implements Initializable {

    @FXML
    private TableColumn<Orders, String> column_client, column_emp, column_num, column_date,
            column_shop, column_address;

    @FXML
    private Label l_client, l_emp, l_num, l_date,
            l_shop, l_address;

    @FXML
    private TableView<Orders> tableview;

    private Stage stage;

    ObservableList<Orders> deals = FXCollections.observableArrayList(
            new Orders("1", "Брокерская", "РТС", "Доллар", "AAPL",
                    "50"),
            new Orders("2", "Брокерская", "РТС", "Доллар", "ROSN",
                    "25"),
            new Orders("3", "Брокерская", "РТС", "Доллар", "GAZP",
                    "15"));

    @FXML
    void add(ActionEvent event) throws IOException {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("add-view.fxml"));
        dialog.setScene(new Scene(loader.load(),620, 620));
        AddController controller = loader.getController();
        controller.getdialog(dialog);
        controller.getagreements(deals);
        dialog.showAndWait();
        tableview.setItems(FXCollections.observableList(deals));
    }

    @FXML
    void delete(ActionEvent event) {
        if (tableview.getSelectionModel().getSelectedItem()!=null) {
            deals.remove(tableview.getSelectionModel().getSelectedItem());
            tableview.setItems(FXCollections.observableList(deals));
            l_num.setText("Запись №");
            l_date.setText("Дата продажи: ");
            l_emp.setText("Сотрудник: ");
            l_address.setText("Адрес магазина: ");
            l_shop.setText("Магазин: ");
            l_client.setText("Клиент: ");
        }
    }

    @FXML
    void edit(ActionEvent event) throws IOException {
        if (tableview.getSelectionModel().getSelectedItem()!=null) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("edit-view.fxml"));
            stage.setScene(new Scene(loader.load(),620, 620));
            EditController controller = loader.getController();
            controller.getdialog(stage);
            int id = deals.indexOf(tableview.getSelectionModel().getSelectedItem());
            controller.getagreeemet(deals.get(id));
            stage.showAndWait();
            tableview.setItems(deals);
            tableview.getSelectionModel().clearSelection();
            tableview.getSelectionModel().select(id);
            tableview.refresh();
        }
    }

    public void getStage(Stage stage)
    {this.stage = stage;}

    public File getTeachersFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(HelloApplication.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(HelloApplication.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Обновление заглавия сцены.
            stage.setTitle("Orders - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Обновление заглавия сцены.
            stage.setTitle("Orders");
        }
    }

    public void loadPersonDataFromFile(File file) {
        try {

            JAXBContext context = JAXBContext
                    .newInstance(DealsListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            // Чтение XML из файла и демаршализация.
            DealsListWrapper wrapper = (DealsListWrapper) um.unmarshal(file);

            deals = FXCollections.observableList(wrapper.getDeals());
            tableview.setItems(deals);

            // Сохраняем путь к файлу в реестре.
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не получилось открыть файл");
            alert.setContentText("Не получилось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(DealsListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Обёртываем наши данные об преподавателях.
            DealsListWrapper wrapper = new DealsListWrapper();
            wrapper.setDeals(deals);

            // Маршаллируем и сохраняем XML в файл.
            m.marshal(wrapper, file);

            // Сохраняем путь к файлу в реестре.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не получилось открыть файл");
            alert.setContentText("Не получилось загрузить данные из файла:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    @FXML
    private void new_file(ActionEvent event) {
        deals.clear();
        tableview.setItems(deals);
        setPersonFilePath(null);
    }

    @FXML
    private void open_file(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог загрузки файла
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * Сохраняет файл в файл адресатов, который в настоящее время открыт.
     * Если файл не открыт, то отображается диалог "save as".
     */
    @FXML
    private void save_file(ActionEvent event) {
        File personFile = getTeachersFilePath();
        if (personFile != null) {
            savePersonDataToFile(personFile);
        } else {
            save_as_file(event);
        }
    }

    /**
     * Открывает FileChooser, чтобы пользователь имел возможность
     * выбрать файл, куда будут сохранены данные
     */
    @FXML
    private void save_as_file(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Задаём фильтр расширений
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Показываем диалог сохранения файла
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            savePersonDataToFile(file);
        }
    }

    /**
     * Открывает диалоговое окно about.
     */
    @FXML
    private void help_about(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Продажи");
        alert.setHeaderText("Информация");
        alert.setContentText("Автор: Языков Александр \nИСТ-332");

        alert.showAndWait();
    }

    /**
     * Закрывает приложение.
     */
    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        column_num.setCellValueFactory(new PropertyValueFactory<Orders, String>("id"));
        column_client.setCellValueFactory(new PropertyValueFactory<Orders, String>("client"));
        column_emp.setCellValueFactory(new PropertyValueFactory<Orders, String>("emp"));
        column_date.setCellValueFactory(new PropertyValueFactory<Orders, String>("date"));
        column_shop.setCellValueFactory(new PropertyValueFactory<Orders, String>("shop"));
        column_address.setCellValueFactory(new PropertyValueFactory<Orders, String>("address"));

        tableview.setItems(deals);
        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Orders orders = tableview.getSelectionModel().getSelectedItem();
                l_num.setText("Запись №" + orders.getId());
                l_date.setText("Дата продажи: " + orders.getDate());
                l_emp.setText("Сотрудник: " + orders.getEmp());
                l_address.setText("Адрес магазина: " + orders.getAddress());
                l_shop.setText("Магазин: " + orders.getShop());
                l_client.setText("Клиент: " + orders.getClient());
            }
        });
    }
}