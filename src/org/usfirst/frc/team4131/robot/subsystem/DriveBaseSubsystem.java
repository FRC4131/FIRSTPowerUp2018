package org.usfirst.frc.team4131.robot.subsystem;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4131.robot.RobotMap;
import org.usfirst.frc.team4131.robot.command.MoveCommand;

import static org.usfirst.frc.team4131.robot.Oi.sigl;
import static org.usfirst.frc.team4131.robot.Oi.sigr;

/**
 * The drive base subsystem, linking the 4 Talon SRX
 * controllers and their respective motors.
 */
public class DriveBaseSubsystem extends Subsystem {
    // Sensor constants
    private static final int PID_IDX = 0;
    private static final int SENSOR_TIMEOUT = 0;

    private static final int ERROR_DELTA = 0;

    // Physical drive mappings
    private final TalonSRX left;
    private final TalonSRX right;
    private final TalonSRX right2;

    /**
     * Creates and caches the motors used for the drive base
     */
    public DriveBaseSubsystem() {
        this.left = new TalonSRX(RobotMap.L1);
        new TalonSRX(RobotMap.L2).follow(this.left);

        this.right = new TalonSRX(RobotMap.R1);
        this.right2 = new TalonSRX(RobotMap.R2);
        this.right2.follow(this.right);

        this.setupEncoder();
        this.reset();
        this.setupPid(this.left);
        this.setupPid(this.right);
        this.setupPid(this.right2);
    }

    @Override
    protected void initDefaultCommand() {
        this.setDefaultCommand(new MoveCommand(this));
    }

    // Talon setup methods ---------------------------------

    /**
     * Configures the selected sensor to the quadrature
     * encoder on the left and right motors.
     */
    private void setupEncoder() {
        ErrorCode code = this.left.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_IDX, SENSOR_TIMEOUT);
        if (code.value != 0) {
            DriverStation.reportError("Error occurred configuring quad encoders", false);
        }
    }

    /**
     * Resets the position indicated by the quadencoders to
     * {@code 0}.
     */
    public void reset() {
        ErrorCode code = this.left.setSelectedSensorPosition(0, PID_IDX, SENSOR_TIMEOUT);
        if (code.value != 0) {
            DriverStation.reportError("Error occurred resetting quad encoders", false);
        }
    }

    /**
     * Configures the PID constants on the given talon
     * device.
     *
     * @param talon the talon on which the PID constants
     * will be configured
     */
    private void setupPid(TalonSRX talon) {
        ErrorCode code = ErrorCode.worstOne(talon.config_kP(PID_IDX, 0.4, SENSOR_TIMEOUT),
                talon.config_kI(PID_IDX, 0, SENSOR_TIMEOUT),
                talon.config_kD(PID_IDX, 0, SENSOR_TIMEOUT),
                talon.config_kF(PID_IDX, 0, SENSOR_TIMEOUT));
        ErrorCode code0 = ErrorCode.worstOne(talon.configAllowableClosedloopError(PID_IDX, ERROR_DELTA, SENSOR_TIMEOUT),
                // talon.configVoltageCompSaturation(5, SENSOR_TIMEOUT),
                talon.configClosedloopRamp(0.3, SENSOR_TIMEOUT));
        // talon.configOpenloopRamp(0.3, SENSOR_TIMEOUT));
        // talon.enableVoltageCompensation(true);
        if (code.value != 0 || code0.value != 0) {
            DriverStation.reportError("Error occurred configuring PIDF", false);
        }
    }

    /**
     * Print Amperage Voltage Velocity
     *
     * @param speed the speed currently being input
     * @param round the loop round
     */
    public void pavv(double speed, int round) {
        if (round % 100000 == 0) {
            System.out.println("INPUT=" + speed
                    + " ENCODER VEL=" + this.left.getSelectedSensorVelocity(PID_IDX)
                    + " AMPS=" + this.left.getOutputCurrent()
                    + " VOLTS=" + this.left.getBusVoltage());
        }
    }

    // Talon control ---------------------------------------

    /**
     * Changes the speed of the motors to the given
     * constant output mode (-1 to 1).
     *
     * @param l the left motor speed
     * @param r the right motor speed
     */
    public void doThrottle(double l, double r) {
    	l = l * l * l;
    	r = r * r * r;
        this.left.set(ControlMode.PercentOutput, sigl() * l);
        this.right.set(ControlMode.PercentOutput, sigr() * r);
        this.right2.set(ControlMode.PercentOutput, sigr() * r);
    }

    /**
     * Prepares the correct talon configuration in
     * order to use the {@Link #gotoPosition(int)}
     * method.
     */
    public void prepareAuto() {
    	this.right.follow(this.left);
    	this.right2.follow(this.left);
    	this.right.setInverted(true);
    	this.right2.setInverted(true);
    }
    
    /**
     * Performs a PID-controlled movement using the
     * encoder target tick.
     *
     * @param ticks the number of ticks to move the
     * robot
     */
    public void gotoPosition(int ticks) {
        this.left.set(ControlMode.Position, ticks);
    }
    
    /**
     * Ends autonomous configuration on the talons,
     * resets them so that teleop will work
     * correctly.
     */
    public void prepareTeleop() {
    	this.right.set(ControlMode.PercentOutput, 0);
    	this.right2.follow(this.right);    	
    	this.right.setInverted(false);
    	this.right2.setInverted(false);
    }

    public void setVelocity(int vel) {
        this.left.set(ControlMode.Velocity, sigl() * vel);
    }

    public void setVelocityLeft(int vel) {
        this.left.set(ControlMode.Velocity, sigl() * vel);
    }

    // Sensor polling methods ------------------------------
    // DO NOT use SensorCollection here

    public int getLeftVelocity() {
        return sigl() * this.left.getSelectedSensorVelocity(PID_IDX);
    }

    public int getRightVelocity() {
    	return sigr() * this.right.getSelectedSensorVelocity(PID_IDX);
    }

    /**
     * Obtains the tick count for the left encoder.
     *
     * @return the ticks since the last reset travelled by
     * the left encoder
     */
    public int getLeftDist() {
        return sigl() * this.left.getSelectedSensorPosition(PID_IDX);
    }
    
    public int getRightDist() {
    	return sigr() * this.right.getSelectedSensorPosition(PID_IDX);
    }
}