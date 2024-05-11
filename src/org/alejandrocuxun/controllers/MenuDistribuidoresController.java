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
import org.alejandrocuxun.models.Distribuidores;
import org.alejandrocuxun.systems.Main;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class MenuDistribuidoresController implements Initializable {
    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfDistribuidorId, tfNombreDistribuidor, tfDescripcionDistribuidor, tfNit, tfTelefono, tfWeb;
    @FXML
    TableView tblDistribuidores;
    @FXML
    TableColumn colDistribuidorId, colNombreDistribuidor, colDescripcionDistribuidor, colNit, colTelefono, colWeb;
    @FXML
    Button btnGuardar, btnVaciar, btnRegresar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfDistribuidorId.getText().equals("")){
                agregarDistribuidores();
                cargarDatos();
            }else{
                editarDistribuidores();
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarForm();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }    

    public void cargarDatos(){
        tblDistribuidores.setItems(listarDistribuidores());
        colDistribuidorId.setCellValueFactory(new PropertyValueFactory<Distribuidores, Integer>("distribuidorId"));
        colNombreDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("nombreDistribuidor"));
        colDescripcionDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("descripcionDistribuidor"));
        colNit.setCellValueFactory(new PropertyValueFactory<Distribuidores, String>("nitDistribuidor"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Distribuidores, Integer>("Telefono"));
        colWeb.setCellValueFactory(new PropertyValueFactory<Distribuidores, Integer>("web"));
    }
    
    public void vaciarForm(){
        tfDistribuidorId.clear();
        tfNombreDistribuidor.clear();
        tfDescripcionDistribuidor.clear();
        tfNit.clear();
        tfTelefono.clear();
        tfWeb.clear();
    }
    
    @FXML
    public void cargarForm(){
        Distribuidores ts = (Distribuidores)tblDistribuidores.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfDistribuidorId.setText(Integer.toString(ts.getDistribuidorId()));
            tfNombreDistribuidor.setText(Integer.toString(ts.getDistribuidorId()));
            tfDescripcionDistribuidor.setText(Integer.toString(ts.getDistribuidorId()));
            tfNit.setText(Integer.toString(ts.getDistribuidorId()));
            tfTelefono.setText(Integer.toString(ts.getDistribuidorId()));
            tfWeb.setText(Integer.toString(ts.getDistribuidorId()));
        }
    }
    
    public ObservableList<Distribuidores> listarDistribuidores(){
        ArrayList<Distribuidores> distribuidores = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDistribuidores()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String descripcionDistribuidor = resultSet.getString("descripcionDistribuidor");
                int nitDistribuidor = resultSet.getInt("nitDistribuidor");
                int Telefono = resultSet.getInt("Telefono");
                String web = resultSet.getString("web");
                
                distribuidores.add(new Distribuidores(distribuidorId, nombreDistribuidor,descripcionDistribuidor, nitDistribuidor, Telefono, web));
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
        
        return FXCollections.observableList(distribuidores);
    }
    
    public void agregarDistribuidores(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDistribuidores(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfDistribuidorId.getText());
            statement.setString(2, tfNombreDistribuidor.getText());
            statement.setString(3, tfDescripcionDistribuidor.getText());
            statement.setString(4, tfNit.getText());
            statement.setString(5, tfTelefono.getText());
            statement.setString(7, tfWeb.getText());
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
    
    public void editarDistribuidores(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarDistribuidores(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            statement.setString(2, tfNombreDistribuidor.getText());
            statement.setString(3, tfDescripcionDistribuidor.getText());
            statement.setString(4, tfNit.getText());
            statement.setString(5, tfTelefono.getText());
            statement.setString(6, tfWeb.getText());
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
