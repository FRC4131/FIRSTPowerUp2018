package org.usfirst.frc.team4131.robot.auto.procedure;

import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.auto.Procedure;
import org.usfirst.frc.team4131.robot.auto.Side;
import org.usfirst.frc.team4131.robot.auto.action.DistanceMoveAction;
import org.usfirst.frc.team4131.robot.auto.action.TurnAction;
import org.usfirst.frc.team4131.robot.subsystem.SubsystemProvider;

import java.util.List;

/**
 * A sample procedure that moves the robot forward 12 inches
 * and then turns 90 degrees to the right.
 */
public class Move12ThenTurn90 implements Procedure {
    @Override
    public int estimateLen() {
        return 6;
    }

    @Override
    public void populate(SubsystemProvider provider, List<Side> data, List<Action> procedure) {
        procedure.add(new DistanceMoveAction(provider.getDriveBase(), 36));
        procedure.add(new TurnAction(provider.getDriveBase(), 90));
        procedure.add(new DistanceMoveAction(provider.getDriveBase(), 12));
        procedure.add(new TurnAction(provider.getDriveBase(), -90));
        procedure.add(new DistanceMoveAction(provider.getDriveBase(), 24));
        procedure.add(new TurnAction(provider.getDriveBase(), 90));
    }
}