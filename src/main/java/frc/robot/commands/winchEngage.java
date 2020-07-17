/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.climb;
import java.util.function.BooleanSupplier;

public class winchEngage extends CommandBase {
  /**
   * Creates a new winchEngage.
   */
  private climb m_climb = null;
  private double dir; 
  private BooleanSupplier m_left, m_right;

  public winchEngage(climb subsystem, double direction, BooleanSupplier left, BooleanSupplier right) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = subsystem;
    dir = direction;
    m_left = left;
    m_right = right;
    addRequirements(m_climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_left.getAsBoolean()){
      m_climb.driveLeftWinch(dir);
    }
    if(m_right.getAsBoolean()){
      m_climb.driveRightWinch(dir);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.driveLeftWinch(0);
    m_climb.driveRightWinch(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
