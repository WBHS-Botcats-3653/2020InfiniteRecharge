/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.limelight;

public class changePipeline extends CommandBase {
  
  private boolean flag = false;
  private limelight m_lime;
  
  public changePipeline(limelight subsystem) {
    m_lime = subsystem;
    addRequirements(m_lime);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    m_lime.changePipe();
    // flag variable ensures command runs only once per press
    flag = true;
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return flag;
  }
}
