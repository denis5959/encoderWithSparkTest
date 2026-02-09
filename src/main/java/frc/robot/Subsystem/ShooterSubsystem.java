// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystem;

import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ShooterSubsystem with velocity PID control for shooting balls */
  private final SparkMax shooterMotor;
  private final SparkMaxConfig shooterMotorConfig;
  private final SparkClosedLoopController closedLoopController;
  private final RelativeEncoder encoder;
  
  // PID Constants for velocity control - these will need tuning
  private static final double kP = 0.000125;     
private static final double kI = 0.0;
private static final double kD = 0.00003;
private static final double kFF = 0.0020475;    
  
  // Target velocity in RPM
  private static final double TARGET_VELOCITY_RPM = 3000; // Adjust this based on your shooting needs
  
  // Conversion factor: 0 to .60 motor output correlates to velocity
  private static final double MAX_OUTPUT_PERCENT = 0.60;

  public ShooterSubsystem() {
    // Initialize motor on CAN ID 14
    shooterMotor = new SparkMax(14, MotorType.kBrushless);
    shooterMotorConfig = new SparkMaxConfig();
    closedLoopController = shooterMotor.getClosedLoopController();
    encoder = shooterMotor.getEncoder();

    // Configure motor settings
    shooterMotorConfig
      .idleMode(IdleMode.kCoast) // Coast mode is better for flywheels
      .smartCurrentLimit(40)
      .inverted(false); // Change if motor spins wrong direction
    
    // Configure PID for velocity control
    shooterMotorConfig.closedLoop
      .p(kP)
      .i(kI)
      .d(kD)
      .velocityFF(kFF)
      .outputRange(-MAX_OUTPUT_PERCENT, MAX_OUTPUT_PERCENT);
    
    // Optional: Set ramp rate to prevent voltage spikes
    
    //shooterMotorConfig.closedLoopRampRate(0.5); // seconds from 0 to full
    
    // Apply configuration
    shooterMotor.configure(shooterMotorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  /**
   * Spin up the shooter to target velocity
   */
  public void spinUp() {
    closedLoopController.setReference(TARGET_VELOCITY_RPM, ControlType.kVelocity);
  }

  /**
   * Stop the shooter
   */
  public void stop() {
    shooterMotor.stopMotor();
  }

  /**
   * Set a custom velocity target
   * @param velocityRPM Target velocity in RPM
   */
  public void setVelocity(double velocityRPM) {
    closedLoopController.setReference(velocityRPM, ControlType.kVelocity);
  }

  /**
   * Get current velocity of the shooter
   * @return Current velocity in RPM
   */
  public double getVelocity() {
    return encoder.getVelocity();
  }

  /**
   * Check if shooter is at target velocity
   * @param tolerance Acceptable error in RPM
   * @return true if within tolerance
   */
  public boolean atTargetVelocity(double tolerance) {
    return Math.abs(getVelocity() - TARGET_VELOCITY_RPM) < tolerance;
  }

  @Override
  public void periodic() {

    
    SmartDashboard.putNumber("Shooter Velocity (RPM)", getVelocity());
    SmartDashboard.putNumber("Shooter Target (RPM)", TARGET_VELOCITY_RPM);
    SmartDashboard.putNumber("Shooter Output", shooterMotor.getAppliedOutput());
    SmartDashboard.putBoolean("Shooter At Speed", atTargetVelocity(100)); // 100 RPM tolerance
  }
}