/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class digitalinputs extends SubsystemBase {
  /**
   * Creates a new photoelectrics.
   */

  DigitalInput photoIn, photoDel;
  DigitalInput photoOut;
  boolean photoInVal, photoDelVal, photoOutVal;
  boolean InValPre, DelValPre, OutValPre;
  boolean outValPre = false;
  Counter outCounter = null;

  int storageCells;
  int deliveryCells;

  boolean autoShot;


  public digitalinputs() {
    storageCells = 0;
    deliveryCells = 0;
    autoShot = false;

    photoIn = new DigitalInput(Constants.photoElectricIntake);
    photoDel = new DigitalInput(Constants.photoElectricDelivery);
    //photoOut = new DigitalInput(Constants.photoElectricOuttake);
  
    outCounter = new Counter(Constants.photoElectricOuttake);
    
  }

  public boolean getIn(){
    return photoIn.get();
  }
  public boolean getDel(){
    return photoDel.get();
  }
  public boolean getOut(){
    return false;
  }
  public double getOutCount(){
    return outCounter.get();
  }

  public void updateStorage(int delta){
    storageCells += delta;
  }

  public void updateDelivery(int delta){
    deliveryCells += delta;
    if(delta<0){
      autoShot=true;
    }
  }

  public void resetAutoShot(){
    autoShot = false;
  }

  public boolean getAutoShot(){
    return autoShot;
  }
  @Override
  public void periodic() {
    boolean outval = getOut();
    if(outval == false && outValPre == true){
      autoShot=true;
    }
    SmartDashboard.putNumber("storage", storageCells);
    SmartDashboard.putNumber("delivery", deliveryCells);
    SmartDashboard.putBoolean("photo1",getIn());
    SmartDashboard.putBoolean("photo2",getDel());
    SmartDashboard.putBoolean("photo3",getOut());
    SmartDashboard.putNumber("outCounter",outCounter.get());
    boolean outValPre = getOut();
  }
}
