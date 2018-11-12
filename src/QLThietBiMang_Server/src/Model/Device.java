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
public class Device implements Serializable{

    private int id;
    private String name;
    private float price;
    private int count;
    private String startYear;
    private String deadYear;
    private String manufacturer; // hãng sản xuất

    public Device() {
    }

    public Device(int id, String name, float price, int count, String startYear, String deadYear, String manufacturer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
        this.startYear = startYear;
        this.deadYear = deadYear;
        this.manufacturer = manufacturer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getDeadYear() {
        return deadYear;
    }

    public void setDeadYear(String deadYear) {
        this.deadYear = deadYear;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    

}
