package com.healthsync;
public class MedicineEntry {
    private String name;
    private String dosage;
    private String time;
    private String notes;
    public MedicineEntry(String name, String dosage, String time, String notes){
        this.name=name; this.dosage=dosage; this.time=time; this.notes=notes;
    }
    public String toString(){ return name + " ("+dosage+") at "+time + (notes==null||notes.isBlank()?"":" - "+notes); }
}
