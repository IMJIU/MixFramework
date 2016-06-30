package com.framework.netty5.src;

import io.netty.buffer.AbstractReferenceCountedByteBuf;
import io.netty.util.internal.PlatformDependent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import sun.misc.Unsafe;

public class UnsafeTest{

	private volatile int num = 0;
	public long offset;
	static Unsafe unsafe =null;
	
	static {
        long refCntFieldOffset = -1;
        try {
            if (PlatformDependent.hasUnsafe()) {
            	System.out.println("yes");
            	unsafe = Unsafe.getUnsafe();
            	System.out.println(unsafe==null);
                refCntFieldOffset = PlatformDependent.objectFieldOffset(UnsafeTest.class.getDeclaredField("num"));
                System.out.println(refCntFieldOffset);
            }else{
            	System.out.println("NONE");
            }
        } catch (Throwable t) {
            // Ignored
        }

    }
	public static void main(String[] args) {
		
		UnsafeTest t = new UnsafeTest();
//		test1();
		t.getOff(UnsafeTest.class,"num",t.getClass());
		System.out.println(t.offset);
    }
	private static void test1() {
		UnsafeTest test = new UnsafeTest();
		for (int i = 0; i < 1000; i++) {
			unsafe.getAndAddInt(UnsafeTest.class, -1, i);
        }
    }
	public int getAndIncrement(UnsafeTest obj) {
		return getAndAdd(obj, 1);
	}
	public int getAndAdd(UnsafeTest obj, int delta) {
		return unsafe.getAndAddInt(obj, -1, delta);
	}
	public void getOff(final Class tclass, final String fieldName, final Class<?> caller) {
		final Field field;
		final int modifiers;
		try {
			field = AccessController.doPrivileged(new PrivilegedExceptionAction<Field>() {

				public Field run() throws NoSuchFieldException {
					return tclass.getDeclaredField(fieldName);
				}
			});
			modifiers = field.getModifiers();
			sun.reflect.misc.ReflectUtil.ensureMemberAccess(caller, tclass, null, modifiers);
			ClassLoader cl = tclass.getClassLoader();
			ClassLoader ccl = caller.getClassLoader();
			if ((ccl != null) && (ccl != cl) && ((cl == null) )) {
				sun.reflect.misc.ReflectUtil.checkPackageAccess(tclass);
			}
		} catch (PrivilegedActionException pae) {
			throw new RuntimeException(pae.getException());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		Class<?> fieldt = field.getType();
		if (fieldt != int.class)
			throw new IllegalArgumentException("Must be integer type");

		if (!Modifier.isVolatile(modifiers))
			throw new IllegalArgumentException("Must be volatile type");

//		this.cclass = (Modifier.isProtected(modifiers) && caller != tclass) ? caller : null;
//		this.tclass = tclass;
		System.out.println(unsafe==null);
		if(unsafe==null){
			unsafe = Unsafe.getUnsafe();
			if(unsafe==null){
				System.out.println(unsafe==null);
				unsafe = Unsafe.getUnsafe();
				if(unsafe==null){
					System.out.println(unsafe==null);
					unsafe = Unsafe.getUnsafe();
				}
			}
		}
		//offset = unsafe.objectFieldOffset(field);
	}

}
