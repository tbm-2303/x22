package dtos;

import entities.House;

public class HouseDTO {
    private int id;
    private String address;
    private String city;
    private int rooms;


    public HouseDTO(House house) {
        this.id = house.getId();
        this.address = house.getAddress();
        this.city = house.getCity();
        this.rooms = house.getNumberOfRooms();

    }

    public HouseDTO(String address, String city, int rooms) {
        this.address = address;
        this.city = city;
        this.rooms = rooms;
    }


    public int getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public int getRooms() {
        return rooms;
    }


}