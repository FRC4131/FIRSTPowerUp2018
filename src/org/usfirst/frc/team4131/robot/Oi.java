/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4131.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Mappings of physical control devices (i.e. joysticks,
 * buttons, etc.) to representations useful for commands to
 * poll their state.
 */

public final class Oi {
    
	// Joysticks
	public static final Joystick L_JOYSTICK = new Joystick(RobotMap.L_JOY_PORT);
    public static final Joystick R_JOYSTICK = new Joystick(RobotMap.R_JOY_PORT);
    public static final Joystick AUX_JOYSTICK = new Joystick(RobotMap.AUX_JOY_PORT);
    
    // Stick one buttons
    public static final JoystickButton INVERT_L_1 = new JoystickButton(L_JOYSTICK, 6);
    public static final JoystickButton INVERT_L_2 = new JoystickButton(L_JOYSTICK, 4);

    public static final JoystickButton INVERT_R_1 = new JoystickButton(R_JOYSTICK, 5);
    public static final JoystickButton INVERT_R_2 = new JoystickButton(R_JOYSTICK, 3);
    
    // Aux stick buttons
    public static final JoystickButton CLIMB = new JoystickButton(AUX_JOYSTICK, 1);
    public static final JoystickButton LOWER = new JoystickButton(AUX_JOYSTICK, 2);
    
    private Oi() { // Prevent instantiation
    }
}