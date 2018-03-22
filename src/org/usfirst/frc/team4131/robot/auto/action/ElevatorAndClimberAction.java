package org.usfirst.frc.team4131.robot.auto.action;

import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.subsystem.ClimberSubsystem;
import org.usfirst.frc.team4131.robot.subsystem.ElevatorSubsystem;

import edu.wpi.first.wpilibj.Timer;

public class ElevatorAndClimberAction implements Action{

	ClimberSubsystem climber;
	ElevatorSubsystem elevator;
	
	private boolean elevator2 = false;
	private boolean climber2 = false;
		
	public ElevatorAndClimberAction(ClimberSubsystem climber, ElevatorSubsystem elevator, boolean climber2, boolean elevator2) {
		this.climber = climber;
		this.elevator = elevator;
		
		this.elevator2 = elevator2;
		this.climber2 = climber2;
	}
	
	@Override
	public void doAction() {
		if (elevator2 && climber2) {
			this.elevator.goToTop();
			Timer.delay(3);
			this.climber.goToTop();
		} else if (climber2) {
			this.climber.goToTop();
		} else if (elevator2) {
			this.elevator.goToTop();
		}
	}
}
