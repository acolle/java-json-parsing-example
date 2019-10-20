/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.eventmonitoring;

/**
 *
 * @author anthonycolle
 */
public class Event {
    
    private String id;
    private String type = "n/a";
    private String host = "n/a";
    private int timestamp_start;
    private int timestamp_end;
    private int duration;
    private boolean alert;
    
    // Set the id when a new object is instantiated 
    Event(String id) {
      this.id = id;
    }
    
    // Overwrite Object method to get the content of the obj as a String
    @Override
    public String toString() {
        return "{ \"id\":\"" + getId() + "\", \"timestamp_start\":" + getTimestampStart() + ", \"timestamp_end\":" + getTimestampEnd() + ", \"duration\": " + getDuration() + ", \"alert:\" " + getAlert() + ", \"type\":\"" + getType() + "\", \"host\":\"" + getHost() + "\"}";
    }
    
    // Setters
    void setType(String type) {
        this.type = type;
    }
    
    void setHost(String host) {
        this.host = host;
    }
    
    void setTimestampStart(int start) {
        this.timestamp_start = start;
    }
    
    void setTimestampEnd(int end) {
        this.timestamp_end = end;
    }
    
    void setDuration() {
        this.duration = getTimestampEnd() - getTimestampStart();
    }
    
    void setAlert() {
        if (getDuration() > 4) {
            this.alert = true;
        } else {
            this.alert = false;
        }
    }
    
    // Getters
    String getId() {
      return this.id;
    }
    
    String getType() {
      return this.type;
    }
    
    String getHost() {
      return this.host;
    }
    
    int getTimestampStart() {
      return this.timestamp_start;
    }
    
    int getTimestampEnd() {
      return this.timestamp_end;
    }
    
    int getDuration() {
      return this.duration;
    }
    
    boolean getAlert() {
        return this.alert;
    }
    
}
