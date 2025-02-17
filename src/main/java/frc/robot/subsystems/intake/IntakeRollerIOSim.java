//package frc.robot.subsystems.intake;
//
//import edu.wpi.first.math.controller.ProfiledPIDController;
//import edu.wpi.first.math.controller.SimpleMotorFeedforward;
//import edu.wpi.first.math.trajectory.TrapezoidProfile;
//import frc.robot.subsystems.roller.RollerIOSim;
//
//import static frc.robot.RobotConstants.intakeConstants.*;
//
//public class IntakeRollerIOSim extends RollerIOSim implements IntakeRollerIO {
//
//    public IntakeRollerIOSim() {
//        super(1, ROLLER_RATIO, new SimpleMotorFeedforward(0.0, 0.24),
//                new ProfiledPIDController(0.5, 0.0, 0.0,
//                        new TrapezoidProfile.Constraints(15,1)));
//    }
//}
