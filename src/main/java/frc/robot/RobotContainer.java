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
import frc.robot.Subsystem.LedSubsystem;   // <-- AGREGAR
import frc.robot.Commands.RainbowLed;     


import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {

  intakeSubsystem intakeSubsystem = new intakeSubsystem();
  intakePositionOne intakePositionOne = new intakePositionOne(intakeSubsystem);
  intakePositionTwo intakePositionTwo = new intakePositionTwo(intakeSubsystem);

  ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  SpinUpShooter spinUpShooter = new SpinUpShooter(shooterSubsystem);

  LedSubsystem ledSubsystem = new LedSubsystem();     
  RainbowLed rainbowLed = new RainbowLed(ledSubsystem);
  private final CommandPS4Controller CommandPS4Controller = new CommandPS4Controller(1);
  private final NetworkTableEntry ledToggleEntry = NetworkTableInstance.getDefault()
      .getTable("SmartDashboard")
      .getEntry("Rainbow Toggle");

  public RobotContainer() {
    ledToggleEntry.setBoolean(false); // Initialize the toggle to false
    configureBindings();
    rainbowLed.schedule();
  }

  private void configureBindings() {
    CommandPS4Controller.circle().onTrue(intakePositionOne);
    CommandPS4Controller.triangle().onTrue(intakePositionTwo);
    
    // D-pad up: Spin up shooter while held, stop when released
    CommandPS4Controller.povUp().whileTrue(spinUpShooter);

    Trigger rainbowTrigger = new Trigger(() -> ledToggleEntry.getBoolean(false));

    rainbowTrigger.onTrue(Commands.runOnce(() -> ledSubsystem.setRainbow(), ledSubsystem));
    rainbowTrigger.onFalse(Commands.runOnce(() -> ledSubsystem.setRed(), ledSubsystem));
  }

  public Command getAutonomousCommand() {
    
    return Commands.print("No autonomous command configured");
  }
}