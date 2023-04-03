package com.MyApplication;

public class MyThread extends Thread {
    Game parent;
    MyThread(Game parent_obj) {
        parent = parent_obj;
    }
    public void run() {
        while (parent.proc) {
                    parent.gameDraw();
                    try {
                        this.sleep(parent.difficult);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
