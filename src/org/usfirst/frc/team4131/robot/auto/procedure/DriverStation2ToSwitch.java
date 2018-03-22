package org.usfirst.frc.team4131.robot.auto.procedure;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

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
 * Autonomous procedure to run when the robot is placed
 * in front of the driver station.
 */
public class DriverStation2ToSwitch implements Procedure {
	@Override
	public int estimateLen() {
		return 5;
	}

	@Override
	public void populate(SubsystemProvider provider, List<Side> data, List<Action> procedure) {
		// check fms data
		//Johnny's implementation: data.get(0) == Side.RIGHT
		if (data.get(0) == Side.RIGHT) {
			//forward 18", right 90, forward 27", left 90, forward 122"
			procedure.add(new PneumaticActionOne(provider.getClaw()));
			Timer.delay(1);
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 30));
			procedure.add(new TurnAction(provider.getDriveBase(), 90));
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 27));
			procedure.add(new TurnAction(provider.getDriveBase(), -90));
			procedure.add(new ElevatorAndClimberAction(provider.getClimber(), provider.getElevator(), false, true));
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 92));
			Timer.delay(1);
			procedure.add(new PneumaticActionTwo(provider.getClaw()));
		} else if (data.get(0) == Side.LEFT){
			//forward 18", left 90, forward 151", right 90, forward 122"
			procedure.add(new PneumaticActionOne(provider.getClaw()));
			Timer.delay(1);
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 30));
			procedure.add(new TurnAction(provider.getDriveBase(), -90));
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 27));
			procedure.add(new TurnAction(provider.getDriveBase(), 90));
			procedure.add(new ElevatorAndClimberAction(provider.getClimber(), provider.getElevator(), false, true));
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 92));
			Timer.delay(1);
			procedure.add(new PneumaticActionTwo(provider.getClaw()));
		} else {
			DriverStation.reportError("Bad FMS data", true);
			System.err.println("Bad FMS data");
		}
	}
}
