package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.VoltageUnit;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.RobotConstants;

import static edu.wpi.first.units.Units.*;
import static frc.robot.RobotConstants.ElevatorConstants.ELEVATOR_LEFT_MOTOR_ID;
import static frc.robot.RobotConstants.ElevatorConstants.ELEVATOR_RIGHT_MOTOR_ID;

import javax.print.attribute.SetOfIntegerSyntax;

public class ElevatorIOTalonFX implements ElevatorIO {
    private final TalonFX leftElevatorTalon = new TalonFX(ELEVATOR_LEFT_MOTOR_ID, RobotConstants.CAN_BUS_NAME);
    private final TalonFX rightElevatorTalon = new TalonFX(ELEVATOR_RIGHT_MOTOR_ID,
            RobotConstants.CAN_BUS_NAME);
    private final StatusSignal<AngularVelocity> leftElevatorVelocity = leftElevatorTalon.getVelocity();
    private final StatusSignal<Angle> leftElevatorPosition = leftElevatorTalon.getPosition();
    private final StatusSignal<Voltage> leftElevatorAppliedVoltage = leftElevatorTalon.getMotorVoltage();
    private final StatusSignal<Current> leftElevatorSupplyCurrent = leftElevatorTalon.getSupplyCurrent();
    private final StatusSignal<AngularVelocity> rightElevatorVelocity = rightElevatorTalon.getVelocity();
    private final StatusSignal<Angle> rightElevatorPosition = rightElevatorTalon.getPosition();
    private final StatusSignal<Voltage> rightElevatorAppliedVoltage = rightElevatorTalon.getMotorVoltage();
    private final StatusSignal<Current> rightElevatorSupplyCurrent = rightElevatorTalon.getSupplyCurrent();
    private double targetElevatorVelocity = 0;

    public ElevatorIOTalonFX() {
        var ElevatorMotorConfig = new TalonFXConfiguration();
        ElevatorMotorConfig.CurrentLimits.SupplyCurrentLimit = 30.0;
        ElevatorMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        ElevatorMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        ElevatorMotorConfig.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RotorSensor;
        ElevatorMotorConfig.Feedback.SensorToMechanismRatio = 1;
        leftElevatorTalon.getConfigurator().apply(ElevatorMotorConfig);
        var response = leftElevatorTalon.getConfigurator().apply(ElevatorMotorConfig);
        if (response.isError())
            System.out.println("Left Elevator TalonFX failed config with error" + response);
        response = leftElevatorTalon.clearStickyFaults();
        if (response.isError())
            System.out.println("Left Elevator TalonFX failed sticky fault clearing with error" + response);
        response = rightElevatorTalon.getConfigurator().apply(ElevatorMotorConfig);
        if (response.isError())
            System.out.println("Right Elevator TalonFX failed config with error" + response);
        response = rightElevatorTalon.clearStickyFaults();
        if (response.isError())
            System.out.println("Right Elevator TalonFX failed sticky fault clearing with error" + response);
        leftElevatorTalon.setInverted(false);
        rightElevatorTalon.setControl(new Follower(leftElevatorTalon.getDeviceID(),
                true));
    }

    public void runVolts(double volts) {
        leftElevatorTalon.setControl(new VoltageOut(volts));
    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {
        BaseStatusSignal.refreshAll(
                leftElevatorVelocity,
                leftElevatorPosition,
                leftElevatorAppliedVoltage,
                leftElevatorSupplyCurrent,
                rightElevatorVelocity,
                rightElevatorPosition,
                rightElevatorAppliedVoltage,
                rightElevatorSupplyCurrent);

        inputs.leftElevatorVelocity = RadiansPerSecond
                .of(Units.rotationsToRadians(leftElevatorVelocity.getValueAsDouble()));
        inputs.leftElevatorPosition = Radians.of(Units.rotationsToRadians(leftElevatorPosition.getValueAsDouble()));
        inputs.leftElevatorAppliedVoltage = Volts.of(leftElevatorAppliedVoltage.getValueAsDouble());
        inputs.leftElevatorSupplyCurrent = Amps.of(leftElevatorSupplyCurrent.getValueAsDouble());

        inputs.rightElevatorVelocity = RadiansPerSecond
                .of(Units.rotationsToRadians(rightElevatorVelocity.getValueAsDouble()));
        inputs.rightElevatorPosition = Radians.of(Units.rotationsToRadians(rightElevatorPosition.getValueAsDouble()));
        inputs.rightElevatorAppliedVoltage = Volts.of(rightElevatorAppliedVoltage.getValueAsDouble());
        inputs.rightElevatorSupplyCurrent = Amps.of(rightElevatorSupplyCurrent.getValueAsDouble());

        inputs.targetElevatorVelocity = RadiansPerSecond.of(targetElevatorVelocity);

        leftElevatorTalon.getConfigurator().apply(new Slot0Configs()
                .withKP(inputs.ElevatorKP)
                .withKI(inputs.ElevatorKI)
                .withKD(inputs.ElevatorKD)
                .withKA(inputs.ElevatorKA)
                .withKV(inputs.ElevatorKV)
                .withKS(inputs.ElevatorKS));
    }

    @Override
    public void setElevatorDirectVoltage(Measure<VoltageUnit> volts) {
        leftElevatorTalon.setControl(new VoltageOut(volts.magnitude()));
    }

    @Override
    public void setElevatorVelocity(double velocityRPM) {
        var velocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(velocityRPM);
        leftElevatorTalon.setControl(new VelocityVoltage(
                Units.radiansToRotations(velocityRadPerSec)
        ));
        targetElevatorVelocity = velocityRadPerSec;
    }

    @Override
    public void setElevatorVelocity(double velocityRPM, double ffVoltage) {
        var velocityRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(velocityRPM);
        leftElevatorTalon.setControl(new VelocityVoltage(
                Units.radiansToRotations(velocityRadPerSec)
        ));
        targetElevatorVelocity = velocityRadPerSec;
    }

    @Override
    public double getVelocity() {
        return rightElevatorVelocity.getValueAsDouble() * 60;
    }

}
