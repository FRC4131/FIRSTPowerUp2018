/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4131.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Mappings of physical control devices (i.e. joysticks,
 * buttons, etc.) to representations useful for commands to
 * poll their state.
 */
public final class Oi {
    public static final Joystick L_JOYSTICK = new Joystick(RobotMap.L_JOY_PORT);
    public static final Joystick R_JOYSTICK = new Joystick(RobotMap.R_JOY_PORT);

    private Oi() { // Prevent instantiation
    }

    public static void init() {
    }
}