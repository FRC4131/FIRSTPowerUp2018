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
import org.usfirst.frc.team4131.robot.ctl.TurnCtl;
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
    public static boolean isThrottleMode;
    private static int round;

 
    // Auton chooser
    private final SendableChooser<Procedure> chooser = new SendableChooser<>();

    // Limit Switches
    private final DigitalInput topClimberSwitch = new DigitalInput(2);
    private final DigitalInput bottomClimberSwitch = new DigitalInput(3);
    private final DigitalInput topElevatorSwitch = new DigitalInput(1);
    private final DigitalInput bottomElevatorSwitch = new DigitalInput(0);

    // Subsystem stuff
    private SubsystemProvider provider;

    public static void debug(Supplier<String> string) {
        if (round++ == 2000000) {
            System.out.println("DEBUG: " + string.get());
            round = 0;
        }
    }

    @Override
    public void robotInit() {
    	TurnCtl.getInstance().reset();
    	
        // Init subsystems
        this.provider = new SubsystemProvider(new DriveBaseSubsystem(),
                new ClawSubsystem(), new ClimberSubsystem(), new ElevatorSubsystem());

        
        // Init camera
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setVideoMode(new VideoMode(VideoMode.PixelFormat.kMJPEG, 600, 600, 10));

        // Compressor setup
        compressor.setClosedLoopControl(true);
        compressor.clearAllPCMStickyFaults();

        // Display auto procedures on dashboard
        this.chooser.addDefault("Left Right Baseline", new LeftRightBaseLine());
        //this.chooser.addDefault("Left Right Baseline", new DriverSTation2ToBaseLine());
        this.chooser.addObject("DriverStation 2 to switch", new DriverStation2ToSwitch());
        /*this.chooser.addObject("LeftToSwitchOrScale", new LeftToSwitchOrScale());
        this.chooser.addObject("RightToSwitchOrScale", new RightToSwitchOrScale());
        this.chooser.addObject("Encoder Calibration", new EncoderCalibration());
        */SmartDashboard.putData("Auto Mode", this.chooser);
        
        provider.getClaw().armUp();
        provider.getClaw().clamp();
    }

    @Override
    public void autonomousInit() {

    	provider.getDriveBase().reset();
    	
        String str = "";
        
        while (str.length() != 3) {
            str = DriverStation.getInstance().getGameSpecificMessage();
        }                                                               
        Side[] sides = new Side[str.length()];
        for (int i = 0, s = str.length(); i < s; i++) {
            sides[i] = Side.decode(str.charAt(i));
        }

        // We can now test with or without smart dashboard easily
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
    	Scheduler.getInstance().run();
    	
        // Prints Drivebase encoder value
        SmartDashboard.putNumber("Encoder Ticks", provider.getDriveBase().getDist());
        
    	// Limit switches
    	isClimberTop = this.topClimberSwitch.get();
        isClimberBottom = this.bottomClimberSwitch.get();
        isElevatorTop = this.topElevatorSwitch.get();
        isElevatorBottom = this.bottomElevatorSwitch.get();
    }

    // ----------------------------------------------------

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        // Inverting controls
        isInverted = Oi.INVERT_L_1.get() && Oi.INVERT_L_2.get() && Oi.INVERT_R_1.get() && Oi.INVERT_R_2.get();
        
        // Throttle Mode
        isThrottleMode = Oi.THROTTLE_MODE.get();

        // Limit switch stuff
        isClimberTop = this.topClimberSwitch.get();
        isClimberBottom = this.bottomClimberSwitch.get();
        isElevatorTop = this.topElevatorSwitch.get();
        isElevatorBottom = this.bottomElevatorSwitch.get();
      
        // Smart Dashboard Info
        SmartDashboard.putBoolean("Controls Inverted", isInverted);
        SmartDashboard.putBoolean("Throttle Mode", isThrottleMode);
        SmartDashboard.putBoolean("Elevator Top", isElevatorTop);
        SmartDashboard.putBoolean("Elevator Bottom", isElevatorBottom);
        SmartDashboard.putBoolean("Climber Top", isClimberTop);
        SmartDashboard.putBoolean("Climber Bottom", isClimberBottom);
        SmartDashboard.putNumber("Encoder Ticks", provider.getDriveBase().getDist());
    }
}