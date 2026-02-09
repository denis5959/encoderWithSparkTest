// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystem.ShooterSubsystem;

public class SpinUpShooter extends Command {
  /** Command to spin up the shooter to target velocity while button is held */
  private final ShooterSubsystem shooterSubsystem;

  /**
   * Creates a new SpinUpShooter command.
   * @param subsystem The shooter subsystem to control
   */
  public SpinUpShooter(ShooterSubsystem subsystem) {
    this.shooterSubsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Start spinning up the shooter when button is pressed
    shooterSubsystem.spinUp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // The PID controller on the SPARK MAX handles velocity control automatically
    // We just need to keep the command running while button is held
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop the shooter when button is released
    shooterSubsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // This command runs as long as the button is held (whileTrue binding)
    // It will end when the button is released
    return false;
  }
}