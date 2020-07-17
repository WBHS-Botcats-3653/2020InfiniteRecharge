/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.drive;

import java.util.function.DoubleSupplier;

public class differentialDrive extends CommandBase {
  /**
   * Creates a new differentialDrive.
   */
  private final drive m_drive;
  private final DoubleSupplier m_speed;
  private final DoubleSupplier m_angle;

  public differentialDrive(drive subsystem, DoubleSupplier forward, DoubleSupplier turn) {
    m_drive = subsystem;
    m_speed = forward;
    m_angle = turn;
    addRequirements(m_drive);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // activates drive with controller input
    m_drive.differentialDrive(m_speed.getAsDouble(), -1*m_angle.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.differentialDrive(0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
