package guru.springframework.utils;

public class RecipeAppUtils {

    public static Byte[] boxBytes(byte[] unboxedBytes){
        if(unboxedBytes == null){
            return null;
        }
        Byte[] boxedBytes = new Byte[unboxedBytes.length];
        int i = 0;
        for(byte b : unboxedBytes){
            boxedBytes[i++] = b;
        }
        return boxedBytes;
    }

    public static byte[] unboxBytes(Byte[] boxedBytes){
        if(boxedBytes == null){
            return null;
        }
        byte[] unboxedBytes = new byte[boxedBytes.length];
        int i = 0;
        for(Byte b : boxedBytes){
            unboxedBytes[i++] = b;
        }
        return unboxedBytes;
    }

}
