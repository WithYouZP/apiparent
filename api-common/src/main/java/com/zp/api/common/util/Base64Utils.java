package com.zp.api.common.util;

import java.nio.charset.Charset;
import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public abstract class Base64Utils {

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private static final Base64Delegate delegate;

	static {
		Base64Delegate delegateToUse = null;
		// JDK 8's java.util.Base64 class present?
		if (ClassUtils.isPresent("java.util.Base64", Base64Utils.class.getClassLoader())) {
			delegateToUse = new JdkBase64Delegate();
		}
		delegate = delegateToUse;
	}

	private static void assertDelegateAvailable() {
		Assert.state(delegate != null, "Neither Java 8 found - Base64 encoding between byte arrays not supported");
	}

	public static byte[] encode(byte[] src) {
		assertDelegateAvailable();
		return delegate.encode(src);
	}

	public static byte[] decode(byte[] src) {
		assertDelegateAvailable();
		return delegate.decode(src);
	}

	public static byte[] encodeUrlSafe(byte[] src) {
		assertDelegateAvailable();
		return delegate.encodeUrlSafe(src);
	}

	public static byte[] decodeUrlSafe(byte[] src) {
		assertDelegateAvailable();
		return delegate.decodeUrlSafe(src);
	}

	public static String encodeToString(byte[] src) {
		if (ObjectUtils.isEmpty(src)) {
			return null;
		}
		if (delegate != null) {
			return new String(delegate.encode(src), DEFAULT_CHARSET);
		} else {
			return DatatypeConverter.printBase64Binary(src);
		}
	}

	public static byte[] decodeFromString(String src) {
		if (ObjectUtils.isEmpty(src)) {
			return null;
		}
		if (src.isEmpty()) {
			return new byte[0];
		}

		if (delegate != null) {
			return delegate.decode(src.getBytes(DEFAULT_CHARSET));
		} else {
			return DatatypeConverter.parseBase64Binary(src);
		}
	}

	interface Base64Delegate {

		byte[] encode(byte[] src);

		byte[] decode(byte[] src);

		byte[] encodeUrlSafe(byte[] src);

		byte[] decodeUrlSafe(byte[] src);
	}

	static class JdkBase64Delegate implements Base64Delegate {

		@Override
		public byte[] encode(byte[] src) {
			if (ObjectUtils.isEmpty(src)) {
				return src;
			}
			return Base64.getEncoder().encode(src);
		}

		@Override
		public byte[] decode(byte[] src) {
			if (ObjectUtils.isEmpty(src)) {
				return src;
			}
			return Base64.getDecoder().decode(src);
		}

		@Override
		public byte[] encodeUrlSafe(byte[] src) {
			if (ObjectUtils.isEmpty(src)) {
				return src;
			}
			return Base64.getUrlEncoder().encode(src);
		}

		@Override
		public byte[] decodeUrlSafe(byte[] src) {
			if (ObjectUtils.isEmpty(src)) {
				return src;
			}
			return Base64.getUrlDecoder().decode(src);
		}

	}
}
