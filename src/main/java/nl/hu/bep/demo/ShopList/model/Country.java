package nl.hu.bep.demo.ShopList.model;

import java.io.Serializable;
import java.util.Objects;

public class Country implements Serializable {
    private String code;
    private String iso3;
    private String name;
    private String capital;
    private String continent;
    private String region;
    private double surface;
    private int population;
    private String government;
    private double latitude;
    private double longitude;

    public Country(String code, String iso3, String nm, String cap, String ct, String reg, double sur, int pop, String gov, double lat, double lng) {
        this.code = code;
        this.iso3 = iso3;
        this.name = nm;
        this.capital = cap;
        this.continent = ct;
        this.region = reg;
        this.surface = sur;
        this.population = pop;
        this.government = gov;
        this.latitude = lat;
        this.longitude = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return code.equals(country.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public String getCode() {
        return code;
    }

    public String getIso3() {
        return iso3;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getContinent() {
        return continent;
    }

    public String getRegion() {
        return region;
    }

    public double getSurface() {
        return surface;
    }

    public int getPopulation() {
        return population;
    }

    public String getGovernment() {
        return government;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

