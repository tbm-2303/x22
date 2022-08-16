package dtos;

import entities.Rental;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RentalDTO {
    private int id;
    private String startDate;
    private String endDate;
    private double priceAnual;
    private double deposit;
    private String contactPerson;



    public RentalDTO(Rental rental) {
        this.id = rental.getId();
        this.startDate = rental.getStartDate();
        this.endDate = rental.getEndDate();
        this.priceAnual = rental.getPriceAnnual();
        this.deposit = rental.getDeposit();
        this.contactPerson = rental.getContactPerson();
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
    public double getPriceAnual() { return priceAnual; }
    public void setPriceAnual(double priceAnual) { this.priceAnual = priceAnual; }
    public double getDeposit() { return deposit; }
    public void setDeposit(double deposit) { this.deposit = deposit; }
    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }



}