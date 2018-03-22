package org.usfirst.frc.team4131.robot.command;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.subsystem.ElevatorSubsystem;

/**
 * A command used to raise or lower the crate held in the
 * claw using the elevator subsystem.
 */
//TODO test when elevator is built
public class ElevatorCommand extends SingleSubsystemCmd<ElevatorSubsystem> {
	private boolean isTop = true, isBottom = true;
    public ElevatorCommand(ElevatorSubsystem subsystem) {
        super(subsystem);
    }

    /**
     * Checks the claw elevator button in order to determine
     * if the claw should be raised.
     *
     * @return {@code true} to signal that the claw should
     * be raised
     */
    private static boolean shouldRaise() {
        return Oi.ELEVATORUP.get();
    }

    /**
     * Checks the claw elevator button in order to determine
     * if the claw should be lowered.
     *
     * @return {@code true} to signal that the claw should
     * be lowered
     */
    private static boolean shouldLower() {
        return Oi.ELEVATORDOWN.get();
    }

    @Override
    protected void execute() {
        if (shouldRaise() && shouldLower()) {
            this.subsystem.stop();
        } else if (shouldRaise()) {
            if (!Robot.isElevatorTop || isTop) {
            	isTop = false;
                this.subsystem.stop();
            } else {
                this.subsystem.raise();
                isBottom = true;
            }
        } else if (shouldLower()) {
            if (!Robot.isElevatorBottom || isBottom) {
                this.subsystem.stop();
                isBottom = false;
            } else {
                this.subsystem.lower();
                isTop = true;
            }
        } else {
            this.subsystem.stop();
        }
    }

    @Override
    protected void interrupted() {
        this.subsystem.stop();
    }
}