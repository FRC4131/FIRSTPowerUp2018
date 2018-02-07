package org.usfirst.frc.team4131.robot.auto.procedure;

import java.util.List;

import org.usfirst.frc.team4131.robot.Robot;
import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.auto.Procedure;
import org.usfirst.frc.team4131.robot.auto.Side;
import org.usfirst.frc.team4131.robot.auto.action.DistanceMoveAction;
import org.usfirst.frc.team4131.robot.auto.action.TurnAction;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

public class ds2ToSwitch implements Procedure{	
	
	String fmsAt0 = "" + Robot.FMS_DATA.charAt(0);
	@Override
	public void populate(SubsystemProvider provider, List<Side> data, List<Action> procedure) {
		
		// check fms data
		if (fmsAt0.equals("R")) {
			//forward 18", right 90, forward 27", left 90, forward 122"
			
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 18));
			procedure.add(new TurnAction(provider.getDriveBase(), 90));
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 27));
			procedure.add(new TurnAction(provider.getDriveBase(), -90));
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 122));
		}
		else if (fmsAt0.equals("L")) {
			//forward 18", left 90, forward 151", right 90, forward 122"
			
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 18));
			procedure.add(new TurnAction(provider.getDriveBase(), -90));
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 27));
			procedure.add(new TurnAction(provider.getDriveBase(), 90));
			procedure.add(new DistanceMoveAction(provider.getDriveBase(), 122));
		}
		else {
			System.err.println("Bad FMS data!");
		}
	}

	
}