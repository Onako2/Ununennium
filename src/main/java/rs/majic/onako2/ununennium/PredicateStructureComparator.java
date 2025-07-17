package rs.majic.onako2.ununennium;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Predicate;

// Only AI-generated class in this project because I am not a Java wizard, sorry!
// You are free to skid this xD
public final class PredicateStructureComparator {
    private PredicateStructureComparator() {
    }

    /**
     * Returns true if a and b are “structurally” the same predicate:
     * 1) If they’re the same object → true
     * 2) If both are serializable lambdas → compare impl‐class, method name, signature, and captured args
     * 3) Otherwise → compare their runtime classes (anonymous classes & custom classes share a class per declaration)
     */
    public static boolean structurallySame(Predicate<?> a, Predicate<?> b) {
        if (a == b) return true;
        if (a == null || b == null) return false;

        // 2) Try the "Serializable lambda" path:
        if (a instanceof Serializable && b instanceof Serializable) {
            try {
                SerializedLambda la = extractSerializedLambda(a);
                SerializedLambda lb = extractSerializedLambda(b);

                if (!la.getImplClass().equals(lb.getImplClass())) return false;
                if (!la.getImplMethodName().equals(lb.getImplMethodName())) return false;
                if (!la.getImplMethodSignature().equals(lb.getImplMethodSignature())) return false;

                // compare captured arguments
                int capA = la.getCapturedArgCount();
                if (capA != lb.getCapturedArgCount()) return false;
                for (int i = 0; i < capA; i++) {
                    Object argA = la.getCapturedArg(i);
                    Object argB = lb.getCapturedArg(i);
                    if (!Objects.equals(argA, argB)) {
                        return false;
                    }
                }
                return true;
            } catch (ReflectiveOperationException | ClassCastException e) {
                // not a lambda we can introspect—fall through to class check
            }
        }

        // 3) Fallback: same declaring class → assume same structure
        return a.getClass().equals(b.getClass());
    }

    private static SerializedLambda extractSerializedLambda(Object lambda)
            throws ReflectiveOperationException, ClassCastException {
        Method writeReplace = lambda.getClass().getDeclaredMethod("writeReplace");
        writeReplace.setAccessible(true);
        Object replacement = writeReplace.invoke(lambda);
        return (SerializedLambda) replacement;
    }
}
