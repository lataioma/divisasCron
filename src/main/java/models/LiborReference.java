package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* Clase para representar el latest del libor en las cinco divisas principales (usd=libor, eur=euroLibor, gpd=poundLibor, jpy=yenLibor,ch=francoLibor)
* Created by T13932 on 14/9/16.
*/

public class LiborReference {

	private String dateLibor;
    private float usdLiborSpot;
    private float usdLibor1Week;
    private float usdLibor1Month;
    private float usdLibor2Month;
    private float usdLibor3Month;
    private float usdLibor6Month;
    private float usdLibor1Year;

    private float euroLiborSpot;
    private float euroLibor1Week;
    private float euroLibor1Month;
    private float euroLibor2Month;
    private float euroLibor3Month;
    private float euroLibor6Month;
    private float euroLibor1Year;

    private float poundLiborSpot;
    private float poundLibor1Week;
    private float poundLibor1Month;
    private float poundLibor2Month;
    private float poundLibor3Month;
    private float poundLibor6Month;
    private float poundLibor1Year;

    private float yenLiborSpot;
    private float yenLibor1Week;
    private float yenLibor1Month;
    private float yenLibor2Month;
    private float yenLibor3Month;
    private float yenLibor6Month;
    private float yenLibor1Year;

    private float francLiborSpot;
    private float francLibor1Week;
    private float francLibor1Month;
    private float francLibor2Month;
    private float francLibor3Month;
    private float francLibor6Month;
    private float francLibor1year;
    
    

    public float getPoundLibor1Year() {
		return poundLibor1Year;
	}

	public void setPoundLibor1Year(float poundLibor1Year) {
		this.poundLibor1Year = poundLibor1Year;
	}

	public float getYenLibor1Year() {
		return yenLibor1Year;
	}

	public void setYenLibor1Year(float yenLibor1Year) {
		this.yenLibor1Year = yenLibor1Year;
	}

	public float getFrancLibor1year() {
		return francLibor1year;
	}

	public void setFrancLibor1year(float francLibor1year) {
		this.francLibor1year = francLibor1year;
	}

	public LiborReference() {
    }

    public String getDateLibor() {
        return dateLibor;
    }

    public void setDateLibor(String dateLibor) {
    	this.dateLibor = dateLibor;
    }

    public float getUsdLiborSpot() {
        return usdLiborSpot;
    }

    public void setUsdLiborSpot(float usdLiborSpot) {
        this.usdLiborSpot = usdLiborSpot;
    }

    public float getUsdLibor1Week() {
        return usdLibor1Week;
    }

    public void setUsdLibor1Week(float usdLibor1Week) {
        this.usdLibor1Week = usdLibor1Week;
    }

    public float getUsdLibor1Month() {
        return usdLibor1Month;
    }

    public void setUsdLibor1Month(float usdLibor1Month) {
        this.usdLibor1Month = usdLibor1Month;
    }

    public float getUsdLibor2Month() {
        return usdLibor2Month;
    }

    public void setUsdLibor2Month(float usdLibor2Month) {
        this.usdLibor2Month = usdLibor2Month;
    }

    public float getUsdLibor3Month() {
        return usdLibor3Month;
    }

    public void setUsdLibor3Month(float usdLibor3Month) {
        this.usdLibor3Month = usdLibor3Month;
    }

    public float getUsdLibor6Month() {
        return usdLibor6Month;
    }

    public void setUsdLibor6Month(float usdLibor6Month) {
        this.usdLibor6Month = usdLibor6Month;
    }

    public float getUsdLibor1Year() {
        return usdLibor1Year;
    }

    public void setUsdLibor1Year(float usdLibor1Year) {
        this.usdLibor1Year = usdLibor1Year;
    }

    public float getEuroLiborSpot() {
        return euroLiborSpot;
    }

    public void setEuroLiborSpot(float euroLiborSpot) {
        this.euroLiborSpot = euroLiborSpot;
    }

    public float getEuroLibor1Week() {
        return euroLibor1Week;
    }

    public void setEuroLibor1Week(float euroLibor1Week) {
        this.euroLibor1Week = euroLibor1Week;
    }

    public float getEuroLibor1Month() {
        return euroLibor1Month;
    }

    public void setEuroLibor1Month(float euroLibor1Month) {
        this.euroLibor1Month = euroLibor1Month;
    }

    public float getEuroLibor2Month() {
        return euroLibor2Month;
    }

    public void setEuroLibor2Month(float euroLibor2Month) {
        this.euroLibor2Month = euroLibor2Month;
    }

    public float getEuroLibor3Month() {
        return euroLibor3Month;
    }

    public void setEuroLibor3Month(float euroLibor3Month) {
        this.euroLibor3Month = euroLibor3Month;
    }

    public float getEuroLibor6Month() {
        return euroLibor6Month;
    }

    public void setEuroLibor6Month(float euroLibor6Month) {
        this.euroLibor6Month = euroLibor6Month;
    }

    public float getEuroLibor1Year() {
        return euroLibor1Year;
    }

    public void setEuroLibor1Year(float euroLibor1Year) {
        this.euroLibor1Year = euroLibor1Year;
    }

    public float getPoundLiborSpot() {
        return poundLiborSpot;
    }

    public void setPoundLiborSpot(float poundLiborSpot) {
        this.poundLiborSpot = poundLiborSpot;
    }

    public float getPoundLibor1Week() {
        return poundLibor1Week;
    }

    public void setPoundLibor1Week(float poundLibor1Week) {
        this.poundLibor1Week = poundLibor1Week;
    }

    public float getPoundLibor1Month() {
        return poundLibor1Month;
    }

    public void setPoundLibor1Month(float poundLibor1Month) {
        this.poundLibor1Month = poundLibor1Month;
    }

    public float getPoundLibor2Month() {
        return poundLibor2Month;
    }

    public void setPoundLibor2Month(float poundLibor2Month) {
        this.poundLibor2Month = poundLibor2Month;
    }

    public float getPoundLibor3Month() {
        return poundLibor3Month;
    }

    public void setPoundLibor3Month(float poundLibor3Month) {
        this.poundLibor3Month = poundLibor3Month;
    }

    public float getPoundLibor6Month() {
        return poundLibor6Month;
    }

    public void setPoundLibor6Month(float poundLibor6Month) {
        this.poundLibor6Month = poundLibor6Month;
    }

    public float getYenLiborSpot() {
        return yenLiborSpot;
    }

    public void setYenLiborSpot(float yenLiborSpot) {
        this.yenLiborSpot = yenLiborSpot;
    }

    public float getYenLibor1Week() {
        return yenLibor1Week;
    }

    public void setYenLibor1Week(float yenLibor1Week) {
        this.yenLibor1Week = yenLibor1Week;
    }

    public float getYenLibor1Month() {
        return yenLibor1Month;
    }

    public void setYenLibor1Month(float yenLibor1Month) {
        this.yenLibor1Month = yenLibor1Month;
    }

    public float getYenLibor2Month() {
        return yenLibor2Month;
    }

    public void setYenLibor2Month(float yenLibor2Month) {
        this.yenLibor2Month = yenLibor2Month;
    }

    public float getYenLibor3Month() {
        return yenLibor3Month;
    }

    public void setYenLibor3Month(float yenLibor3Month) {
        this.yenLibor3Month = yenLibor3Month;
    }

    public float getYenLibor6Month() {
        return yenLibor6Month;
    }

    public void setYenLibor6Month(float yenLibor6Month) {
        this.yenLibor6Month = yenLibor6Month;
    }

    public float getFrancLiborSpot() {
        return francLiborSpot;
    }

    public void setFrancLiborSpot(float francLiborSpot) {
        this.francLiborSpot = francLiborSpot;
    }

    public float getFrancLibor1Week() {
        return francLibor1Week;
    }

    public void setFrancLibor1Week(float francLibor1Week) {
        this.francLibor1Week = francLibor1Week;
    }

    public float getFrancLibor1Month() {
        return francLibor1Month;
    }

    public void setFrancLibor1Month(float francLibor1Month) {
        this.francLibor1Month = francLibor1Month;
    }

    public float getFrancLibor2Month() {
        return francLibor2Month;
    }

    public void setFrancLibor2Month(float francLibor2Month) {
        this.francLibor2Month = francLibor2Month;
    }

    public float getFrancLibor3Month() {
        return francLibor3Month;
    }

    public void setFrancLibor3Month(float francLibor3Month) {
        this.francLibor3Month = francLibor3Month;
    }

    public float getFrancLibor6Month() {
        return francLibor6Month;
    }

    public void setFrancLibor6Month(float francLibor6Month) {
        this.francLibor6Month = francLibor6Month;
    }
}
