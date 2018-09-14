package org.abewang.basis;

/**
 * 暴力的Thread.stop()
 *
 * @Author Abe
 * @Date 2018/4/17.
 */
public class StopThreadUnsafe {
    public static User user = new User();

    public static class User {
        private int id;

        private int name;

        public User() {
            this.id = 0;
            this.name = 0;
        }

        // region getter & setter
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getName() {
            return name;
        }

        public void setName(int name) {
            this.name = name;
        }
        // endregion


        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name=" + name +
                    '}';
        }
    }

    public static class ChangeObjectThread extends Thread {
        @Override
        public void run() {
            synchronized (user) {
                int value = (int) System.currentTimeMillis();
                user.setId(value);
                // do sth. else
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                user.setName(value);
            }
            Thread.yield();
        }
    }

    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (user) {
                    if (user.getId() != user.getName()) {
                        System.out.println(user.toString());
                    }
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        new ReadObjectThread().start();
        while (true) {
            ChangeObjectThread cThread = new ChangeObjectThread();
            cThread.start();
            try {
                Thread.sleep((long) (150 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cThread.stop();
        }
    }
}
