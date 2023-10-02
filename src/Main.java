import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.awt.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Main {
    private long window;

    Settings s = new Settings();
    private static boolean[] keys = new boolean[GLFW_KEY_LAST];

    public void run() {
        init();
        loop();

        //glfwFreeCallbacks(window);
        //glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        glfwDefaultWindowHints();                   // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);   // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);  // the window will be resizable

        window = glfwCreateWindow(s.width, s.height, "socorro", NULL, NULL);    // WINDOW CONFIG
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2);
        }
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }


    private void loop() {
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.2f, 0.0f);

        Player p1 = new Player(true);
        Player p2 = new Player(false);
        float speed = 0.02f;

        glfwSetKeyCallback(window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = action != GLFW_RELEASE;
            }
        });

        while (!glfwWindowShouldClose(window)) {    // ******************************************************* GAME LOOP
            glClear(GL_COLOR_BUFFER_BIT);

            if (keys[GLFW_KEY_W]) {
                if (p1.getY() < 0.75f)
                    p1.setY(p1.getY() + speed);
            }
            if (keys[GLFW_KEY_S]) {
                if (p1.getY() > - 0.99f)
                    p1.setY(p1.getY() - speed);
            }
            if (keys[GLFW_KEY_UP]) {
                if (p2.getY() < 0.8f)
                    p2.setY(p2.getY() + speed);
            }
            if (keys[GLFW_KEY_DOWN]) {
                if (p2.getY() > - 0.99f)
                    p2.setY(p2.getY() - speed);
            }

            p1.draw();
            p2.draw();

            //System.out.println(p1.getX()+ " " + p1.getY());

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
        glfwTerminate();
    }


    public static void main(String[] args) {
        new Main().run();
    }

}