package org.usfirst.frc.team4131.robot.auto.procedure;

import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.auto.Procedure;
import org.usfirst.frc.team4131.robot.auto.Side;
import org.usfirst.frc.team4131.robot.auto.action.DistanceMoveAction;
import org.usfirst.frc.team4131.robot.auto.action.ElevatorAndClimberAction;
import org.usfirst.frc.team4131.robot.auto.action.PneumaticActionOne;
import org.usfirst.frc.team4131.robot.auto.action.PneumaticActionTwo;
import org.usfirst.frc.team4131.robot.auto.action.TurnAction;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import java.util.List;

/**
 * Autonomous procedure to run when the robot is placed to
 * the right.
 */
public class RightToSwitchOrScale implements Procedure {
	
	@Override
	public int estimateLen() {
		return 4;
	}

	@Override
	public void populate(SubsystemProvider provider, List<Side> data, List<Action> procedure) {
		if (data.get(0) == Side.RIGHT) {
            procedure.add(new PneumaticActionOne(provider.getClaw()));
			//if the switch is right
			//drive straight 168 inches (until level with center of the switch
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 168));
			//turn left 90
			procedure.add(new TurnAction(provider.getDriveBase(), -90));
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 19.5));
			procedure.add(new ElevatorAndClimberAction(provider.getClimber(), provider.getElevator(), false, true));
			procedure.add(new PneumaticActionTwo(provider.getClaw()));
		} else if (data.get(0) == Side.LEFT && data.get(1) == Side.RIGHT) {
            procedure.add(new PneumaticActionOne(provider.getClaw()));
			//if the switch is left but the scale is right
			//drive until level with the scale
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 168));
			//turn left 90
			procedure.add(new TurnAction(provider.getDriveBase(), -90));
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 5.9));
			procedure.add(new ElevatorAndClimberAction(provider.getClimber(), provider.getElevator(), true, true));
			procedure.add(new PneumaticActionTwo(provider.getClaw()));
		} else if (data.get(0) == Side.LEFT && data.get(1) == Side.LEFT) {
            procedure.add(new PneumaticActionOne(provider.getClaw()));
			//if both are left (either drive straight or go to center)
			//Currently runs same as LRBaseline
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 144));
		} else {
			DriverStation.reportError("Bad FMS data", true);
		}
	}
}
