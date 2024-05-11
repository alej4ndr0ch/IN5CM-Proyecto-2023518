/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.alejandrocuxun.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.alejandrocuxun.dao.Conexion;
import org.alejandrocuxun.models.Promociones;
import org.alejandrocuxun.systems.Main;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class MenuPromocionesController implements Initializable {
    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfPromocionesId, tfDescripcionPromocion, tfPrecioPromocion, tfFechaInicio, tfFechaFinalizacion;
    @FXML
    ComboBox cmbProductoId;
    @FXML
    TableView tblPromociones;
    @FXML
    TableColumn colPromocionId, colDescripcionPromocion, colPrecioPromocion, colFechaInicio, colFechaFinalizacion, colProductoId;
    @FXML
    Button btnGuardar, btnVaciar, btnRegresar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfPromocionesId.getText().equals("")){
                agregarPromociones();
                cargarDatos();
            }else{
                editarPromociones();
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarForm();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cargarCmbProductoId();
    }    

    public void cargarDatos(){
        tblPromociones.setItems(listarPromociones());
        colPromocionId.setCellValueFactory(new PropertyValueFactory<Promociones, Integer>("promocionId"));
        colDescripcionPromocion.setCellValueFactory(new PropertyValueFactory<Promociones, String>("precioPromocion"));
        colPrecioPromocion.setCellValueFactory(new PropertyValueFactory<Promociones, String>("descripcionPromocion"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<Promociones, String>("fechaInicio"));
        colFechaFinalizacion.setCellValueFactory(new PropertyValueFactory<Promociones, Integer>("fechaFinalizacion"));
        colProductoId.setCellValueFactory(new PropertyValueFactory<Promociones, Integer>("productoId"));
    }
    
    public void cargarCmbProductoId(){
        cmbProductoId.getItems().add("En proceso");
        cmbProductoId.getItems().add("Finalizado");
    }
    
    public void vaciarForm(){
        tfPromocionesId.clear();
        tfDescripcionPromocion.clear();
        tfPrecioPromocion.clear();
        tfFechaInicio.clear();
        tfFechaFinalizacion.clear();
        cmbProductoId.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void cargarForm(){
        Promociones ts = (Promociones)tblPromociones.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfPromocionesId.setText(Integer.toString(ts.getPromocionId()));
            tfDescripcionPromocion.setText(Integer.toString(ts.getPromocionId()));
            tfPrecioPromocion.setText(Integer.toString(ts.getPromocionId()));
            tfFechaInicio.setText(Integer.toString(ts.getPromocionId()));
            tfFechaFinalizacion.setText(Integer.toString(ts.getPromocionId()));
            cmbProductoId.getSelectionModel().select(0);
        }
    }
    
    public ObservableList<Promociones> listarPromociones(){
        ArrayList<Promociones> promociones = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarPromociones()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int promocionId = resultSet.getInt("promocionId");
                String descripcionPromocion = resultSet.getString("descripcionPromocion");
                int precioPromocion = resultSet.getInt("precioPromocion");
                int fechaInicio = resultSet.getInt("fechaInicio");
                int fechaFinalizacion = resultSet.getInt("fechaFinalizacion");
                
                promociones.add(new Promociones(promocionId, descripcionPromocion, precioPromocion, fechaInicio, fechaFinalizacion));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        
        return FXCollections.observableList(promociones);
    }
    
    public void agregarPromociones(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarPromociones(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfPromocionesId.getText());
            statement.setString(2, tfDescripcionPromocion.getText());
            statement.setString(3, tfPrecioPromocion.getText());
            statement.setString(4, tfFechaInicio.getText());
            statement.setString(5, tfFechaFinalizacion.getText());
            statement.setInt(6, Integer.parseInt(cmbProductoId.getSelectionModel().getSelectedItem().toString()));
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void editarPromociones(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarTicketSoporte(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfPromocionesId.getText()));
            statement.setString(2, tfDescripcionPromocion.getText());
            statement.setString(3, tfPrecioPromocion.getText());
            statement.setString(4, tfFechaInicio.getText());
            statement.setString(5, tfFechaFinalizacion.getText());
            statement.setString(6, (cmbProductoId.getSelectionModel().getSelectedItem().toString()));
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}
