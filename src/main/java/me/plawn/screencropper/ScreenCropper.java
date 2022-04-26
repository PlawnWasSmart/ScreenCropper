package me.plawn.screencropper;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseMotionListener;
import me.plawn.screencropper.listeners.CropperListener;
import me.plawn.screencropper.listeners.KeyBindListener;
import me.plawn.screencropper.utils.ImageSelection;

import javax.swing.*;
import java.awt.*;

public class ScreenCropper {
    private final JFrame frame;
    private static ScreenCropper cropper;

    public ScreenCropper() throws NativeHookException {
        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setFocusableWindowState(false);
        frame.setSize(300,300);
        frame.setBackground(new Color(0,0,0,1));
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        frame.setLayout(null);

        JPanel croppingPanel = new JPanel();
        croppingPanel.setBackground(new Color(0,0,0,50));

        frame.add(croppingPanel);

        CropperListener listener = new CropperListener(frame,croppingPanel,(rect) -> {
            croppingPanel.setSize(0,0);
            frame.setVisible(false);
            try {
                if(rect.getHeight()!=0&&rect.getWidth()!=0){
                    Image image = new Robot().createScreenCapture(rect);
                    ImageSelection imageSelection = new ImageSelection(image);
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imageSelection,null);
                }
            } catch (AWTException e) {
                e.printStackTrace();
            }
        });
        frame.addMouseListener(listener);
        frame.addMouseMotionListener(listener);

        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new KeyBindListener());
        GlobalScreen.addNativeMouseMotionListener(new NativeMouseMotionListener() {
            @Override
            public void nativeMouseMoved(NativeMouseEvent e) {
                frame.setLocation(e.getX()-150,e.getY()-150);
            }
        });
    }

    public void crop(){
        frame.setVisible(true);
    }

    public static ScreenCropper getCropper() {
        return cropper;
    }

    public static void main(String[] args) throws InterruptedException, NativeHookException {
        cropper = new ScreenCropper();
        Thread.currentThread().join();
    }
}
