/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4131.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4131.robot.auto.Action;
import org.usfirst.frc.team4131.robot.auto.Procedure;
import org.usfirst.frc.team4131.robot.auto.Side;
import org.usfirst.frc.team4131.robot.auto.procedure.*;
import org.usfirst.frc.team4131.robot.subsystem.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Robot lifecycle handler.
 */
public class Robot extends IterativeRobot {
    // Compressor stuff
    private static final Compressor compressor = new Compressor(61);

    // Booleans for random functions
    public static boolean isInverted;
    public static boolean isClimberTop;
    public static boolean isClimberBottom;
    public static boolean isElevatorTop;
    public static boolean isElevatorBottom;
    private static int round;

    // Auton chooser
    private final SendableChooser<Procedure> chooser = new SendableChooser<>();

    // Limit Switches
    private final DigitalInput topClimberSwitch = new DigitalInput(0);
    private final DigitalInput bottomClimberSwitch = new DigitalInput(1);
    private final DigitalInput topElevatorSwitch = new DigitalInput(2);
    private final DigitalInput bottomElevatorSwitch = new DigitalInput(3);

    // Subsystem stuff
    private SubsystemProvider provider;

    public static void debug(Supplier<String> string) {
        if (round++ == 20000) {
            System.out.println("DEBUG: " + string.get());
            round = 0;
        }
    }

    @Override
    public void robotInit() {
        // Init subsystems
        this.provider = new SubsystemProvider(new DriveBaseSubsystem(),
                new ClawSubsystem(), new ClimberSubsystem(), new ElevatorSubsystem());

        // Init camera
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setVideoMode(new VideoMode(VideoMode.PixelFormat.kMJPEG, 600, 600, 10));

        this.chooser.addDefault("Left Right Baseline", new LeftRightBaseline());
        this.chooser.addObject("Encoder Calibration", new EncoderCalibration());
        this.chooser.addObject("Move 12 then Turn 90", new Move12ThenTurn90());
        this.chooser.addObject("Turn 90 Degrees", new Turn90());
        this.chooser.addObject("Move 12 Inches", new Move12());
        this.chooser.addObject("Turn 180 degrees", new Turn180());
        this.chooser.addObject("Ramp Test Procedure", new Ramp());
        SmartDashboard.putData("Auto mode", this.chooser);

        compressor.setClosedLoopControl(true);
        compressor.clearAllPCMStickyFaults();
    }

    @Override
    public void autonomousInit() {
        String str = DriverStation.getInstance().getGameSpecificMessage();
        Side[] sides = new Side[str.length()];
        for (int i = 0, s = str.length(); i < s; i++) {
            sides[i] = Side.decode(str.charAt(i));
        }
        
        this.provider.getDriveBase().prepareAuto();
        Procedure procedure = this.chooser.getSelected();
        List<Action> actions = new ArrayList<>(procedure.estimateLen());
        procedure.populate(this.provider, Arrays.asList(sides), actions);

        for (int i = 0, s = actions.size(); i < s; i++) {
            Action action = actions.get(i);
            action.doAction();
        }
    }

    @Override
    public void autonomousPeriodic() {
    }

    // ----------------------------------------------------

    @Override
    public void teleopInit() {
    	this.provider.getDriveBase().prepareTeleop();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        // Limit switch stuff
        isClimberTop = this.topClimberSwitch.get();
        isClimberBottom = this.bottomClimberSwitch.get();
        isElevatorTop = this.topElevatorSwitch.get();
        isElevatorBottom = this.bottomElevatorSwitch.get();

        // Inverting controls
        isInverted = Oi.INVERT_L_1.get() && Oi.INVERT_L_2.get() && Oi.INVERT_R_1.get() && Oi.INVERT_R_2.get();
    }
}