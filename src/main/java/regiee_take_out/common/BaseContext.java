package regiee_take_out.common;

/**
 * 保存和获取当前用户id
 */
public class BaseContext {
    private static ThreadLocal<Long>threadLocal=new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
       return threadLocal.get();
    }
}
