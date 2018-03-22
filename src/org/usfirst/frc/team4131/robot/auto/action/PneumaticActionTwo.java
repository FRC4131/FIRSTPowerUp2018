package org.usfirst.frc.team4131.robot.auto.action;

import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.subsystem.ClawSubsystem;

/*
 * Cube despense actions, end of auton
 * Release cube
 * Eject Cube
 */
public class PneumaticActionTwo implements Action{

	ClawSubsystem claw;

	public PneumaticActionTwo(ClawSubsystem claw) {
		this.claw = claw;
	}
	
	@Override
	public void doAction() {
		claw.release();
		claw.pusherOut();
	}
}
