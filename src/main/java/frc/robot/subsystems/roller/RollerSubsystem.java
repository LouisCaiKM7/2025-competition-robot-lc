//package frc.robot.subsystems.roller;
//
//import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.SubsystemBase;
//
//import java.util.function.DoubleSupplier;
//
//import org.littletonrobotics.junction.Logger;
//
//public class RollerSubsystem extends SubsystemBase {
//    protected final RollerIO io;
//    protected final RollerIOInputsAutoLogged inputs = new RollerIOInputsAutoLogged();
//
//    private final String name;
//
//    /**
//     * Creates a new RollerSubsystem.
//     *
//     * @param io   an io impl for this subsystem to use. Should be RollerIOReal in
//     *             all cases, use the
//     *             callback to handle sim behavior.
//     * @param name a name to be used for logging this subsystem. Should be unique.
//     */
//    public RollerSubsystem(RollerIO io, String name) {
//        this.io = io;
//        this.name = name;
//    }
//
//    @Override
//    public void periodic() {
//        io.updateInputs(inputs);
//        Logger.processInputs(name, inputs);
//    }
//
//    public Command setVelocity(DoubleSupplier velocity) {
//        return this.run(() -> io.setVelocity(velocity.getAsDouble()));
//    }
//
//    public Command setVelocity(double velocity) {
//        return this.setVelocity(() -> velocity);
//    }
//
//    public Command setVoltage(DoubleSupplier voltage) {
//        return this.run(() -> io.setVoltage(voltage.getAsDouble()));
//    }
//
//    public Command setVoltage(double voltage) {
//        return this.setVoltage(() -> voltage);
//    }
//}
