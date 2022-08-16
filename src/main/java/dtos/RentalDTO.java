package dtos;

import entities.Rental;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RentalDTO {
    private int id;
    private String startDate;
    private String endDate;
    private double priceAnnual;
    private double deposit;
    private String contactPerson;
    private int houseId;


    public RentalDTO(Rental rental) {
        this.id = rental.getId();
        this.startDate = rental.getStartDate();
        this.endDate = rental.getEndDate();
        this.priceAnnual = rental.getPriceAnnual();
        this.deposit = rental.getDeposit();
        this.contactPerson = rental.getContactPerson();
    }
//need it to create
    public RentalDTO(String startDate, String endDate, double priceAnnual, double deposit, String contactPerson, int houseId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceAnnual = priceAnnual;
        this.deposit = deposit;
        this.contactPerson = contactPerson;
        if(houseId != 0 ){
            this.houseId = houseId;
        }

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) {this.endDate = endDate; }
    public double getPriceAnnual() { return priceAnnual; }
    public int getHouseId() { return houseId; }
    public void setHouseId(int houseId) { this.houseId = houseId; }
    public void setPriceAnnual(double priceAnnual) { this.priceAnnual = priceAnnual; }
    public double getDeposit() { return deposit; }
    public void setDeposit(double deposit) { this.deposit = deposit; }
    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }


}