//package frc.robot.subsystems.endeffector;
//
//
//import static edu.wpi.first.units.Units.Volts;
//import static frc.robot.RobotConstants.LOOPER_DT;
//
//import edu.wpi.first.math.controller.ElevatorFeedforward;
//import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.math.controller.ProfiledPIDController;
//import edu.wpi.first.math.controller.SimpleMotorFeedforward;
//import edu.wpi.first.math.system.plant.DCMotor;
//import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
//import edu.wpi.first.units.Measure;
//import edu.wpi.first.units.VoltageUnit;
//import edu.wpi.first.wpilibj.simulation.DCMotorSim;
//import frc.robot.RobotConstants.ElevatorGainsClass;
//import frc.robot.RobotConstants.EndEffectorConstants.EndEffectorGainsClass;
//import frc.robot.subsystems.roller.RollerIOSim;
//import frc.robot.subsystems.roller.RollerIO.RollerIOInputs;
//public class EndEffectorIOSim extends RollerIOSim implements EndEffectorIO {
//
//    public EndEffectorIOSim() {
//        super(0.025,6.25,new SimpleMotorFeedforward(
//        EndEffectorGainsClass.ENDEFFECTOR_KS.get(),
//        EndEffectorGainsClass.ENDEFFECTOR_KV.get(),
//        EndEffectorGainsClass.ENDEFFECTOR_KA.get(),
//        LOOPER_DT),
//        new ProfiledPIDController(
//            ElevatorGainsClass.ELEVATOR_KP.get(),
//            ElevatorGainsClass.ELEVATOR_KI.get(),
//            ElevatorGainsClass.ELEVATOR_KD.get(),
//            new Constraints(5.0, 10.0)
//        ));
//    }
//}