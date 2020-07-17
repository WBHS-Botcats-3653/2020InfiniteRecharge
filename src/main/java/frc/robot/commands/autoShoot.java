/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.conveyer;
import frc.robot.subsystems.outtake;
import frc.robot.subsystems.storage;
import frc.robot.subsystems.digitalinputs;
import frc.robot.subsystems.limelight;

public class autoShoot extends CommandBase {
  /**
   * Creates a new autoShoot.
   */
conveyer m_delivery = null;
digitalinputs m_photoels = null;
storage m_store = null;
limelight m_lime = null;

double initialCount;
boolean flag;

  public autoShoot(conveyer subsystem1, digitalinputs subsystem3, storage subsystem4, limelight subsystem5) {
    m_delivery = subsystem1;
    m_photoels = subsystem3;
    m_store = subsystem4;
    m_lime = subsystem5;

    flag = false;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initialCount = m_photoels.getOutCount();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_delivery.deliveryDrive(1);
    m_store.storageDrive(1);
    DriverStation.reportError("inautoShoot",true);
    //SmartDashboard.putNumber("time", shootTime.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_delivery.deliveryDrive(0);
    m_store.storageDrive(0);
    //shootTime.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return (shootTime.get()*1000>time_to_elapse);
    return m_photoels.getOutCount()>=initialCount+3;
  }
}
