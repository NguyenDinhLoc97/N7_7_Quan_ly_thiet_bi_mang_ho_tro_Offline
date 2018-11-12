/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author Bv ptop 268
 */
public class UpdateDevice implements Serializable{
    private int idPast;
    private Device device;

    public UpdateDevice() {
    }

    public UpdateDevice(int idPast, Device device) {
        this.idPast = idPast;
        this.device = device;
    }

    public int getIdPast() {
        return idPast;
    }

    public void setIdPast(int idPast) {
        this.idPast = idPast;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
    
}
