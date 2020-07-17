/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.climb;

public class climbEngage extends CommandBase {
  /**
   * Creates a new climbEngage.
   */
  climb m_climb;
  int direct;
  private BooleanSupplier m_left, m_right;
  
  public climbEngage(climb subsystem, int dir, BooleanSupplier left, BooleanSupplier right) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = subsystem;
    direct = dir;
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
      m_climb.driveLeftClimb(direct);
    }
    if(m_right.getAsBoolean()){
      m_climb.driveRightClimb(direct);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.driveLeftClimb(0);
    m_climb.driveRightClimb(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
