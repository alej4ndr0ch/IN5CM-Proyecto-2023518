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
import org.alejandrocuxun.models.Empleados;
import org.alejandrocuxun.models.TicketSoporte;
import org.alejandrocuxun.systems.Main;

/**
 * FXML Controller class
 *
 * @author aleja
 */
public class MenuEmpleadosController implements Initializable {
    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfEmpledoId, tfNombreEmpledo, tfApellidoEmpledo, tfEntrada, tfSalida, tfSueldo;
    @FXML
    ComboBox cmbCargo;
    @FXML
    TableView tblEmpleados;
    @FXML
    TableColumn colEmpleadoId, colNombreEmpleado, colApellidoEmpleado, colEntrada, colSalida, colCargo, colSueldo;
    @FXML
    Button btnGuardar, btnLimpiar, btnRegresar;
    
    @FXML
        public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfEmpledoId.getText().equals("")){
                agregarEmpleado();
                cargarDatos();
            }else{
                editarEmpleado();
                cargarDatos();
            }
        }else if(event.getSource() == btnLimpiar){
            limpiarForm();
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cargarcmbCargo();
    }    

    public void cargarDatos(){
        tblEmpleados.setItems(listarEmpleados());
        colEmpleadoId.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("empleadoId"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombreEmpleado"));
        colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidoEmpleado"));
        colEntrada.setCellValueFactory(new PropertyValueFactory<Empleados, String>("horaEntrada"));
        colSalida.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("horaSalida"));
        colCargo.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("cargo"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("sueldo"));
    }
    
    public void cargarcmbCargo(){
        cmbCargo.getItems().add("En proceso");
        cmbCargo.getItems().add("Finalizado");
    }
    
    public void limpiarForm(){
        tfEmpledoId.clear();
        tfNombreEmpledo.clear();
        tfApellidoEmpledo.clear();
        tfEntrada.clear();
        tfSalida.clear();
        tfSueldo.clear();
        cmbCargo.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void cargarForm(){
        Empleados ts = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfEmpledoId.setText(Integer.toString(ts.getEmpleadoId()));
            tfNombreEmpledo.setText((ts.getNombreEmpleado()));
            tfApellidoEmpledo.setText((ts.getApellidoEmpleado()));
            tfEntrada.setText(Integer.toString(ts.getHoraEntrada()));
            tfSalida.setText(Integer.toString(ts.getHoraSalida()));
            tfSueldo.setText((ts.getCargo()));
            cmbCargo.getSelectionModel().select(0);
        }
    }
    
    public int obtenerIndexEmpleados(){
        int index = 0;
        String clienteTbl = ((TicketSoporte)tblEmpleados.getSelectionModel().getSelectedItem()).getCliente(); 
        for(int i = 0 ; i <= cmbCargo.getItems().size() ; i++){
            String clienteCmb = cmbCargo.getItems().get(i).toString();
            
            if(clienteTbl.equals(clienteCmb)){
                index = i;
                break;
            }
        }
        
        return index;
    }
    
    public ObservableList<Empleados> listarEmpleados(){
        ArrayList<Empleados> empleados = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarTicketsSoporte()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadosId");
                String nombreEmpleado = resultSet.getString("nombreEmpleados");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleados");
                int horaEntrada = resultSet.getInt("horaSalida");
                int horaSalida = resultSet.getInt("horaEntrada");
                String cargo = resultSet.getString("cargo");
                int sueldo = resultSet.getInt("sueldo");
                
                empleados.add(new Empleados(empleadoId, nombreEmpleado, apellidoEmpleado, horaEntrada, horaSalida, cargo, sueldo));
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
        
        return FXCollections.observableList(empleados);
    }
    
    public void agregarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarEmpleados(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
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
    
    public void editarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarEmpleados(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEmpledoId.getText()));
            statement.setString(2, tfNombreEmpledo.getText());
            statement.setString(3, tfApellidoEmpledo.getText());
            statement.setString(4, tfEntrada.getText());
            statement.setString(5, tfSalida.getText());
            statement.setString(6, tfSueldo.getText());
            statement.setString(7, (cmbCargo.getSelectionModel().getSelectedItem().toString()));
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
