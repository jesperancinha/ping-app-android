package org.jesperancinha.ping;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.junit.jupiter.api.Test;

class SwipeAdapterTest {

    @Test
    void getItem() {
        SwipeAdapter swipeAdapter = new SwipeAdapter(new FragmentManager() {
        });

        Fragment item = swipeAdapter.getItem(0);
        Fragment item1 = swipeAdapter.getItem(1);

        assert item instanceof PingFragment;
        assert item1 instanceof TraceRouteFragment;
    }
}