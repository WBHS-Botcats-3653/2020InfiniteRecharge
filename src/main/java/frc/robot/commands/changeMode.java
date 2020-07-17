/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class changeMode extends CommandBase {
  /**
   * Creates a new changeMode.
   */
  private limelight m_lime;
  private boolean flag;

  public changeMode(limelight subsystem) {
    flag = false;
    m_lime = new limelight();

    addRequirements(m_lime);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    m_lime.changeMode();
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
