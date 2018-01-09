package org.usfirst.frc.team4131.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4131.robot.RobotMap;
import org.usfirst.frc.team4131.robot.commands.MoveCommand;

public class DriveBaseSubsystem extends Subsystem {
    // Control mode used to determine the drive speed
    private static final ControlMode CTL = ControlMode.PercentOutput;

    // Physical drive mappings
    private final TalonSRX l1;
    private final TalonSRX l2;
    private final TalonSRX r1;
    private final TalonSRX r2;

    public DriveBaseSubsystem() {
        this.l1 = new TalonSRX(RobotMap.L1);
        this.l2 = new TalonSRX(RobotMap.L2);
        this.r1 = new TalonSRX(RobotMap.R1);
        this.r2 = new TalonSRX(RobotMap.R2);
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new MoveCommand(this));
    }

    public void doMove(double l, double r) {
        this.l1.set(CTL, l);
        this.l2.set(CTL, l);
        this.r1.set(CTL, -r);
        this.r2.set(CTL, -r);
    }
}