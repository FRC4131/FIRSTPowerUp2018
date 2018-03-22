package org.usfirst.frc.team4131.robot.command;

import org.usfirst.frc.team4131.robot.Oi;
import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.subsystem.ClimberSubsystem;



/**
 * A command which will activate the climber and raise the
 * robot using the pull-up bar.
 */
public class ClimbCommand extends SingleSubsystemCmd<ClimberSubsystem> {
	private boolean isTop = true, isBottom = true;
	public ClimbCommand(ClimberSubsystem subsystem) {
		
		
        super(subsystem);
    }

    /**
     * Checks the climb button in order to determine whether
     * the robot should climb.
     *
     * @return {@code true} to signal that climbing should
     * commence
     */
    private static boolean shouldLower() {
        return Oi.CLIMBERDOWN.get();
    }

    /**
     * Checks the lower button in order to determine whether
     * the robot should lower itself.
     *
     * @return {@code true} to signal that the robot should
     * lower itself
     */
    private static boolean shouldRaise() {
        return Oi.CLIMBERUP.get();
    }

    @Override
    protected void execute() {
        if (shouldLower() && shouldRaise()) {
            this.subsystem.stop();
        } else if (shouldLower()) {
            if (!Robot.isClimberTop) {
            	isTop = false;
            	this.subsystem.stop();
            } else {
                this.subsystem.climb();
                isBottom = true;
            }
        } else if (shouldRaise()) {
        	if (!Robot.isClimberBottom) {
        		isBottom = false;
                this.subsystem.stop();
            } else {
                this.subsystem.descend();
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