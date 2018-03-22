package org.usfirst.frc.team4131.robot.auto.procedure;

import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.auto.Procedure;
import org.usfirst.frc.team4131.robot.auto.Side;
import org.usfirst.frc.team4131.robot.auto.action.DistanceMoveAction;
import org.usfirst.frc.team4131.robot.auto.action.PneumaticActionOne;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import edu.wpi.first.wpilibj.Timer;

import java.util.List;

/**
 * Autonomous procedure to run when the robot is placed
 * in front of the driver station.
 */
public class DriverSTation2ToBaseLine implements Procedure {

    @Override
    public void populate(SubsystemProvider provider, List<Side> data, List<Action> procedure) {
    		procedure.add(new PneumaticActionOne(provider.getClaw()));
    		Timer.delay(1);
            procedure.add(new DistanceMoveAction(provider.getDriveBase(), (144 - 40)));   
    }
}