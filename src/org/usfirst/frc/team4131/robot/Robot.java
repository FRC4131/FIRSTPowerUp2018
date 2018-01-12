/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4131.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4131.robot.subsystems.DriveBaseSubsystem;

/**
 * Robot lifecycle handler.
 */
public class Robot extends IterativeRobot {
    private Command autoCommand;
    private final SendableChooser<Command> chooser = new SendableChooser<>();

    @Override
    public void robotInit() {
        // Run init block for OI
        // TODO: If no init needed, remove this
        Oi.init();

        // Init subsystems
        DriveBaseSubsystem driveBase = new DriveBaseSubsystem();

        // TODO: Register auton Commands to chooser
        // this.chooser.addDefault("Default", new AutoDefaultCommand(driveBase));
        SmartDashboard.putData("Auto mode", this.chooser);
    }

    // DISABLED MODE ---------------------------------------

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    // AUTONOMOUS ------------------------------------------

    @Override
    public void autonomousInit() {
        String str = DriverStation.getInstance().getGameSpecificMessage();

        this.autoCommand = this.chooser.getSelected();

        if (this.autoCommand != null) {
            this.autoCommand.start();
        }
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    // HUMAN-OPERATED --------------------------------------

    @Override
    public void teleopInit() {
        if (this.autoCommand != null) {
            this.autoCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    // TEST-MODE -------------------------------------------

    @Override
    public void testPeriodic() {
    }
}