package me.plawn.screencropper.listeners;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import me.plawn.screencropper.ScreenCropper;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class KeyBindListener implements NativeKeyListener {
    private final Set<Integer> pressing;

    public KeyBindListener() {
        this.pressing = new HashSet<>();
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        pressing.add(e.getKeyCode());
        if(pressing.containsAll(List.of(42,29,46))){
            ScreenCropper.getCropper().crop();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        pressing.remove(e.getKeyCode());
    }
}
