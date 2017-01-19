package cn.itsite.abase.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SharedPreferencesUtils {

    private static final String TAG = AESCryptUtils.class.getSimpleName();

    //AESCrypt-ObjC uses CBC and PKCS7Padding
    private static final String AES_MODE = "AES/CBC/PKCS7Padding";
    private static final String CHARSET = "UTF-8";

    //AESCrypt-ObjC uses SHA-256 (and so a 256-bit key)
    private static final String HASH_ALGORITHM = "SHA-256";

    //AESCrypt-ObjC uses blank IV (not the best security, but the aim here is compatibility)
    private static final byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    //togglable log option (please turn off in live!)
    public static boolean DEBUG_LOG_ENABLED = false;



    private SharedPreferencesUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "setting";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {




            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

    public class AESCryptUtils {

        /**
         * Generates SHA256 hash of the password which is used as key
         *
         * @param password used to generated key
         * @return SHA256 of the password
         */
        private  SecretKeySpec generateKey(final String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
            final MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] bytes = password.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            byte[] key = digest.digest();

            log("SHA-256 key ", key);

            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            return secretKeySpec;
        }


        /**
         * Encrypt and encode message using 256-bit AES with key generated from password.
         *
         * @param password used to generated key
         * @param message  the thing you want to encrypt assumed String UTF-8
         * @return Base64 encoded CipherText
         * @throws GeneralSecurityException if problems occur during encryption
         */
        public  String encrypt(final String password, String message)
                throws GeneralSecurityException {

            try {
                final SecretKeySpec key = generateKey(password);

                log("message", message);

                byte[] cipherText = encrypt(key, ivBytes, message.getBytes(CHARSET));

                //NO_WRAP is important as was getting \n at the end
                String encoded = Base64.encodeToString(cipherText, Base64.NO_WRAP);
                log("Base64.NO_WRAP", encoded);
                return encoded;
            } catch (UnsupportedEncodingException e) {
                if (DEBUG_LOG_ENABLED)
                    Log.e(TAG, "UnsupportedEncodingException ", e);
                throw new GeneralSecurityException(e);
            }
        }


        /**
         * More flexible AES encrypt that doesn't encode
         *
         * @param key     AES key typically 128, 192 or 256 bit
         * @param iv      Initiation Vector
         * @param message in bytes (assumed it's already been decoded)
         * @return Encrypted cipher text (not encoded)
         * @throws GeneralSecurityException if something goes wrong during encryption
         */
        public  byte[] encrypt(final SecretKeySpec key, final byte[] iv, final byte[] message)
                throws GeneralSecurityException {
            final Cipher cipher = Cipher.getInstance(AES_MODE);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            byte[] cipherText = cipher.doFinal(message);

            log("cipherText", cipherText);

            return cipherText;
        }


        /**
         * Decrypt and decode ciphertext using 256-bit AES with key generated from password
         *
         * @param password                used to generated key
         * @param base64EncodedCipherText the encrpyted message encoded with base64
         * @return message in Plain text (String UTF-8)
         * @throws GeneralSecurityException if there's an issue decrypting
         */
        public  String decrypt(final String password, String base64EncodedCipherText)
                throws GeneralSecurityException {

            try {
                final SecretKeySpec key = generateKey(password);

                log("base64EncodedCipherText", base64EncodedCipherText);
                byte[] decodedCipherText = Base64.decode(base64EncodedCipherText, Base64.NO_WRAP);
                log("decodedCipherText", decodedCipherText);

                byte[] decryptedBytes = decrypt(key, ivBytes, decodedCipherText);

                log("decryptedBytes", decryptedBytes);
                String message = new String(decryptedBytes, CHARSET);
                log("message", message);


                return message;
            } catch (UnsupportedEncodingException e) {
                if (DEBUG_LOG_ENABLED)
                    Log.e(TAG, "UnsupportedEncodingException ", e);

                throw new GeneralSecurityException(e);
            }
        }


        /**
         * More flexible AES decrypt that doesn't encode
         *
         * @param key               AES key typically 128, 192 or 256 bit
         * @param iv                Initiation Vector
         * @param decodedCipherText in bytes (assumed it's already been decoded)
         * @return Decrypted message cipher text (not encoded)
         * @throws GeneralSecurityException if something goes wrong during encryption
         */
        public  byte[] decrypt(final SecretKeySpec key, final byte[] iv, final byte[] decodedCipherText)
                throws GeneralSecurityException {
            final Cipher cipher = Cipher.getInstance(AES_MODE);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            byte[] decryptedBytes = cipher.doFinal(decodedCipherText);

            log("decryptedBytes", decryptedBytes);

            return decryptedBytes;
        }


        private  void log(String what, byte[] bytes) {
            if (DEBUG_LOG_ENABLED)
                Log.e(TAG, what + "[" + bytes.length + "] [" + bytesToHex(bytes) + "]");
        }

        private  void log(String what, String value) {
            if (DEBUG_LOG_ENABLED)
                Log.e(TAG, what + "[" + value.length() + "] [" + value + "]");
        }


        /**
         * Converts byte array to hexidecimal useful for logging and fault finding
         *
         * @param bytes
         * @return
         */
        private  String bytesToHex(byte[] bytes) {
            final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            char[] hexChars = new char[bytes.length * 2];
            int v;
            for (int j = 0; j < bytes.length; j++) {
                v = bytes[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            return new String(hexChars);
        }
    }
}