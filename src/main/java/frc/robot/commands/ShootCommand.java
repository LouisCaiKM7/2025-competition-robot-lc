//package frc.robot.commands;
//
//import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.subsystems.endeffector.EndEffectorSubsystem;
//
//public class ShootCommand extends Command{
//    private EndEffectorSubsystem endEffectorSubsystem;
//
//    public ShootCommand(EndEffectorSubsystem endEffectorSubsystem) {
//        this.endEffectorSubsystem = endEffectorSubsystem;
//    }
//    @Override
//        public void execute() {
//            endEffectorSubsystem.setWantedState(EndEffectorSubsystem.WantedState.SHOOT);
//        }
//
//        @Override
//        public boolean isFinished() {
//            return endEffectorSubsystem.isShootFinished();
//        }
//}
