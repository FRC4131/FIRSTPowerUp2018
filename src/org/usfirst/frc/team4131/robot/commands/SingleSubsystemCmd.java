package org.usfirst.frc.team4131.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class SingleSubsystemCmd<T extends Subsystem> extends Command {
    protected final T subsystem;

    public SingleSubsystemCmd(T subsystem) {
        this.subsystem = subsystem;
        this.requires(subsystem);
    }
}