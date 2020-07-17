/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.digitalinputs;
import frc.robot.subsystems.storage;
import frc.robot.subsystems.conveyer;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.outtake;

public class powercellTracking extends CommandBase {
  /**
   * Creates a new powercellTracking.
   */

  digitalinputs m_photo = null;
  conveyer m_delivery = null;
  storage m_storage = null;
  intake m_intake = null;
  outtake m_outtake = null;

  boolean inval, delval, outval;
  boolean invalpre, delvalpre, outvalpre;

  public powercellTracking(digitalinputs subsystem1, conveyer subsystem2, storage subsystem3, intake subsystem4, outtake subsystem5) {
    m_photo = subsystem1;
    m_delivery = subsystem2;
    m_storage = subsystem3;
    m_intake = subsystem4;
    m_outtake = subsystem5;

    addRequirements(m_photo);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    inval = m_photo.getIn();
    delval = m_photo.getDel();
    outval = m_photo.getOut();

    if(outval==false && outvalpre==true && m_delivery.getDirection()>=0){
      m_photo.updateDelivery(-1);
    }
    if(delval==false && delvalpre==true && (m_storage.getDirection()>0 || m_delivery.getDirection()>0)){
      m_photo.updateDelivery(1);
      m_photo.updateStorage(-1);
    }
    if(inval==true && invalpre==false && m_intake.getDirection()>=0 && m_storage.getDirection()>=0){
      m_photo.updateStorage(1);
    }
    if(inval==false && invalpre==true && m_storage.getDirection()<=0){
      m_photo.updateStorage(-1);
    }

    invalpre = m_photo.getIn();
    delvalpre = m_photo.getDel();
    outvalpre = m_photo.getOut();
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
