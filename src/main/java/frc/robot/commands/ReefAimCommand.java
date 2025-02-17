//package frc.robot.commands;
//
//import edu.wpi.first.math.MathUtil;
//import edu.wpi.first.math.geometry.Pose2d;
//import edu.wpi.first.math.geometry.Transform2d;
//import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.RobotConstants;
//import frc.robot.subsystems.apriltagvision.AprilTagVision;
//import frc.robot.subsystems.swerve.Swerve;
//
//// Command to aim the robot at a specific reef using AprilTag vision
//public class ReefAimCommand extends Command {
//    private final AprilTagVision aprilTagVision;
//    private final Swerve swerve = Swerve.getInstance();
//    private final int tagID;
//    private final boolean rightReef; // true if shooting right reef
//    private Pose2d robotPose;
//    private Pose2d tagPose;
//    private Pose2d destinationPose;
//    private Transform2d transform;
//    private boolean isFinished = false;
//
//    // Constructor for ReefAimCommand
//    public ReefAimCommand(AprilTagVision aprilTagVision, int tagID, boolean rightReef) {
//        this.aprilTagVision = aprilTagVision;
//        // each subsystem used by the command must be passed into the
//        // addRequirements() method (which takes a vararg of Subsystem)
//        addRequirements(this.aprilTagVision, this.swerve);
//        this.tagID = tagID;
//        this.rightReef = rightReef;
//    }
//
//    /**
//     * The initial subroutine of a command. Called once when the command is
//     * initially scheduled.
//     */
//    @Override
//    public void initialize() {
//        this.robotPose = this.aprilTagVision.getRobotPose3d().toPose2d();
//        this.tagPose = this.aprilTagVision.getAllTagPoses().get(this.tagID).toPose2d();
//        if (this.rightReef) {
//            this.destinationPose = this.tagPose.transformBy(RobotConstants.ReefAimConstants.tagRightToRobot);
//        } else {
//            this.destinationPose = this.tagPose.transformBy(RobotConstants.ReefAimConstants.tagLeftToRobot);
//        }
//        this.transform = this.destinationPose.minus(this.robotPose);
//    }
//
//    /**
//     * The main body of a command. Called repeatedly while the command is scheduled.
//     * (That is, it is called repeatedly until {@link #isFinished()}) returns true.)
//     */
//    @Override
//    public void execute() {
//        this.swerve.drive(this.transform.getTranslation(),
//                MathUtil.angleModulus(this.transform.getRotation().getRadians()), false, false);
//        this.isFinished = true;
//    }
//
//    /**
//     * <p>
//     * Returns whether this command has finished. Once a command finishes --
//     * indicated by
//     * this method returning true -- the scheduler will call its
//     * {@link #end(boolean)} method.
//     * </p>
//     * <p>
//     * Returning false will result in the command never ending automatically. It may
//     * still be
//     * cancelled manually or interrupted by another command. Hard coding this
//     * command to always
//     * return true will result in the command executing once and finishing
//     * immediately. It is
//     * recommended to use * {@link edu.wpi.first.wpilibj2.command.InstantCommand
//     * InstantCommand}
//     * for such an operation.
//     * </p>
//     *
//     * @return whether this command has finished.
//     */
//    @Override
//    public boolean isFinished() {
//        // TODO: Make this return true when this Command no longer needs to run
//        // execute()
//        return this.isFinished;
//    }
//
//    /**
//     * The action to take when the command ends. Called when either the command
//     * finishes normally -- that is it is called when {@link #isFinished()} returns
//     * true -- or when it is interrupted/canceled. This is where you may want to
//     * wrap up loose ends, like shutting off a motor that was being used in the
//     * command.
//     *
//     * @param interrupted whether the command was interrupted/canceled
//     */
//    @Override
//    public void end(boolean interrupted) {
//        this.swerve.brake();
//    }
//}
