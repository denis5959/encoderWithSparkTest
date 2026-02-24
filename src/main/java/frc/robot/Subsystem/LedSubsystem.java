package frc.robot.Subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.TejuinoBoard;

public class LedSubsystem extends SubsystemBase {
    
  private final TejuinoBoard tejuino = new TejuinoBoard();
  public LedSubsystem() {
    tejuino.init(tejuino.TEJUINO_DEVICE_NUMBER_1);
  }

  public void setRainbow() {
    tejuino.rainbow_effect(tejuino.LED_STRIP_0);
  }
  public void setRed() {
    tejuino.all_leds_red(tejuino.LED_STRIP_0);
    }

  public void turnOff() {
    tejuino.turn_off_all_leds(tejuino.LED_STRIP_0);
  }
}