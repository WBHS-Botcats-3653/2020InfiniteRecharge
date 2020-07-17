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
public class autoTurn extends CommandBase {
  /**
   * Creates a new autoDrive.
   */
  drive m_drive = null;
  driveSensors m_gyro = null;
  double angle;
  double initial_heading;
  boolean flag;
  
  public autoTurn(drive subsystem1, driveSensors subsystem2, double theta) {
    flag = false;
    
    m_drive = subsystem1;
    m_gyro = subsystem2;
    angle = theta;
    
    initial_heading = m_gyro.getAngle();
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
    double offset = angle-(m_gyro.getAngle()-initial_heading);
    m_drive.differentialDrive(0,0.5*offset/Math.abs(offset));
    if(offset<1){
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
