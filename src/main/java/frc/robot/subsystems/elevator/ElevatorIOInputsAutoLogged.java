package frc.robot.subsystems.elevator;

import java.lang.Cloneable;
import java.lang.Override;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class ElevatorIOInputsAutoLogged extends ElevatorIO.ElevatorIOInputs implements LoggableInputs, Cloneable {
  @Override
  public void toLog(LogTable table) {
    table.put("LeftElevatorVelocity", leftElevatorVelocity);
    table.put("LeftElevatorPosition", leftElevatorPosition);
    table.put("LeftElevatorAppliedVoltage", leftElevatorAppliedVoltage);
    table.put("LeftElevatorSupplyCurrent", leftElevatorSupplyCurrent);
    table.put("RightElevatorVelocity", rightElevatorVelocity);
    table.put("RightElevatorPosition", rightElevatorPosition);
    table.put("RightElevatorAppliedVoltage", rightElevatorAppliedVoltage);
    table.put("RightElevatorSupplyCurrent", rightElevatorSupplyCurrent);
    table.put("TargetElevatorVelocity", targetElevatorVelocity);
    table.put("ElevatorKP", ElevatorKP);
    table.put("ElevatorKI", ElevatorKI);
    table.put("ElevatorKD", ElevatorKD);
    table.put("ElevatorKA", ElevatorKA);
    table.put("ElevatorKV", ElevatorKV);
    table.put("ElevatorKS", ElevatorKS);
  }

  @Override
  public void fromLog(LogTable table) {
    leftElevatorVelocity = table.get("LeftElevatorVelocity", leftElevatorVelocity);
    leftElevatorPosition = table.get("LeftElevatorPosition", leftElevatorPosition);
    leftElevatorAppliedVoltage = table.get("LeftElevatorAppliedVoltage", leftElevatorAppliedVoltage);
    leftElevatorSupplyCurrent = table.get("LeftElevatorSupplyCurrent", leftElevatorSupplyCurrent);
    rightElevatorVelocity = table.get("RightElevatorVelocity", rightElevatorVelocity);
    rightElevatorPosition = table.get("RightElevatorPosition", rightElevatorPosition);
    rightElevatorAppliedVoltage = table.get("RightElevatorAppliedVoltage", rightElevatorAppliedVoltage);
    rightElevatorSupplyCurrent = table.get("RightElevatorSupplyCurrent", rightElevatorSupplyCurrent);
    targetElevatorVelocity = table.get("TargetElevatorVelocity", targetElevatorVelocity);
    ElevatorKP = table.get("ElevatorKP", ElevatorKP);
    ElevatorKI = table.get("ElevatorKI", ElevatorKI);
    ElevatorKD = table.get("ElevatorKD", ElevatorKD);
    ElevatorKA = table.get("ElevatorKA", ElevatorKA);
    ElevatorKV = table.get("ElevatorKV", ElevatorKV);
    ElevatorKS = table.get("ElevatorKS", ElevatorKS);
  }

  public ElevatorIOInputsAutoLogged clone() {
    ElevatorIOInputsAutoLogged copy = new ElevatorIOInputsAutoLogged();
    copy.leftElevatorVelocity = this.leftElevatorVelocity;
    copy.leftElevatorPosition = this.leftElevatorPosition;
    copy.leftElevatorAppliedVoltage = this.leftElevatorAppliedVoltage;
    copy.leftElevatorSupplyCurrent = this.leftElevatorSupplyCurrent;
    copy.rightElevatorVelocity = this.rightElevatorVelocity;
    copy.rightElevatorPosition = this.rightElevatorPosition;
    copy.rightElevatorAppliedVoltage = this.rightElevatorAppliedVoltage;
    copy.rightElevatorSupplyCurrent = this.rightElevatorSupplyCurrent;
    copy.targetElevatorVelocity = this.targetElevatorVelocity;
    copy.ElevatorKP = this.ElevatorKP;
    copy.ElevatorKI = this.ElevatorKI;
    copy.ElevatorKD = this.ElevatorKD;
    copy.ElevatorKA = this.ElevatorKA;
    copy.ElevatorKV = this.ElevatorKV;
    copy.ElevatorKS = this.ElevatorKS;
    return copy;
  }
}
