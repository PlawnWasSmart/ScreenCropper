package me.plawn.screencropper.listeners;

import me.plawn.screencropper.utils.CropCallback;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CropperListener extends MouseAdapter {

    private CropCallback callback;
    private Point startPos;
    private JPanel croppingPanel;
    private JFrame frame;

    public CropperListener(JFrame frame,JPanel croppingPanel, CropCallback callback){
        this.frame = frame;
        this.callback = callback;
        this.croppingPanel = croppingPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPos = new Point((int) (e.getX()+frame.getLocation().getX()), (int) (e.getY()+frame.getLocation().getY()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        frame.setSize(300,300);
        int x = (int) Math.min(startPos.getX(),e.getX()+frame.getLocation().getX());
        int y = (int) Math.min(startPos.getY(),e.getY()+frame.getLocation().getY());
        int height = (int) Math.abs(e.getY() + frame.getLocation().getY() - startPos.getY());
        int width = (int) Math.abs(e.getX() + frame.getLocation().getX() - startPos.getX());
        callback.call(new Rectangle(x,y,width,height));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = (int) Math.min(startPos.getX(),e.getX()+frame.getLocation().getX());
        int y = (int) Math.min(startPos.getY(),e.getY()+frame.getLocation().getY());
        int height = (int) Math.abs(e.getY() + frame.getLocation().getY() - startPos.getY());
        int width = (int) Math.abs(e.getX() + frame.getLocation().getX() - startPos.getX());


        frame.setLocation(x-150,y-150);
        frame.setSize(width+300,height+300);

        croppingPanel.setLocation((int) (x-frame.getLocation().getX()), (int) (y- frame.getLocation().getY()));
        croppingPanel.setSize(width,height);
    }
}
