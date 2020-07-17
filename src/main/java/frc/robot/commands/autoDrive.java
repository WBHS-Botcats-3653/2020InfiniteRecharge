/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drive;
import frc.robot.subsystems.driveSensors;
public class autoDrive extends CommandBase {
  /**
   * Creates a new autoDrive.
   */
  drive m_drive = null;
  driveSensors m_encoders = null;
  double distance;
  double direction;
  double init_angle;
  boolean flag;
  
  public autoDrive(drive subsystem1, driveSensors subsystem2, double dist) {
    flag = false;
    m_drive = subsystem1;
    m_encoders = subsystem2;
    distance = dist;
    init_angle = m_encoders.getAngle();

    direction = distance/Math.abs(distance);
    m_encoders.resetEncoders();
    addRequirements(m_drive);
    // Use addRequirements() here to declare subsystem dependencies.
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double angle = m_encoders.getAngle();
    double heading_stable = 0;
    double error = (Math.abs(distance-m_encoders.getLeftDistance()))/distance;

    if(angle<init_angle){
      angle = Constants.minTurn;
    } else if (angle>init_angle){
      angle = -1*Constants.minTurn;
    }

    if(Math.abs(m_encoders.getLeftDistance())<Math.abs(distance)){
      m_drive.differentialDrive(Constants.autoDrive*direction,heading_stable);
    } else {
      flag = true;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.differentialDrive(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return flag;
  }
}
