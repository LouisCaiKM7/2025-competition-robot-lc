package frc.robot.subsystems.elevator;

import edu.wpi.first.units.*;
import edu.wpi.first.units.measure.AngularVelocity;

import org.littletonrobotics.junction.AutoLog;

import static edu.wpi.first.units.Units.*;
import static frc.robot.RobotConstants.ElevatorConstants.elevatorGainsClass.*;

public interface ElevatorIO {
    void updateInputs(ElevatorIOInputs inputs);

    void setElevatorDirectVoltage(Measure<VoltageUnit> volts);

    void setElevatorVelocity(double velocityRPM, double ffVoltage);

    void setElevatorVelocity(double velocityRPM);

    void runVolts(double volts);

    double getVelocity();


    @AutoLog
    class ElevatorIOInputs {
        public Measure<AngularVelocityUnit> leftElevatorVelocity = RadiansPerSecond.zero();
        public Measure<AngleUnit> leftElevatorPosition = Radians.zero();
        public Measure<VoltageUnit> leftElevatorAppliedVoltage = Volts.zero();
        public Measure<CurrentUnit> leftElevatorSupplyCurrent = Amps.zero();

        public Measure<AngularVelocityUnit> rightElevatorVelocity = RadiansPerSecond.zero();
        public Measure<AngleUnit> rightElevatorPosition = Radians.zero();
        public Measure<VoltageUnit> rightElevatorAppliedVoltage = Volts.zero();
        public Measure<CurrentUnit> rightElevatorSupplyCurrent = Amps.zero();

        public Measure<AngularVelocityUnit> targetElevatorVelocity = RadiansPerSecond.zero();

        public double ElevatorKP = ELEVATOR_KP.get();
        public double ElevatorKI = ELEVATOR_KI.get();
        public double ElevatorKD = ELEVATOR_KD.get();
        public double ElevatorKA = ELEVATOR_KA.get();
        public double ElevatorKV = ELEVATOR_KV.get();
        public double ElevatorKS = ELEVATOR_KS.get();
    }
}
