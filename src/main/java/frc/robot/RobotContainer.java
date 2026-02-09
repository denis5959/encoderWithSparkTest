// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import frc.robot.Commands.intakePositionOne;
import frc.robot.Commands.intakePositionTwo;
import frc.robot.Commands.SpinUpShooter;
import frc.robot.Subsystem.intakeSubsystem;
import frc.robot.Subsystem.ShooterSubsystem;


public class RobotContainer {

  intakeSubsystem intakeSubsystem = new intakeSubsystem();
  intakePositionOne intakePositionOne = new intakePositionOne(intakeSubsystem);
  intakePositionTwo intakePositionTwo = new intakePositionTwo(intakeSubsystem);

  ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  SpinUpShooter spinUpShooter = new SpinUpShooter(shooterSubsystem);

  private final CommandPS4Controller CommandPS4Controller = new CommandPS4Controller(1);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    CommandPS4Controller.circle().onTrue(intakePositionOne);
    CommandPS4Controller.triangle().onTrue(intakePositionTwo);
    
    // D-pad up: Spin up shooter while held, stop when released
    CommandPS4Controller.povUp().whileTrue(spinUpShooter);
  }

  public Command getAutonomousCommand() {
    
    return Commands.print("No autonomous command configured");
  }
}