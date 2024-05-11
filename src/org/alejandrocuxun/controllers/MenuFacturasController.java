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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.alejandrocuxun.dao.Conexion;
import org.alejandrocuxun.models.Facturas;
import org.alejandrocuxun.systems.Main;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class MenuFacturasController implements Initializable {
    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfFacturaId, tfHora, tfFecha, tfTotal;
    @FXML
    TableView tblFacturas;
    @FXML
    TableColumn colFacturaId, colFecha, colHora, colTotal, colEmpleadoId, colClienteId;
    @FXML
    Button btnGuardar, btnVaciar, btnRegresar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfFacturaId.getText().equals("")){
                //agregarTicket();
                cargarDatos();
            }else{
                //editarTicket();
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciar){
            //vaciarForm();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }    

    public void cargarDatos(){
        tblFacturas.setItems(listarFactura());
        colFacturaId.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("facturaId"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Facturas, String>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<Facturas, String>("hora"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Facturas, String>("total"));
        colEmpleadoId.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("empleadoId"));
        colClienteId.setCellValueFactory(new PropertyValueFactory<Facturas, Integer>("clienteId"));
    }

    
    public void vaciarForm(){
        tfFacturaId.clear();
        tfHora.clear();
        tfFecha.clear();
        tfTotal.clear();
    }
    
    @FXML
    public void cargarForm(){
        Facturas ts = (Facturas)tblFacturas.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfFacturaId.setText(Integer.toString(ts.getFacturaId()));
            tfHora.setText(Integer.toString(ts.getFacturaId()));
            tfFecha.setText(Integer.toString(ts.getFacturaId()));
            tfTotal.setText(Integer.toString(ts.getFacturaId()));
        }
    }
    
    public ObservableList<Facturas> listarFactura(){
        ArrayList<Facturas> facturas = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarFacturas()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int facturaId = resultSet.getInt("tfFacturaId");
                int fecha = resultSet.getInt("fecha");
                int hora = resultSet.getInt("hora");
                int clienteId = resultSet.getInt("clienteId");
                int empleadoId = resultSet.getInt("empleadoId");
                int total = resultSet.getInt("total");
                
                facturas.add(new Facturas(facturaId, fecha, hora, total, clienteId, empleadoId));
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
        
        return FXCollections.observableList(facturas);
    }
    
    
    
    public void agregarFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarFactura(?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfFacturaId.getText());
            statement.setString(2, tfFecha.getText());
            statement.setString(3, tfHora.getText());
            statement.setString(4, tfTotal.getText());
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
    
    public void editarFacturas(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarFacturas(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfFacturaId.getText()));
            statement.setInt(2, Integer.parseInt(tfFecha.getText()));
            statement.setInt(3, Integer.parseInt(tfHora.getText()));
            statement.setInt(4, Integer.parseInt(tfTotal.getText()));
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
