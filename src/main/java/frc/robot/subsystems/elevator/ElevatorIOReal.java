//package frc.robot.subsystems.elevator;
//
//import com.ctre.phoenix6.BaseStatusSignal;
//import com.ctre.phoenix6.StatusSignal;
//import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
//import com.ctre.phoenix6.configs.MotionMagicConfigs;
//import com.ctre.phoenix6.configs.MotorOutputConfigs;
//import com.ctre.phoenix6.configs.Slot0Configs;
//import com.ctre.phoenix6.configs.TalonFXConfigurator;
//import com.ctre.phoenix6.controls.Follower;
//import com.ctre.phoenix6.controls.MotionMagicVoltage;
//import com.ctre.phoenix6.controls.VoltageOut;
//import com.ctre.phoenix6.hardware.TalonFX;
//import com.ctre.phoenix6.signals.InvertedValue;
//import com.ctre.phoenix6.signals.NeutralModeValue;
//import edu.wpi.first.math.MathUtil;
//import edu.wpi.first.units.measure.Angle;
//import edu.wpi.first.units.measure.AngularVelocity;
//import edu.wpi.first.units.measure.Current;
//import edu.wpi.first.units.measure.Temperature;
//import edu.wpi.first.units.measure.Voltage;
//import frc.robot.RobotConstants;
//import frc.robot.RobotConstants.ElevatorGainsClass;
//
//import static frc.robot.RobotConstants.CANIVORE_CAN_BUS_NAME;
//import static frc.robot.RobotConstants.ElevatorConstants.*;
//
//
//public class ElevatorIOReal implements ElevatorIO {
//    /* Hardware */
//    private final TalonFX leader;
//    private final TalonFX follower;
//
//    /* Configurators */
//    private TalonFXConfigurator leaderConfigurator;
//    private TalonFXConfigurator followerConfigurator;
//
//    /* Configs */
//    private final CurrentLimitsConfigs currentLimitsConfigs;
//    private final MotorOutputConfigs leaderMotorConfigs;
//    private final MotorOutputConfigs followerMotorConfigs;
//    private final Slot0Configs slot0Configs;
//    private final MotionMagicConfigs motionMagicConfigs;
//
//    private final MotionMagicVoltage motionRequest = new MotionMagicVoltage(0.0).withEnableFOC(true);
//
//    private final StatusSignal<AngularVelocity> velocityLeft;
//    private final StatusSignal<AngularVelocity> velocityRight;
//    private final StatusSignal<Angle> positionLeft;
//    private final StatusSignal<Angle> positionRight;
//    private final StatusSignal<Voltage> voltageLeft;
//    private final StatusSignal<Voltage> voltageRight;
//    private final StatusSignal<Current> statorLeft;
//    private final StatusSignal<Current> statorRight;
//    private final StatusSignal<Current> supplyLeft;
//    private final StatusSignal<Current> supplyRight;
//    private final StatusSignal<Temperature> tempLeft;
//    private final StatusSignal<Temperature> tempRight;
//
//    public ElevatorIOReal() {
//        this.leader = new TalonFX(LEFT_ELEVATOR_MOTOR_ID, CANIVORE_CAN_BUS_NAME);
//        this.follower = new TalonFX(RIGHT_ELEVATOR_MOTOR_ID, CANIVORE_CAN_BUS_NAME);
//
//        this.leaderConfigurator = leader.getConfigurator();
//        this.followerConfigurator = follower.getConfigurator();
//
//        currentLimitsConfigs = new CurrentLimitsConfigs();
//        currentLimitsConfigs.StatorCurrentLimitEnable = true;
//        currentLimitsConfigs.SupplyCurrentLimitEnable = true;
//        currentLimitsConfigs.StatorCurrentLimit = 80.0;
//        currentLimitsConfigs.SupplyCurrentLimit = 30.0;
//
//        leaderMotorConfigs = new MotorOutputConfigs();
//        leaderMotorConfigs.NeutralMode = NeutralModeValue.Brake;
//        leaderMotorConfigs.Inverted = InvertedValue.Clockwise_Positive;
//        followerMotorConfigs = new MotorOutputConfigs();
//        followerMotorConfigs.NeutralMode = NeutralModeValue.Brake;
//
//        motionMagicConfigs = new MotionMagicConfigs();
//        motionMagicConfigs.MotionMagicAcceleration = motionAcceleration.get();
//        motionMagicConfigs.MotionMagicCruiseVelocity = motionCruiseVelocity.get();
//        motionMagicConfigs.MotionMagicJerk = motionJerk.get();
//
//        slot0Configs = new Slot0Configs();
//        slot0Configs.kA = ElevatorGainsClass.ELEVATOR_KA.get();
//        slot0Configs.kS = ElevatorGainsClass.ELEVATOR_KS.get();
//        slot0Configs.kV = ElevatorGainsClass.ELEVATOR_KV.get();
//        slot0Configs.kP = ElevatorGainsClass.ELEVATOR_KP.get();
//        slot0Configs.kI = ElevatorGainsClass.ELEVATOR_KI.get();
//        slot0Configs.kD = ElevatorGainsClass.ELEVATOR_KD.get();
//
//        leader.setPosition(0.0);
//        follower.setPosition(0.0);
//
//        leaderConfigurator.apply(currentLimitsConfigs);
//        leaderConfigurator.apply(leaderMotorConfigs);
//        leaderConfigurator.apply(slot0Configs);
//        leaderConfigurator.apply(motionMagicConfigs);
//        followerConfigurator.apply(currentLimitsConfigs);
//        followerConfigurator.apply(followerMotorConfigs);
//        followerConfigurator.apply(slot0Configs);
//        followerConfigurator.apply(motionMagicConfigs);
//
//        velocityLeft = leader.getVelocity();
//        velocityRight = follower.getVelocity();
//        positionLeft = leader.getPosition();
//        positionRight = leader.getPosition();
//        voltageLeft = leader.getSupplyVoltage();
//        voltageRight = follower.getSupplyVoltage();
//        statorLeft = leader.getStatorCurrent();
//        statorRight = follower.getStatorCurrent();
//        supplyLeft = leader.getSupplyCurrent();
//        supplyRight = follower.getSupplyCurrent();
//        tempLeft = leader.getDeviceTemp();
//        tempRight = follower.getDeviceTemp();
//
//        follower.setControl(new Follower(leader.getDeviceID(), true));
//    }
//
//    @Override
//    public void updateInputs(ElevatorIOInputs inputs) {
//        BaseStatusSignal.refreshAll(
//
//                velocityLeft, velocityRight,
//                positionLeft, positionRight,
//                voltageLeft, voltageRight,
//                statorLeft, statorRight,
//                supplyLeft, supplyRight,
//                tempLeft, tempRight
//
//        );
//
//        inputs.positionMeters = getElevatorHeight();
//        inputs.velocityMetersPerSec = getElevatorVelocity();
//        inputs.motionMagicVelocityTarget = talonPosToHeight(leader.getClosedLoopReferenceSlope().getValue());
//        inputs.motionMagicPositionTarget = talonPosToHeight(leader.getClosedLoopReference().getValue());
//        inputs.appliedVelocity = new double[] { velocityLeft.getValueAsDouble(), velocityRight.getValueAsDouble() };
//        inputs.appliedPosition = new double[] { positionLeft.getValueAsDouble(), positionRight.getValueAsDouble() };
//        inputs.appliedVolts = new double[] { voltageLeft.getValueAsDouble(), voltageRight.getValueAsDouble() };
//        inputs.statorCurrentAmps = new double[] { statorLeft.getValueAsDouble(), statorRight.getValueAsDouble() };
//        inputs.supplyCurrentAmps = new double[] { supplyLeft.getValueAsDouble(), supplyRight.getValueAsDouble() };
//        inputs.tempCelsius = new double[] { tempLeft.getValueAsDouble(), tempRight.getValueAsDouble() };
//
//        if (RobotConstants.TUNING) {
//            slot0Configs.kA = ElevatorGainsClass.ELEVATOR_KA.get();
//            slot0Configs.kS = ElevatorGainsClass.ELEVATOR_KS.get();
//            slot0Configs.kV = ElevatorGainsClass.ELEVATOR_KV.get();
//            slot0Configs.kP = ElevatorGainsClass.ELEVATOR_KP.get();
//            slot0Configs.kI = ElevatorGainsClass.ELEVATOR_KI.get();
//            slot0Configs.kD = ElevatorGainsClass.ELEVATOR_KD.get();
//            slot0Configs.kG = ElevatorGainsClass.ELEVATOR_KG.get();
//
//            motionMagicConfigs.MotionMagicAcceleration = motionAcceleration.get();
//            motionMagicConfigs.MotionMagicCruiseVelocity = motionCruiseVelocity.get();
//            motionMagicConfigs.MotionMagicJerk = motionJerk.get();
//
//            leaderConfigurator.apply(slot0Configs);
//            followerConfigurator.apply(slot0Configs);
//            leaderConfigurator.apply(motionMagicConfigs);
//            followerConfigurator.apply(motionMagicConfigs);
//        }
//    }
//
//    @Override
//    public void setElevatorDirectVoltage(double volts) {
//        leader.setControl(new VoltageOut(volts));
//    }
//
//    @Override
//    public void setElevatorTarget(double meters) {
//        leader.setControl(motionRequest.withPosition(heightToTalonPos(meters)));
//    }
//
//    @Override
//    public void resetElevatorPosition(){
//        leader.setPosition(0.0);
//        follower.setPosition(0.0);
//    }
//
//
//    @Override
//    public double getElevatorVelocity() {
//        return talonPosToHeight(leader.getVelocity().getValueAsDouble());
//    }
//
//    @Override
//    public double getElevatorHeight() {
//        return talonPosToHeight(leader.getPosition().getValueAsDouble());
//    }
//
//    @Override
//    public boolean isNearZeroExtension(){
//        return MathUtil.isNear(heightToTalonPos(0.05), leader.getPosition().getValueAsDouble(), 0.3);
//    }
//
//    @Override
//    public boolean isNearExtension(double expected) {
//        return MathUtil.isNear(heightToTalonPos(expected), leader.getPosition().getValueAsDouble(), 0.02);
//    }
//
//
//    private double heightToTalonPos(double heightMeters) {
//        return (heightMeters / (Math.PI * ELEVATOR_SPOOL_DIAMETER)) * ELEVATOR_GEAR_RATIO;
//    }
//
//    private double talonPosToHeight(double rotations) {
//        return rotations * (Math.PI * ELEVATOR_SPOOL_DIAMETER) / ELEVATOR_GEAR_RATIO;
//    }
//}