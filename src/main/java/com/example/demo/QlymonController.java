package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class QlymonController implements Initializable {
    private MonHoc[] listTemp;
    @FXML
    private TableView<MonHoc> tableMH;

    @FXML
    private TableColumn<MonHoc, String> maMHColumn;

    @FXML
    private TableColumn<MonHoc, String> tenMHColumn;

    @FXML
    private TableColumn<MonHoc, Integer> soTinColumn;

    @FXML
    private TextField txtMaMH;

    @FXML
    private TextField txtTenMH;

    @FXML
    private TextField txtSoTC;

    private ObservableList<MonHoc> mhList;

    public MonHoc getNewMH(){
        MonHoc newMH = new MonHoc();
        newMH.setMaMonHoc(txtMaMH.getText());
        newMH.setTenMonHoc(txtTenMH.getText());
        newMH.setSoTinChi(Integer.parseInt(txtSoTC.getText()));
        return newMH;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<MonHoc> listTemp = new ArrayList<MonHoc>();
        MonHoc mh =new  MonHoc("CT001", "TDC", 2);
        listTemp.add(mh);

        listTemp = (ArrayList<MonHoc>)read();


        mhList = FXCollections.observableArrayList(listTemp);
        maMHColumn.setCellValueFactory(new PropertyValueFactory<MonHoc, String>("maMonHoc"));
        tenMHColumn.setCellValueFactory(new PropertyValueFactory<MonHoc, String>("tenMonHoc"));
        soTinColumn.setCellValueFactory(new PropertyValueFactory<MonHoc, Integer>("soTinChi"));
        tableMH.setItems(mhList);

        maMHColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableMH.getSortOrder().add(maMHColumn);
        tableMH.sort();
    }

    public void addMH(ActionEvent e){
        boolean check = true;
        if(txtMaMH.getText() == "" || txtTenMH.getText() == ""
                || txtSoTC.getText() == "" ){
            check = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Vui l??ng ??i???n ?????y ????? c??c th??ng tin!" );
            alert.showAndWait();
        }
        if(check == true) {
            MonHoc newMH = getNewMH();
            TableColumn<MonHoc, String> column = maMHColumn;
            List<String> columnData = new ArrayList<>();
            for (MonHoc item : tableMH.getItems()) {
                columnData.add(column.getCellObservableValue(item).getValue());
            }
            if(columnData.contains(newMH.getMaMonHoc())) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("M?? m??n h???c ???? t???n t???i");
                alert.showAndWait();
            }else {

                mhList.add(newMH);


                List<MonHoc> list = mhList.stream().collect(Collectors.toList());
                // converting list of string into string[] array.
                write(list);


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Th??m m??n h???c th??nh c??ng!");
                alert.showAndWait();
                setDefault();
            }
        }

    }

    public void deleteMH(ActionEvent e){
        MonHoc selected = tableMH.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("B???n c?? mu???n x??a m??n h???c n??y kh??ng?");
        alert.setContentText(null);

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == null) {}
        else if(option.get() == ButtonType.OK) {
            if(selected == null){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Warning");
                alert1.setHeaderText(null);
                alert1.setContentText("H??y ch???n m??n h???c ????? x??a!");
                alert1.showAndWait();
            }else {

                mhList.remove(selected);

                List<MonHoc> list = mhList.stream().collect(Collectors.toList());

                write(list);


                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("X??a m??n h???c th??nh c??ng!");
                alert1.showAndWait();
                setDefault();

            }
        } else if (option.get() == ButtonType.CANCEL) {

        } else {

        }
    }

    public void updateMH(ActionEvent e){
       boolean check = true;
        if(txtMaMH.getText() == "" || txtTenMH.getText() == ""
                || txtSoTC.getText() == "" ){
            check = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Vui l??ng ??i???n ?????y ????? c??c th??ng tin!" );
            alert.showAndWait();
        }
        if(check == true) {
            MonHoc newMH = getNewMH();
            int selected = tableMH.getSelectionModel().getSelectedIndex();

            mhList.set(selected, newMH);

            List<MonHoc> list = mhList.stream().collect(Collectors.toList());
            // converting list of string into string[] array.
            write(list);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("S???a th??ng tin m??n h???c th??nh c??ng!");
            alert.showAndWait();
            setDefault();

        }

    }

    public void selectItem(MouseEvent e){
        MonHoc selected = tableMH.getSelectionModel().getSelectedItem();
        if(selected != null){
            txtMaMH.setText(selected.getMaMonHoc());
            txtTenMH.setText(selected.getTenMonHoc());

            Integer stc = selected.getSoTinChi();
            txtSoTC.setText(stc.toString());

        }
    }

    public  void setDefault(){

        txtMaMH.setText("");
        txtTenMH.setText("");
        txtSoTC.setText("");
        txtTenMH.setPromptText("T??n m??n h???c");
        txtMaMH.setPromptText("M?? m??n h???c");
        txtSoTC.setPromptText("S??? t??n ch???");

    }

    public void mouseClick(MouseEvent e){
        setDefault();
    }


    public void exit(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene tableViewScene =  new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    public static Object read(){
        FileInputStream FIn = null;
        ObjectInputStream OIn = null;
        Object read = null;
        try {
            //Kh??? t???o ?????i t?????ng v???i ?????a ch??? truy???n v??o
            FIn = new FileInputStream("mydataMH.dat");
            OIn = new ObjectInputStream(FIn);
            read = OIn.readObject();
            return read;
        } catch (Exception e) {
            System.err.println(e);
        }
        finally {if (read != null)
            System.out.println("");
        else System.out.println("DOC FILE THAT BAI");
            try {
                FIn.close();
                OIn.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        return null;
    }
    public static void write(List<MonHoc> list){
        try {
            //B?????c 1: T???o ?????i t?????ng lu???ng v?? li??n k???t ngu???n d??? li???u
            ;
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("mydataMH.dat"));
            //sua sv


            //B?????c 2: Ghi m???ng ?????i t?????ng v??o file
            oos.writeObject(list);
            //B?????c 3: ????ng lu???ng
            oos.close();

        } catch (IOException ex) {
            System.out.println("Loi ghi file: "+ex);
            ex.printStackTrace();
        }

    }
}
