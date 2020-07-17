/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive;
import frc.robot.subsystems.driveSensors;;

public class autoArcade extends CommandBase {
  /**
   * Creates a new autoArcade.
   */
  drive m_drive = null;
  driveSensors m_sensors = null;

  boolean driveFlag = false;
  boolean turnFlag = false;


  double offsetDirection, m_distance,m_throttle,m_angle,m_turning,m_direction,m_initial_heading;
  public autoArcade(drive subsystem, driveSensors subsystem2, double distance, double throttle, double angle, double turning) {
    m_drive = subsystem;
    m_sensors = subsystem2;
    addRequirements(m_drive);

    m_distance = distance;
    m_throttle = throttle;
    m_angle = angle;
    m_turning = turning;

    m_direction = distance/Math.abs(distance);
    m_initial_heading = m_sensors.getAngle();

    offsetDirection = -angle/Math.abs(angle);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(m_throttle==0||m_distance==0){
      driveFlag=true;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(!(
      Math.abs((m_sensors.getLeftDistance()+m_sensors.getRightDistance())/2)
      > Math.abs(m_distance)
      )
      ){
      driveFlag = true;
      m_throttle = 0;
    }

    double offset = m_angle-(m_sensors.getAngle()-m_initial_heading);
    if(offset<0){
      turnFlag = true;
      m_turning = 0;
    }
    m_drive.differentialDrive(m_throttle*m_direction,m_turning*offsetDirection);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return turnFlag&&driveFlag;
  }
}
