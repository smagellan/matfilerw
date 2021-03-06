/*
 * Code licensed under new-style BSD (see LICENSE).
 * All code up to tags/original: Copyright (c) 2006, Wojciech Gradkowski
 * All code after tags/original: Copyright (c) 2015, DiffPlug
 */
package com.jmatio.io;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

import sun.misc.Unsafe;

/**
 * Provides reflective access to new methods that were added to sun.misc.Unsafe
 * in Java 9. Earlier platforms will fall back to a a behavior-equivalent
 * implementation that uses available operations.
 * <p>
 * ========== JDK Warning: sun.misc.Unsafe ==========
 * <p>
 * A collection of methods for performing low-level, unsafe operations.
 * Although the class and all methods are public, use of this class is
 * limited because only trusted code can obtain instances of it.
 *
 * <em>Note:</em> It is the resposibility of the caller to make sure
 * arguments are checked before methods of this class are
 * called. While some rudimentary checks are performed on the input,
 * the checks are best effort and when performance is an overriding
 * priority, as when methods of this class are optimized by the
 * runtime compiler, some or all checks (if any) may be elided. Hence,
 * the caller must not rely on the checks and corresponding
 * exceptions!
 *
 * @author Florian Enner
 */
class Unsafe9R {

	/**
	 * Invokes the given direct byte buffer's cleaner, if any.
	 *
	 * @param directBuffer a direct byte buffer
	 * @throws NullPointerException     if {@code directBuffer} is null
	 * @throws IllegalArgumentException if {@code directBuffer} is non-direct
	 * @throws IllegalArgumentException if {@code directBuffer} is slice or duplicate (Java 9+ only)
	 */
	public static void invokeCleaner(ByteBuffer directBuffer) {
		if (!directBuffer.isDirect())
			throw new IllegalArgumentException("buffer is non-direct");
		if (useJava9)
			Java9.invokeCleaner(directBuffer);
		else {
			Java6.invokeCleaner(directBuffer);
		}
	}

	static {

		// Get Java version
		String version = System.getProperty("java.specification.version", "6");
		String majorVersion = version.startsWith("1.") ? version.substring(2) : version;
		useJava9 = Integer.parseInt(majorVersion) >= 9;

	}

	private static final boolean useJava9;

	private static class Java9 {

		static void invokeCleaner(ByteBuffer buffer) {
			try {
				INVOKE_CLEANER.invoke(UNSAFE, buffer);
			} catch (Exception e) {
				throw new IllegalStateException("Java 9 Cleaner failed to free DirectBuffer", e);
			}
		}

		static final Unsafe UNSAFE;
		static final Method INVOKE_CLEANER;

		static {
			try {
				final PrivilegedExceptionAction<Unsafe> action = new PrivilegedExceptionAction<Unsafe>() {
					@Override
					public Unsafe run() throws Exception {
						final Field f = Unsafe.class.getDeclaredField("theUnsafe");
						f.setAccessible(true);
						return (Unsafe) f.get(null);
					}
				};
				UNSAFE = AccessController.doPrivileged(action);
				INVOKE_CLEANER = UNSAFE.getClass().getMethod("invokeCleaner", ByteBuffer.class);

			} catch (final Exception ex) {
				throw new IllegalStateException("Java 9 Cleaner not available", ex);
			}

		}

	}

	private static class Java6 {

		static void invokeCleaner(ByteBuffer buffer) {
			try {
				Object cleaner = GET_CLEANER.invoke(buffer);
				if (cleaner != null)
					INVOKE_CLEANER.invoke(cleaner);
			} catch (Exception e) {
				throw new IllegalStateException("Java 6 Cleaner failed to free DirectBuffer", e);
			}
		}

		static {
			try {
				GET_CLEANER = Class.forName("sun.nio.ch.DirectBuffer").getMethod("cleaner");
				INVOKE_CLEANER = Class.forName("sun.misc.Cleaner").getMethod("clean");
			} catch (Exception e) {
				throw new IllegalStateException("Java 6 Cleaner not available", e);
			}
		}

		static final Method GET_CLEANER;
		static final Method INVOKE_CLEANER;

	}

}
