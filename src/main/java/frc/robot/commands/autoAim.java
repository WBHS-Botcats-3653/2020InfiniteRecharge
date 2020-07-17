/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.limelight;
import frc.robot.Constants;
import frc.robot.subsystems.drive;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;

import java.util.function.DoubleSupplier;
/*
public class autoAim extends CommandBase {

  private final limelight m_lime;
  private final drive m_drive;
  private final DoubleSupplier m_speed;
  double min_val_static;
  boolean flag;

  public autoAim(limelight subsystem1, drive subsystem2, DoubleSupplier speed) {

    m_lime = subsystem1;
    m_drive = subsystem2;
    m_speed = speed;
    flag = false;
    addRequirements(m_lime,m_drive);

  }

  @Override
  public void initialize() {

    // starts motor at certain value to overcome static friction
    // (value is different because kinetic friction is smaller)
    double min_val_static = 0.45;
    if(m_lime.validTarget()){
      m_drive.differentialDrive(m_speed.getAsDouble(),min_val_static);
    }
  }

  @Override
  public void execute() {
    double x_angle_lime = m_lime.getX();
    if(m_lime.validTarget()){
      // values for how quickly to aim, minimum value at which the motor moves,
      // and minimum tolerated offset (epsilon)

      double steer_n = 0.01;
      double min_val = 0.46;
      double epsilon = 0.5;    

      double correction = m_lime.getCorrection();
      
      double x_angle_shooter = x_angle_lime-correction;
      double offset = Math.abs(x_angle_shooter);

      if(x_angle_shooter < 0){
        min_val *= -1;
      }

      //stops robot when offset is within tolerance, ends command
      if(offset < 2*epsilon){
        min_val = 0;
        //flag = true;
      }

      //drives robot according to steering constant, x offset
      //also uses controller input so robot can drive forward while aiming
      m_drive.differentialDrive(m_speed.getAsDouble(),(steer_n*x_angle_shooter+min_val)*-1);
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}*/

public  class autoAim extends PIDCommand {
  public autoAim(limelight lime, drive drive, DoubleSupplier speed){
    super(
      new PIDController(Constants.kP, Constants.kI, Constants.kD),
      lime::getX,
      lime::getCorrection,
      output -> drive.differentialDrive(speed.getAsDouble(),output-0.435*(lime.getX()-lime.getCorrection())/Math.abs(lime.getX()-lime.getCorrection())),
      drive
    );

    getController().setTolerance(1,0.41);
  }

  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}