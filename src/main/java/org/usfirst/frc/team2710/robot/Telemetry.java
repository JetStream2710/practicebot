/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2710.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Telemetry {
    private static List<String> events = new ArrayList<>();
    
    public static void addEvent(String event)
    {
        events.add(event);
        while(events.size()>5)
        {
            events.remove(0);
        }
        SmartDashboard.putStringArray("Telemetry Event Log:", events.toArray(new String[events.size()])); 
        System.out.println("ALL EVENTS");
        for (String ev : events) {
            System.out.println("Event: " + ev);
        }
    }
}
