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
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.alejandrocuxun.dao.Conexion;
<<<<<<< HEAD
import org.alejandrocuxun.dto.CompraDTO;
=======
import org.alejandrocuxun.dto.ComprasDTO;
>>>>>>> e2186e805825bd595c799466183545218cd6968b
import org.alejandrocuxun.models.Compras;
import org.alejandrocuxun.systems.Main;
import org.alejandrocuxun.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
<<<<<<< HEAD
 * @author aleja
=======
 * @author informatica
>>>>>>> e2186e805825bd595c799466183545218cd6968b
 */
public class FormComprasController implements Initializable {
    private Main stage;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
   @FXML
<<<<<<< HEAD
    Button btnRegresar, btnGuardar;
   @FXML
   TextField tfCompraId, tfFecha;
=======
    Button btnRegresar,btnGuardar;
   
   @FXML
   TextField tfCompraId,tfFecha;
>>>>>>> e2186e805825bd595c799466183545218cd6968b
   
   @FXML
private void handleButtonAction(ActionEvent event) {
    if (event.getSource() == btnRegresar) {
<<<<<<< HEAD
        CompraDTO.getCompraDTO().setCompra(null);
        stage.menuComprasView();
    } else if (event.getSource() == btnGuardar) {
=======
        ComprasDTO.getCompraDTO().setCompra(null);
        stage.menuComprasView();
    } else if (event.getSource() == btnGuardar) {
        
>>>>>>> e2186e805825bd595c799466183545218cd6968b
            stage.menuComprasView();
        if (op == 2) {
            if (SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(505).get() == ButtonType.OK) {
                editarCompra();
<<<<<<< HEAD
                CompraDTO.getCompraDTO().setCompra(null);
=======
                ComprasDTO.getCompraDTO().setCompra(null);
>>>>>>> e2186e805825bd595c799466183545218cd6968b
                SuperKinalAlert.getInstance().mostrarAlertaInformacion(500);
                stage.menuComprasView();
            } else {
                stage.menuComprasView();
            }
        }
    }
}
    @Override
    public void initialize(URL location, ResourceBundle resources) {
<<<<<<< HEAD
        if(CompraDTO.getCompraDTO().getCompra() != null){
            cargarDatos(CompraDTO.getCompraDTO().getCompra());
        } 
=======
        if(ComprasDTO.getCompraDTO().getCompra() != null){
            cargarDatos(ComprasDTO.getCompraDTO().getCompra());
        }
        
>>>>>>> e2186e805825bd595c799466183545218cd6968b
    }    
    
    public void cargarDatos(Compras compra) {
    tfCompraId.setText(Integer.toString(compra.getCompraId()));
<<<<<<< HEAD
=======

>>>>>>> e2186e805825bd595c799466183545218cd6968b
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
    tfFecha.setText(formatoFecha.format(compra.getFechaCompra()));
}

    
    public void editarCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
<<<<<<< HEAD
            String sql = "CALL sp_EditarCompras(?,?)";
=======
            String sql = "CALL sp_EditarCompra(?,?)";
>>>>>>> e2186e805825bd595c799466183545218cd6968b
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCompraId.getText()));
            statement.setString(2,tfFecha.getText());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
<<<<<<< HEAD
=======
                
>>>>>>> e2186e805825bd595c799466183545218cd6968b
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
    
    public void setOp(int op) {
        this.op = op;
    }
}
