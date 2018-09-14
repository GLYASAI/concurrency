```
C:\Users\Abe\Java\Workspaces\idea\concurrency\basis\src\java\org\abewang\synchronize> javac Main.java
C:\Users\Abe\Java\Workspaces\idea\concurrency\basis\src\java\org\abewang\synchronize> javap -c Main

Compiled from "Main.java"
public class org.abewang.synchronize.Main {
  public org.abewang.synchronize.Main();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public void syncReentrantLock();
    Code:
       0: aload_0
       1: dup
       2: astore_1
       3: monitorenter
       4: aload_0
       5: dup
       6: astore_2
       7: monitorenter
       8: aload_2
       9: monitorexit
      10: goto          18
      13: astore_3
      14: aload_2
      15: monitorexit
      16: aload_3
      17: athrow
      18: aload_1
      19: monitorexit
      20: goto          30
      23: astore        4
      25: aload_1
      26: monitorexit
      27: aload         4
      29: athrow
      30: return
    Exception table:
       from    to  target type
           8    10    13   any
          13    16    13   any
           4    20    23   any
          23    27    23   any
}
```