import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {
    private long window;
    private static final float SPEED = 0.02f;
    private static final float MAX_Y = 0.75f;
    private static final float MIN_Y = -0.99f;

    private int windowWidth;
    private int windowHeight;

    private boolean[] keys = new boolean[GLFW_KEY_LAST];

    private Player p1 = new Player(true);
    private Player p2 = new Player(false);

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(windowWidth, windowHeight, "socorro", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
            keys[key] = action != GLFW_RELEASE;
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            windowWidth = pWidth.get(0);
            windowHeight = pHeight.get(0);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - windowWidth) / 2,
                    (vidmode.height() - windowHeight) / 2);
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.2f, 0.0f);
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT);

            if (keys[GLFW_KEY_W] && p1.getY() < MAX_Y) p1.setY(p1.getY() + SPEED);
            if (keys[GLFW_KEY_S] && p1.getY() > MIN_Y) p1.setY(p1.getY() - SPEED);
            if (keys[GLFW_KEY_UP] && p2.getY() < MAX_Y) p2.setY(p2.getY() + SPEED);
            if (keys[GLFW_KEY_DOWN] && p2.getY() > MIN_Y) p2.setY(p2.getY() - SPEED);

            p1.draw();
            p2.draw();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void cleanup() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
