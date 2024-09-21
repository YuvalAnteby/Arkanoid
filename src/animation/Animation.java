package animation;

import biuoop.DrawSurface;

public interface Animation {
    void doOneFrame(DrawSurface d);
    boolean shouldStop();
}
