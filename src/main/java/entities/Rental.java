package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rental")
@NamedQuery(name = "Rental.deleteAllRows", query = "DELETE from Rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "price_annual")
    private double priceAnnual;

    @Column(name = "deposit")
    private double deposit;

    @Column(name = "contact_person")
    private String contactPerson;

    @ManyToMany
    @JoinTable(name = "tenant_rentals")
    private Set<Tenant> tenants = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "house_id")
    private House house;

    public Rental() {
    }

    public Rental(String startDate, String endDate, double priceAnnual, double deposit, String contactPerson) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceAnnual = priceAnnual;
        this.deposit = deposit;
        this.contactPerson = contactPerson;
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
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public double getPriceAnnual() {
        return priceAnnual;
    }
    public void setPriceAnnual(double priceAnnual) {
        this.priceAnnual = priceAnnual;
    }
    public double getDeposit() {
        return deposit;
    }
    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }
    public String getContactPerson() {
        return contactPerson;
    }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
    public Set<Tenant> getTenants() { return tenants; }
    public void setTenants(Set<Tenant> tenants) {
        this.tenants = tenants;
    }
    public House getHouse() { return house; }
    public void setHouse(House house) { this.house = house; }

    public void addTenant(Tenant tenant){
        this.tenants.add(tenant);
        if(!tenant.getRentals().contains(this)){
            tenant.getRentals().add(this);
        }
    }
    public void removeTenant(Tenant tenant){
        this.tenants.remove(tenant);
        if(tenant.getRentals().contains(this)){
            tenant.removeRental(this);
        }
    }
}
