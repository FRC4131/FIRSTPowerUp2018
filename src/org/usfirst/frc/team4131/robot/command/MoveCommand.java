package org.usfirst.frc.team4131.robot.command;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.subsystem.DriveBaseSubsystem;

/**
 * A command that allows joystick control of the drive base
 */
public class MoveCommand extends SingleSubsystemCmd<DriveBaseSubsystem> {
    /**
     * Creates a new movement command for the given drive
     * base subsystem.
     *
     * @param subsystem the subsystem for which the drive
     * commands will be passed
     */
    public MoveCommand(DriveBaseSubsystem subsystem) {
        super(subsystem);
    }

    /**
     * Obtains the left joystick throttle position
     *
     * @return a number between -1 and 1 depending on if
     * the controls have been inverted and on the position
     * of the joystick
     */
    private static double getLeft() {
        if (Robot.isInverted) {
            return -Oi.R_JOYSTICK.getRawAxis(1);
        } else {
            return Oi.L_JOYSTICK.getRawAxis(1);
        }
    }

    /**
     * Obtains the right joystick throttle position
     *
     * @return a number between -1 and 1 depending on if
     * the controls have been inverted and on the position
     * of the joystick
     */
    private static double getRight() {
        if (Robot.isInverted) {
            return -Oi.L_JOYSTICK.getRawAxis(1);
        } else {
            return Oi.R_JOYSTICK.getRawAxis(1);
        }
    }

    // CLEAN UP --------------------------------------------

    @Override
    protected void execute() {
        if (Robot.isThrottleMode) {
        	this.subsystem.doThrottle(getLeft(), getLeft());
        } else {
        	this.subsystem.doThrottle(getLeft(), getRight());
        }
    }

    // JOYSTICK --------------------------------------------

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void interrupted() {
        this.subsystem.doThrottle(0, 0);
    }
}