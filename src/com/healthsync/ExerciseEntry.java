package com.healthsync;
public class ExerciseEntry {
    private String name;
    private int durationMins;
    private String notes;
    public ExerciseEntry(String name, int durationMins, String notes){
        this.name=name; this.durationMins=durationMins; this.notes=notes;
    }
    public String toString(){ return name + " ("+durationMins+" min)" + (notes==null||notes.isBlank()?"":" - "+notes); }
    public int getDuration(){ return durationMins; }
}
