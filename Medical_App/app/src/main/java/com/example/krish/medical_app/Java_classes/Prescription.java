package com.example.krish.medical_app.Java_classes;

/**
 * Created by parini on 16-06-2017.
 */

public class Prescription
{
    private String prescription_id;
    private String medication;
    private String dispense;
    private String unit;
    private String refills;
    private String sig;
    
    Prescription(String prescription_id,String medication, String dispense, String unit, String refills, String sig)
    {
        this.prescription_id = prescription_id;
        this.medication=medication;
        this.dispense=dispense;
        this.unit = unit;
        this.refills = refills;
        this.sig=sig;
    }

    public String getPrescription_id() {
        return prescription_id;
    }

    public void setPrescription_id(String prescription_id) {
        this.prescription_id = prescription_id;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDispense() {
        return dispense;
    }

    public void setDispense(String dispense) {
        this.dispense = dispense;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRefills() {
        return refills;
    }

    public void setRefills(String refills) {
        this.refills = refills;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }
}
