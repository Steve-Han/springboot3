import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * StackWalkerDemo 使用StackWalker输出堆栈信息
 * since jdk9
 */
public class StackWalkerDemo {

    public void methodOne() {
        this.methodTwo();
    }

    public void methodTwo() {
        this.methodThree();
    }

    public void methodThree() {
        List<StackWalker.StackFrame> walk = StackWalker.getInstance().walk(this::walkExample);

        for (StackWalker.StackFrame stackFrame : walk) {
            System.out.println(stackFrame.toStackTraceElement());
        }
    }

    public List<StackWalker.StackFrame> walkExample(Stream<StackWalker.StackFrame> stackFrameStream) {
        return stackFrameStream.collect(Collectors.toList());
    }

    private Optional<Class<?>> findMainClass(Stream<StackWalker.StackFrame> stack) {
        return stack.filter((frame) -> Objects.equals(frame.getMethodName(), "main")).findFirst()
                .map(StackWalker.StackFrame::getDeclaringClass);
    }
}