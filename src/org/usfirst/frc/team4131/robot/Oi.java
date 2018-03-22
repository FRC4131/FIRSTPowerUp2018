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
    
	// JoySticks
    public static final Joystick L_JOYSTICK = new Joystick(RobotMap.L_JOY_PORT);
    public static final Joystick R_JOYSTICK = new Joystick(RobotMap.R_JOY_PORT);
    public static final Joystick AUX_JOYSTICK = new Joystick(RobotMap.AUX_JOY_PORT);
    
    // Stick one buttons
    public static final JoystickButton INVERT_L_1 = new JoystickButton(L_JOYSTICK, 6);
    public static final JoystickButton INVERT_L_2 = new JoystickButton(L_JOYSTICK, 4);    

    public static final JoystickButton THROTTLE_MODE = new JoystickButton(L_JOYSTICK, 1);

    // Stick two buttons
    public static final JoystickButton INVERT_R_1 = new JoystickButton(R_JOYSTICK, 5);
    public static final JoystickButton INVERT_R_2 = new JoystickButton(R_JOYSTICK, 3);
        
    // Aux stick buttons
    public static final JoystickButton CLAW = new JoystickButton(AUX_JOYSTICK, 1);
    public static final JoystickButton ARM = new JoystickButton(AUX_JOYSTICK, 2);
    public static final JoystickButton PUSHER = new JoystickButton(AUX_JOYSTICK, 11);

    public static final JoystickButton ELEVATORUP = new JoystickButton(AUX_JOYSTICK, 5);
    public static final JoystickButton ELEVATORDOWN = new JoystickButton(AUX_JOYSTICK, 3);
    
    public static final JoystickButton CLIMBERUP = new JoystickButton(AUX_JOYSTICK, 4);
    public static final JoystickButton CLIMBERDOWN = new JoystickButton(AUX_JOYSTICK, 6);
    

    private Oi() { // Prevent instantiation
    }

    /**
     * Obtains the direction sign for the left side motors.
     *
     * @return the sign on input/output on the left side
     * motors
     */
    public static int sigl() {
        return -1;
    }

    /**
     * Obtains the direction sign for the right side motors.
     *
     * @return the sign on the input/output on the right
     * side motors
     */
    public static int sigr() {
        return 1;
    }
}