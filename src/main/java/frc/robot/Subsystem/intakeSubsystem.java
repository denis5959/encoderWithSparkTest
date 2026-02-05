// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystem;

import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.PersistMode; // Importación corregida
import com.revrobotics.ResetMode;   // Importación corregida
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.FeedbackSensor;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class intakeSubsystem extends SubsystemBase {
  /** Creates a new intakeSubsystem. */
  private final SparkMax intakeMotor;
  private final SparkMaxConfig intakeMotorConfig;
  private final SparkClosedLoopController closedLoopController;
  private final SparkAbsoluteEncoder absoluteEncoder;
  

  public intakeSubsystem() {
    intakeMotor = new SparkMax(10, MotorType.kBrushless);
    intakeMotorConfig = new SparkMaxConfig();
    closedLoopController = intakeMotor.getClosedLoopController();
    absoluteEncoder = intakeMotor.getAbsoluteEncoder();

    intakeMotorConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(40);
    intakeMotorConfig.absoluteEncoder
    .positionConversionFactor(360.0) 
    .velocityConversionFactor(360.0 / 60.0)
    .setSparkMaxDataPortConfig();
    
    intakeMotorConfig.closedLoop.feedbackSensor(FeedbackSensor.kAbsoluteEncoder).p(0.0014).i(0).d(0);
    intakeMotorConfig.absoluteEncoder
    .positionConversionFactor(360.0) // Convertir 0-1 rotación a 0-360 grados
    .velocityConversionFactor(360.0 / 60.0) // RPM a grados/segundo
    .setSparkMaxDataPortConfig(); // ¡CRÍTICO! Habilita el encoder en el puerto de datos para SPARK MAX

    intakeMotor.configure(intakeMotorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  public void setpositionOne(double degrees){
    closedLoopController.setSetpoint(degrees, ControlType.kPosition);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Position", absoluteEncoder.getPosition());
  }
}
