
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import javassist.ClassPool;	
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;


public class Hanzawa implements ClassFileTransformer {
	public static void premain(String agentArgs, Instrumentation inst) {
		inst.addTransformer(new Hanzawa());
	}
	
	@Override
	public byte[] transform(ClassLoader loader, String className,
	Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
	byte[] classfileBuffer) throws IllegalClassFormatException {
		return transform(classfileBuffer);
	}

	private ExprEditor editor = new ExprEditor() {

		@Override
		public void edit(MethodCall m) {
			try {
				if (!m.getClassName().startsWith("java.")
					&& !m.getClassName().startsWith("sun.")
					&& m.getMethod().getReturnType()
					.equals(CtClass.intType)) {
						m.replace("{$_ = $proceed();$_*=2;}");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	};
	
	protected byte[] transform(byte[] byteCode) {
		InputStream is = null;
		try {
			ClassPool classPool = ClassPool.getDefault();
			is = new ByteArrayInputStream(byteCode);
			CtClass cc = classPool.makeClass(is);
			cc.instrument(editor);

			return cc.toBytecode();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
