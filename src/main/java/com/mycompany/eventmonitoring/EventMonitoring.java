/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.eventmonitoring;


import java.util.*;
import java.io.*;
import org.json.*;

/**
 *
 * @author anthonycolle
 */
public class EventMonitoring {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
     
        // Parse the content of logfile.txt and store it as a string
        InputStream in = EventMonitoring.class.getResourceAsStream("logfile.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String json = "";
        try {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            sb.append("{ \"events\": [\n");

            while (line != null) {
                sb.append(line + ",");
                sb.append("\n");
                line = reader.readLine();
            }
            sb.append("]\n}");
            json = sb.toString();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            reader.close();
        }
        
        // Parse the Json data from the string
        JSONObject object = new JSONObject(json);
        JSONArray eventArray = object.getJSONArray("events");
        
        // Create a map collection to store values associated with each ids
        HashMap<String, Event> eventMap = new HashMap<String, Event>();
        
        for (int i = 0; i < eventArray.length(); i++) {
            
            // Get relevant fields for each object
            String id = eventArray.getJSONObject(i).getString("id");
            String state = eventArray.getJSONObject(i).getString("state");
            int timestamp = eventArray.getJSONObject(i).getInt("timestamp");
                                    
            if (!eventMap.containsKey(id)) {
               // If id not in the collection, create a new Event object
               Event newEvent = new Event(id);
               
               // Populate the object depending on the fields available and their value
               if (state.equals("STARTED")) {
                   newEvent.setTimestampStart(timestamp);
               } else if (state.equals("FINISHED")) {
                   newEvent.setTimestampEnd(timestamp);
               }
               
               // Compute duration if the 2 required fields have been populated
               if (newEvent.getTimestampStart() != 0 && newEvent.getTimestampEnd() != 0) {
                   newEvent.setDuration();
                   newEvent.setAlert();
               }
               
               // Check if fields type or host exist
               if (eventArray.getJSONObject(i).has("type")) {
                   newEvent.setType(eventArray.getJSONObject(i).getString("type"));
               }
               if (eventArray.getJSONObject(i).has("host")) {
                   newEvent.setHost(eventArray.getJSONObject(i).getString("host"));
               }
               
               // Add the new event in the collection
               eventMap.put(id, newEvent);
            } else {
               // Get the existing Event obj
               Event existingEvent = eventMap.get(id);
               
               // Populate the object depending on the fields available and their value
               if (state.equals("STARTED")) {
                   existingEvent.setTimestampStart(timestamp);
               } else if (state.equals("FINISHED")) {
                   existingEvent.setTimestampEnd(timestamp);
               }
               
               // Compute duration if the 2 required fields have been populated
               if (existingEvent.getTimestampStart() != 0 && existingEvent.getTimestampEnd() != 0) {
                   existingEvent.setDuration();
                   existingEvent.setAlert();
               }
               
               // Check if fields type or host exist
               if (eventArray.getJSONObject(i).has("type")) {
                   existingEvent.setType(eventArray.getJSONObject(i).getString("type"));
               }
               if (eventArray.getJSONObject(i).has("host")) {
                   existingEvent.setHost(eventArray.getJSONObject(i).getString("host"));
               }    
            }
        }
        
        // Iterate through the collection and output the results in a text file
        Iterator it = eventMap.entrySet().iterator();
        String wd = EventMonitoring.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(wd);
        FileWriter writer = new FileWriter(wd + "/com/mycompany/eventmonitoring/eventDurationResults.txt");
        BufferedWriter buffer = new BufferedWriter(writer);  
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue().toString());
            buffer.write(pair.getValue().toString());
            buffer.newLine();
            it.remove();
        }
        buffer.close();
    }
}
