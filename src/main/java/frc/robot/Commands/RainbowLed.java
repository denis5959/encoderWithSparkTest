package frc.robot.Commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Subsystem.LedSubsystem;

public class RainbowLed extends InstantCommand {

  private final LedSubsystem ledSubsystem;

  public RainbowLed(LedSubsystem ledSubsystem) {
    this.ledSubsystem = ledSubsystem;
    addRequirements(ledSubsystem);
  }

  @Override
  public void initialize() {
    ledSubsystem.setRainbow();
  }
}